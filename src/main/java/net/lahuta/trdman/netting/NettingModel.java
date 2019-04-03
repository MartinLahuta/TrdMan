package net.lahuta.trdman.netting;

import lombok.extern.slf4j.Slf4j;
import net.lahuta.trdman.entity.Position;
import net.lahuta.trdman.entity.PositionTrades;
import net.lahuta.trdman.entity.Trade;
import net.lahuta.trdman.exceptions.NettingException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class NettingModel {

    private String nettingKey = "";

    private final Comparator<Trade> sellComparator;
    private final Comparator<Trade> buyComparator;

    public NettingModel() {

        this.sellComparator = Comparator.comparing(Trade::getPrice);

        this.buyComparator = Comparator.comparing(Trade::getPrice,
                (trade1, trade2) -> -trade1.compareTo(trade2));

    }

    public List<Position> doNetting(String key, List<Trade> trades) {

        this.nettingKey = key;

        List<Position> positions = new ArrayList<>();

        Position position = calcOffsetSurplus(nettingKey, trades);
        positions.add(position);

        return positions;
    }

    /**
     * calculate offset/surplus,buy/sell netting positions for trades in specific netting unit.
     * assume standard net result
     */
    private Position calcOffsetSurplus(String key, List<Trade> tradeList) {

        if (tradeList.isEmpty()) {
            throw new NettingException("Empty input list: netting key=" + key);
        }

        /* gather sorted aggregated trades */
        List<Trade> aggrBuyTrades = tradeList.stream()
                .filter(t -> t.getBuySellTyp().equals("B"))
                .sorted(buyComparator)
                .collect(toList());

        List<Trade> aggrSellTrades = tradeList.stream()
                .filter(t -> t.getBuySellTyp().equals("S"))
                .sorted(sellComparator)
                .collect(toList());

        /* calculate aggregated quantities */
        BigDecimal aggrBuyQuantity = aggrBuyTrades.stream()
                .map(Trade::getQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal aggrBuyAmount = aggrBuyTrades.stream()
                .map(Trade::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal aggrSellQuantity = aggrSellTrades.stream()
                .map(Trade::getQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal aggrSellAmount = aggrSellTrades.stream()
                .map(Trade::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        /* calculate surplus/offset values */
        String surplusBuySellType;
        BigDecimal surplusQuantity;
        BigDecimal surplusAmount;
        BigDecimal offsetQuantity;
        BigDecimal offsetAmount;
        List<Trade> surplusTrades;
        List<Trade> offsetTrades;
        Trade splitTrade = null;

        if (aggrSellQuantity.compareTo(aggrBuyQuantity) > 0) {
            surplusBuySellType = "S";

            surplusQuantity = aggrSellQuantity.subtract(aggrBuyQuantity);
            offsetQuantity = aggrBuyQuantity;

            surplusAmount = aggrSellAmount.subtract(aggrBuyAmount);
            offsetAmount = aggrBuyAmount;

            offsetTrades = aggrBuyTrades;
            surplusTrades = aggrSellTrades; // initialize first with all sell trades
        } else {
            surplusBuySellType = "B";

            surplusQuantity = aggrBuyQuantity.subtract(aggrSellQuantity);
            offsetQuantity = aggrSellQuantity;

            surplusAmount = aggrBuyAmount.subtract(aggrSellAmount);
            offsetAmount = aggrSellAmount;

            offsetTrades = aggrSellTrades;
            surplusTrades = aggrBuyTrades; // initialize first with all buy trades
        }

        // splitTradeQuantity,splitTradeAmount: cumulative values which are just above offsetAmount,offsetQuantity
        BigDecimal splitTradeAmount = BigDecimal.ZERO;
        BigDecimal splitTradeQuantity = BigDecimal.ZERO;
        int splitTradeInd = 0; // index of trade in surplusTrades which is >= offsetQuantity (is just above)
        for (Trade trade : surplusTrades) {
            if (splitTradeQuantity.compareTo(offsetQuantity) >= 0) break;
            splitTradeAmount = splitTradeAmount.add(trade.getAmount());
            splitTradeQuantity = splitTradeQuantity.add(trade.getQuantity());
            splitTradeInd++;
        }

        if (surplusQuantity.compareTo(BigDecimal.ZERO) == 0) {
            // special case when surplus is empty
            offsetTrades.addAll(surplusTrades);
            surplusTrades.clear();
            // splitTrade = null
        } else if (splitTradeQuantity.compareTo(offsetQuantity) == 0) {
            // special case when surplus is not empty and no trade is split by offsetQuantity
            offsetTrades.addAll(new ArrayList<>(surplusTrades.subList(0, splitTradeInd)));
            surplusTrades = new ArrayList<>(surplusTrades.subList(splitTradeInd, surplusTrades.size()));
            // splitTrade = null
        } else {
            // surplus is not empty, one trade (surplusTrades[splitTrade-1]) is "split" by offset
            splitTradeInd--;
            offsetTrades.addAll(new ArrayList<>(surplusTrades.subList(0, splitTradeInd)));
            splitTrade = surplusTrades.get(splitTradeInd);
            surplusTrades = new ArrayList<>(surplusTrades.subList(splitTradeInd + 1, surplusTrades.size()));
        }

        /* create Position */
        // TODO: needs rewrite
        Trade trade0 = tradeList.get(0);
        String netCurrency = trade0.getCurrency();
        String netISIN = trade0.getIsin();
        String netMemberId = trade0.getMemberId();
        LocalDate netTradeDate = LocalDate.of(2020,12,18);

        Position position = Position.builder()
                .npuId("npu_" + key)
                .memberId(netMemberId)
                .quantity(surplusQuantity)
                .amount(surplusAmount)
                .price(BigDecimal.ZERO)
                .buySellTyp(surplusBuySellType)
                .currency(netCurrency)
                .isin(netISIN)
                .stlDate(netTradeDate)
                .build();

        /* create PositionTradeSplit */
        int nPositionTrades = surplusTrades.size() + offsetTrades.size();
        if(splitTrade != null) nPositionTrades++;
        List<PositionTrades> positionTradeSplit = new ArrayList<>(nPositionTrades);

        for(Trade trade : surplusTrades) {
            positionTradeSplit.add(
                    PositionTrades.builder()
                            .tradeId(trade.getTradeId())
                            .position(position)
                            .surplusAmount(trade.getAmount())
                            .surplusQuantity(trade.getQuantity())
                            .offsetAmount(BigDecimal.ZERO)
                            .offsetQuantity(BigDecimal.ZERO)
                            .build());
        }
        for(Trade trade : offsetTrades) {
            positionTradeSplit.add(
                    PositionTrades.builder()
                            .tradeId(trade.getTradeId())
                            .position(position)
                            .surplusAmount(BigDecimal.ZERO)
                            .surplusQuantity(BigDecimal.ZERO)
                            .offsetAmount(trade.getAmount())
                            .offsetQuantity(trade.getQuantity())
                            .build());
        }
        if(splitTrade != null) {
            positionTradeSplit.add(
                    PositionTrades.builder()
                            .tradeId(splitTrade.getTradeId())
                            .position(position)
                            .surplusQuantity(splitTradeQuantity.subtract(offsetQuantity))
                            .surplusAmount(splitTradeQuantity.subtract(offsetQuantity).multiply(splitTrade.getPrice()))
                            .offsetQuantity(splitTrade.getQuantity().subtract(splitTradeQuantity.subtract(offsetQuantity)))
                            .offsetAmount(splitTrade.getQuantity().subtract(splitTradeQuantity.subtract(offsetQuantity)).multiply(splitTrade.getPrice()))
                            .build());
        }

        position.setPositionTradesList(positionTradeSplit);

        log.info("POSITION created: {}, containing following trades:", position);
        tradeList.stream().forEach(t -> log.info(t.toString()));
        log.info("... and containing following trade_position_splits:");
        position.getPositionTradesList().stream().forEach(t -> log.info(t.toString()));

        return position;
    }

}

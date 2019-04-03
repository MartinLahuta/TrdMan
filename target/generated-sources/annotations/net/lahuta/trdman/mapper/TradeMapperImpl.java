package net.lahuta.trdman.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import net.lahuta.trdman.dto.TradeDto;
import net.lahuta.trdman.entity.Trade;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-04-03T10:47:15+0200",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_202 (Oracle Corporation)"
)
@Component
public class TradeMapperImpl implements TradeMapper {

    @Override
    public List<TradeDto> tradesToTradesDtos(List<Trade> trades) {
        if ( trades == null ) {
            return null;
        }

        List<TradeDto> list = new ArrayList<TradeDto>( trades.size() );
        for ( Trade trade : trades ) {
            list.add( tradeToTradeDto( trade ) );
        }

        return list;
    }

    @Override
    public List<Trade> tradesDtosToTrades(List<TradeDto> tradeDtos) {
        if ( tradeDtos == null ) {
            return null;
        }

        List<Trade> list = new ArrayList<Trade>( tradeDtos.size() );
        for ( TradeDto tradeDto : tradeDtos ) {
            list.add( tradeDtoToTrade( tradeDto ) );
        }

        return list;
    }

    @Override
    public TradeDto tradeToTradeDto(Trade trade) {
        if ( trade == null ) {
            return null;
        }

        TradeDto tradeDto = new TradeDto();

        tradeDto.setTrdNo( trade.getTrdNo() );
        tradeDto.setIsin( trade.getIsin() );
        tradeDto.setMemberId( trade.getMemberId() );
        tradeDto.setQuantity( trade.getQuantity() );
        tradeDto.setAmount( trade.getAmount() );
        tradeDto.setPrice( trade.getPrice() );
        tradeDto.setBuySellTyp( trade.getBuySellTyp() );
        tradeDto.setCurrency( trade.getCurrency() );
        tradeDto.setStlDate( trade.getStlDate() );

        return tradeDto;
    }

    @Override
    public Trade tradeDtoToTrade(TradeDto Dto) {
        if ( Dto == null ) {
            return null;
        }

        Trade trade = new Trade();

        trade.setTrdNo( Dto.getTrdNo() );
        trade.setIsin( Dto.getIsin() );
        trade.setMemberId( Dto.getMemberId() );
        trade.setQuantity( Dto.getQuantity() );
        trade.setAmount( Dto.getAmount() );
        trade.setPrice( Dto.getPrice() );
        trade.setBuySellTyp( Dto.getBuySellTyp() );
        trade.setCurrency( Dto.getCurrency() );
        trade.setStlDate( Dto.getStlDate() );

        return trade;
    }
}

package net.lahuta.trdman.mapper;

import java.util.List;
import net.lahuta.trdman.dto.TradeDto;
import net.lahuta.trdman.entity.Trade;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TradeMapper {

    List<TradeDto> tradesToTradesDtos(List<Trade> trades);
    List<Trade> tradesDtosToTrades(List<TradeDto> tradeDtos);

    TradeDto tradeToTradeDto(Trade trade);
    Trade tradeDtoToTrade(TradeDto Dto);

}

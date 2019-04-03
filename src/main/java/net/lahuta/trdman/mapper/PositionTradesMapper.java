package net.lahuta.trdman.mapper;

import net.lahuta.trdman.dto.PositionTradesDto;
import net.lahuta.trdman.entity.PositionTrades;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PositionTradesMapper {

    List<PositionTradesDto> positionTradesToPositionTradesDtos(List<PositionTrades> positionTradesList);
    List<PositionTrades> positionTradesDtosToPositionTrades(List<PositionTradesMapper> positionTradesMapperList);

    PositionTradesDto positionTradesToPositionTradesDto(PositionTrades positionTrades);
    PositionTrades positionTradesDtoToPositionTrades(PositionTradesMapper positionTradesMapper);

}

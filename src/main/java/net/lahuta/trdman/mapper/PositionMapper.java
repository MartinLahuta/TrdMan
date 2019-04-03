package net.lahuta.trdman.mapper;

import net.lahuta.trdman.dto.PositionDto;
import net.lahuta.trdman.entity.Position;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PositionMapper {

    List<PositionDto> positionsToPositionDtos(List<Position> positionList);
    List<Position> positionDtosToPositions(List<PositionDto> positionDtoList);

    PositionDto positionToPositionDto(Position position);
    Position positionDtoToPosition(PositionDto positionDto);

}

package net.lahuta.trdman.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionDto {

    private long id;

    private String npuId;
    private String currency;
    private String buySellTyp;
    private BigDecimal quantity;
    private BigDecimal amount;
    private BigDecimal price;
    private String isin;
    private String memberId;
    private LocalDate stlDate;

    private List<PositionTradesDto> positionTradesList;

}

package net.lahuta.trdman.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionTradesDto {

    private long id;
    private long tradeId;

    private BigDecimal offsetAmount;
    private BigDecimal offsetQuantity;
    private BigDecimal surplusAmount;
    private BigDecimal surplusQuantity;

}

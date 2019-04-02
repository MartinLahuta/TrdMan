package net.lahuta.trdman.dto;

// nazvy fieldu musi byt stejne jako v Trade.java

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
// pro jdbc
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeDto {

    private long trade_id;
    private String trdNo;
    private String isin;
    private String memberId;
    private BigDecimal quantity;
    private BigDecimal amount;
    private BigDecimal price;
    private String buySellTyp;
    private String currency;
    private LocalDate stlDate;
    
}

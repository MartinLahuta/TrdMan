package net.lahuta.trdman.dto;

// nazvy fieldu musi byt stejne jako v Trade.java

import java.math.BigInteger;
import java.sql.Date;
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

    private int id;
    private String ISIN;
    private String membId;
    private BigInteger Qty;
    private BigInteger Amnt;
    private BigInteger Prc;
    private String curr;
    private Date stlDate;
    
}

package net.lahuta.trdman.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "TRDBOOK")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_id")
    private long tradeId;

    @Column(name = "trd_no")
    private String trdNo;
    private String isin;

    @Column(name = "member_id")
    private String memberId;

    private BigDecimal quantity;
    private BigDecimal amount;
    private BigDecimal price;

    @Column(name = "buy_sell_typ")
    private String buySellTyp;
    private String currency;

    @Column(name = "stl_date")
    private LocalDate stlDate;

}
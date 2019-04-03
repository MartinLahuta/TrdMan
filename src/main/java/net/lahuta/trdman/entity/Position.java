package net.lahuta.trdman.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "POSITION")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_id")
    private long id;

    @Column(name = "npu_id")
    private String npuId;
    private String currency;
    @Column(name = "buy_sell_typ")
    private String buySellTyp;
    private BigDecimal quantity;
    private BigDecimal amount;
    private BigDecimal price;
    private String isin;
    @Column(name = "member_id")
    private String memberId;
    @Column(name = "stl_date")
    private LocalDate stlDate;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "position")
    private List<PositionTrades> positionTradesList;

    @Override
    public String toString() {
        return "POS: id="+id+" member="+memberId+" "+buySellTyp+": qty="+quantity+" amnt="+amount;
    }

}

package net.lahuta.trdman.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "POSITION_TRADES")
public class PositionTrades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "trade_id")
    private long tradeId;

    private BigDecimal surplusAmount;
    private BigDecimal surplusQuantity;
    private BigDecimal offsetAmount;
    private BigDecimal offsetQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @Override
    public String toString() {
        return "SPLIT: id="+id+
                " sqty="+surplusQuantity+" samnt="+surplusAmount+
                "  oqty="+offsetQuantity+" oamnt="+offsetAmount;
    }

}

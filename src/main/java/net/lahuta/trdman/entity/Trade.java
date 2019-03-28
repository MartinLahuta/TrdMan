package net.lahuta.trdman.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "TRDBOOK")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String ISIN;

    @Column(name = "membid")
    private String membId;

    private BigInteger Qty;
    private BigInteger Amnt;
    private BigInteger Prc;

    private String curr;

    @Column(name = "stldate")
    private Date stlDate;

}
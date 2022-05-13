package com.corinne.coin_data.websocket.model;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "tbl_coin")
public class Coin {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    @Column
    private Long coinId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false)
    private String tiker;

    @Column(nullable = false)
    private double buyPrice;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private int leverage;

    @Version
    private Integer version;

    public Coin() {
    }

}

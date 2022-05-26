package com.corinne.coin_data.websocket.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "tbl_user")
public class User {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    @Column
    private Long userId;

    @Column
    private String imageUrl = "null";

    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column(unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Long accountBalance = 1000000L;

    @Column(nullable = false)
    private int exp = 0;

    @Column(nullable = false)
    private boolean firstLogin = true;

    @Column(nullable = false)
    private double lastFluctuation;

    @Column(nullable = false)
    private int lastRank;

    @Column(nullable = false)
    private int highRank;

    @Column(nullable = false)
    private boolean alarm = false;

    @Column(nullable = false)
    private Long rival = 0L;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Coin> coin;

    @Version
    private Integer version;

}



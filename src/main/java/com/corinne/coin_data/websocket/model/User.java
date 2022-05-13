package com.corinne.coin_data.websocket.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

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
    private String imageUrl;

    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Long accountBalance;

    @Column(nullable = false)
    private int exp;

    @Column(nullable = false)
    private boolean firstLogin;

    @Column(nullable = false)
    private double lastFluctuation;

    @Version
    private Integer version;

}



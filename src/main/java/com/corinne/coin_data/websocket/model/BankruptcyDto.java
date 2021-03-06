package com.corinne.coin_data.websocket.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BankruptcyDto implements Serializable {
    private static final long serialVersionUID = 6494678977089006639L;
    private String tiker;
    private Long userId;
    private Long coinId;
    private int bankruptcyPrice;


}

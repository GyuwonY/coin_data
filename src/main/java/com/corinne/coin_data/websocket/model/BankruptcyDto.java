package com.corinne.coin_data.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankruptcyDto implements Serializable {
    private static final long serialVersionUID = 6494678977089006639L;
    private String tiker;
    private Long userId;
    private Long coinId;
    private int bankruptcyPrice;


}

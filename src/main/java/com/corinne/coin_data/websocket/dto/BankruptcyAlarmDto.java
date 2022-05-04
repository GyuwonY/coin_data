package com.corinne.coin_data.websocket.dto;

import com.corinne.coin_data.websocket.model.BankruptcyDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BankruptcyAlarmDto {
    private String type = "ALARM";
    private String message;

    public BankruptcyAlarmDto(BankruptcyDto dto){
        this.message = "tiker : " + dto.getTiker() + "\n청산가 : " + dto.getBankruptcyPrice() + " 가 되어 청산되었습니다.";
    }
}

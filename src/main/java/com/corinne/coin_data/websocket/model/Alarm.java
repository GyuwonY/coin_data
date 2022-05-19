package com.corinne.coin_data.websocket.model;

import com.corinne.coin_data.websocket.utils.TikerUtil;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "tbl_alarm")
@EntityListeners(AuditingEntityListener.class)
public class Alarm {

    public enum AlarmType {
         RESULT,RIVAL,RANK,FOLLWER,BANKRUPTCY,LEVEL
    }

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    @Column
    private Long alarmId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column (nullable = false)
    private AlarmType alarmNo;

    @Column (nullable = false)
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    public Alarm() {
    }

    public Alarm(User user, AlarmType alarmNo, Coin coin) {
        this.user = user;
        this.alarmNo = alarmNo;
        this.content = TikerUtil.switchTiker(coin.getTiker())+"("+coin.getAmount()+"원)";
    }
}

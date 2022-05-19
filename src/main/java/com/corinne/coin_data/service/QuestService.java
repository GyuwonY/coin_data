package com.corinne.coin_data.service;

import com.corinne.coin_data.websocket.model.Quest;
import com.corinne.coin_data.websocket.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestService {
    private final QuestRepository questRepository;

    public void checkQuest(Long userId){
        Quest quest = questRepository.findByUser_UserIdAndQuestNo(userId, 6).orElse(null);

        if(quest != null){
            if(!quest.isClear()){
                quest.update(true);
            }
        }
    }
}

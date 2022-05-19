package com.corinne.coin_data.websocket.repository;


import com.corinne.coin_data.websocket.model.Quest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestRepository extends JpaRepository<Quest, Long> {
    List<Quest> findAllByUser_UserId(Long userId);

    Optional<Quest> findByUser_UserIdAndQuestNo(Long userId, int questNo);
}

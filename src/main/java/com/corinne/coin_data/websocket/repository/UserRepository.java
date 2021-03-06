package com.corinne.coin_data.websocket.repository;

import com.corinne.coin_data.websocket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(String userEmail);
    Optional<User> findByUserId(Long userId);

    @Transactional
    @Modifying
    @Query(value = "update tbl_user, (select user_id, rank() over (order by last_fluctuation desc) as last_rank from tbl_user) as a set tbl_user.last_rank = a.last_rank where tbl_user.user_id=a.user_id", nativeQuery = true)
    void rankUpdate();

    @Transactional
    @Modifying
    @Query(value = "update tbl_user set high_rank=last_rank where high_rank=0 or high_rank>last_rank", nativeQuery = true)
    void highRankUpdate();
}
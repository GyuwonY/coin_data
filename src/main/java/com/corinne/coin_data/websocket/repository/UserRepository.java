package com.corinne.coin_data.websocket.repository;

import com.corinne.coin_data.websocket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
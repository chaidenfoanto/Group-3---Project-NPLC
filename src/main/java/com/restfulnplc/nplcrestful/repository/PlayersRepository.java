package com.restfulnplc.nplcrestful.repository;

import com.restfulnplc.nplcrestful.model.Players;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayersRepository extends JpaRepository<Players, String> {
    
}
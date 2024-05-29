package com.restfulnplc.nplcrestful.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restfulnplc.nplcrestful.model.Players;

public interface PlayersRepository extends JpaRepository<Players, String> {
    
}

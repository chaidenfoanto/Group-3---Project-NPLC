package com.restfulnplc.nplcrestful.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restfulnplc.nplcrestful.model.Team;

public interface TeamRepository extends JpaRepository<Team, String> {
    
}

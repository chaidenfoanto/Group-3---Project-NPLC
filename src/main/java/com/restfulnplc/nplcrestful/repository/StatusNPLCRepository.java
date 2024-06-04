package com.restfulnplc.nplcrestful.repository;

import com.restfulnplc.nplcrestful.model.StatusGame;
import com.restfulnplc.nplcrestful.model.StatusNPLC;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusNPLCRepository extends JpaRepository<StatusNPLC, StatusGame> {
}

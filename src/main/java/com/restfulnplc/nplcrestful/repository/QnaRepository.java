package com.restfulnplc.nplcrestful.repository;

import com.restfulnplc.nplcrestful.model.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Integer> {
}

package com.restfulnplc.nplcrestful.repository;

import com.restfulnplc.nplcrestful.model.Singlematch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SinglematchRepository extends JpaRepository<Singlematch, String> {
}

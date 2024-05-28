package com.restfulnplc.nplcrestful.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restfulnplc.nplcrestful.model.Login;

public interface LoginRepository extends JpaRepository<Login, String> {
    
}

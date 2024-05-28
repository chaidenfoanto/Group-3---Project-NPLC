package com.restfulnplc.nplcrestful.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.restfulnplc.nplcrestful.dto.LoginDTO;
import com.restfulnplc.nplcrestful.dto.PanitiaDTO;
import com.restfulnplc.nplcrestful.service.LoginService;
import com.restfulnplc.nplcrestful.service.PanitiaService;
import com.restfulnplc.nplcrestful.util.Response;
import com.restfulnplc.nplcrestful.model.Login;
import com.restfulnplc.nplcrestful.model.Panitia;


@RestController
@RequestMapping(value = "/api/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private PanitiaService panitiaService;

    @PostMapping("/process_login_panitia")
    public ResponseEntity<Response<Login>> process_login(@RequestBody LoginDTO loginDTO)
    {
        Optional<Login> sessionOptional = loginService.LoginPanitia(loginDTO);
        Response<Login> response = new Response<Login>();
        if (sessionOptional.isPresent()) {
            response.setService("Login Panitia");
            response.setMessage("Login Success");
            response.setData(sessionOptional.get());
            return  ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
        }
        response.setService("Login Panitia");
        response.setMessage("Login Failed. User or Password Invalid");
        return  ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(response);
    }

    @PostMapping("/addPanitia/{password}")
    public ResponseEntity<Response<Panitia>> addPanitia(@PathVariable("password") String password, @RequestBody PanitiaDTO panitiaDTO)
    {
        Response<Panitia> response = new Response<Panitia>();
        if(loginService.checkAdminPassword(password)){
            Panitia newPanitia = panitiaService.addPanitia(panitiaDTO);
            response.setService("Add Panitia");
            response.setMessage("Panitia Successfully Added");
            response.setData(newPanitia);
            return  ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
        }
        response.setService("Add Panitia");
        response.setMessage("Admin Password Doesn't Match");
        return  ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(response);
    }
    
}

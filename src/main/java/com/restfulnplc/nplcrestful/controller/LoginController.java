package com.restfulnplc.nplcrestful.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.restfulnplc.nplcrestful.dto.LoginDTO;
import com.restfulnplc.nplcrestful.service.LoginService;
import com.restfulnplc.nplcrestful.util.Response;
import com.restfulnplc.nplcrestful.model.Login;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/process_login_panitia")
    public ResponseEntity<Response<Login>> process_login_panitia(@RequestBody LoginDTO loginDTO)
    {
        Optional<Login> sessionOptional = loginService.LoginPanitia(loginDTO);
        Response<Login> response = new Response<Login>();
        response.setService("Login Panitia");
        if (sessionOptional.isPresent()) {
            response.setMessage("Login Success");
            response.setData(sessionOptional.get());
            return  ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
        }
        response.setMessage("Login Failed. User or Password Invalid");
        return  ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(response);
    }

    @PostMapping("/process_login_players")
    public ResponseEntity<Response<Login>> process_login_player(@RequestBody LoginDTO loginDTO)
    {
        Optional<Login> sessionOptional = loginService.LoginPlayer(loginDTO);
        Response<Login> response = new Response<Login>();
        response.setService("Login Player");
        if (sessionOptional.isPresent()) {
            response.setMessage("Login Success");
            response.setData(sessionOptional.get());
            return  ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
        }
        response.setMessage("Login Failed. User or Password Invalid");
        return  ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(response);
    }
    
    @GetMapping("/getSession")
    public ResponseEntity<Response<Login>> getSession(@CookieValue(value = "Token", required = true) String sessionToken) {
        Response<Login> response = new Response<Login>();
        response.setService("Auth Token");
        if(loginService.checkSessionAlive(sessionToken)){
            Login sessionActive = loginService.getLoginSession(sessionToken);
            response.setMessage("Authorization Success");
            response.setData(sessionActive);
            return  ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
        }
        response.setMessage("Authorization Failed");
        return  ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(response);
    }
        

}

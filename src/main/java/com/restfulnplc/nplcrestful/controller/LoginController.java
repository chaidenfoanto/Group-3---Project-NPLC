package com.restfulnplc.nplcrestful.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.restfulnplc.nplcrestful.dto.LoginDTO;
import com.restfulnplc.nplcrestful.dto.AccessDTO;
import com.restfulnplc.nplcrestful.service.LoginService;
import com.restfulnplc.nplcrestful.util.ErrorMessage;
import com.restfulnplc.nplcrestful.util.HTTPCode;
import com.restfulnplc.nplcrestful.util.Response;

import jakarta.servlet.http.HttpServletRequest;

import com.restfulnplc.nplcrestful.model.Login;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    private Response response = new Response();

    @PostMapping("/process_login_panitia")
    public ResponseEntity<Response> process_login_panitia(@RequestBody LoginDTO loginDTO) {
        response.setService("Login Panitia");
        try {
            Optional<Login> sessionOptional = loginService.LoginPanitia(loginDTO);
            if (sessionOptional.isPresent()) {
                response.setMessage("Login Success");
                response.setData(sessionOptional.get());
                response.setError(false);
                response.setHttpCode(HTTPCode.OK);
            } else {
                response.setMessage("Login Failed. User or Password Invalid");
                response.setError(true);
                response.setHttpCode(HTTPCode.FORBIDDEN);
                response.setData(new ErrorMessage(response.getHttpCode()));
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setError(true);
            response.setHttpCode(HTTPCode.INTERNAL_SERVER_ERROR);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping("/process_login_players")
    public ResponseEntity<Response> process_login_player(@RequestBody LoginDTO loginDTO) {
        response.setService("Login Player");
        try {
            Optional<Login> sessionOptional = loginService.LoginPlayer(loginDTO);
            if (sessionOptional.isPresent()) {
                response.setMessage("Login Success");
                response.setData(sessionOptional.get());
                response.setError(false);
                response.setHttpCode(HTTPCode.OK);
            } else {
                response.setMessage("Login Failed. User or Password Invalid");
                response.setError(true);
                response.setHttpCode(HTTPCode.FORBIDDEN);
                response.setData(new ErrorMessage(response.getHttpCode()));
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setError(true);
            response.setHttpCode(HTTPCode.INTERNAL_SERVER_ERROR);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/getSession")
    public ResponseEntity<Response> getSession(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Auth Token");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                Login sessionActive = loginService.getLoginSession(sessionToken);
                response.setMessage("Authorization Success");
                response.setData(sessionActive);
                response.setError(false);
                response.setHttpCode(HTTPCode.OK);
            } else {
                response.setMessage("Authorization Failed");
                response.setError(true);
                response.setHttpCode(HTTPCode.FORBIDDEN);
                response.setData(new ErrorMessage(response.getHttpCode()));
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setError(true);
            response.setHttpCode(HTTPCode.INTERNAL_SERVER_ERROR);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/getAccess")
    public ResponseEntity<Response> getAccess(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Auth Token");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                AccessDTO accessDetails = loginService.getAccessDetails(sessionToken);
                response.setMessage("Authorization Success");
                response.setError(false);
                response.setHttpCode(HTTPCode.OK);
                response.setData(accessDetails);
            } else {
                response.setMessage("Authorization Failed");
                response.setError(true);
                response.setHttpCode(HTTPCode.FORBIDDEN);
                response.setData(new ErrorMessage(response.getHttpCode()));
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setError(true);
            response.setHttpCode(HTTPCode.INTERNAL_SERVER_ERROR);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}

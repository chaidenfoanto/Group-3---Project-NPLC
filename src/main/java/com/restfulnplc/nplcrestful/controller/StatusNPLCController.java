package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.TimeDTO;
import com.restfulnplc.nplcrestful.service.LoginService;
import com.restfulnplc.nplcrestful.service.StatusNPLCService;
import com.restfulnplc.nplcrestful.util.ErrorMessage;
import com.restfulnplc.nplcrestful.util.HTTPCode;
import com.restfulnplc.nplcrestful.util.Response;


@RestController
@CrossOrigin
@RequestMapping(value = "/api/statusnplc")
public class StatusNPLCController {

    @Autowired
    private StatusNPLCService statusNPLCService;
    
    @Autowired
    private LoginService loginService;
    
    private Response response = new Response();

    @GetMapping("/startGame")
    public ResponseEntity<Response> startNPLC(@RequestHeader("Token") String sessionToken, TimeDTO timeDTO) {
        response.setService("NPLC Start");
        if (loginService.checkSessionAlive(sessionToken)) {
            if (loginService.checkSessionKetua(sessionToken)) {
                statusNPLCService.setNPLCTime(timeDTO);
                response.setMessage("Game Started");
                response.setError(false);
                response.setHttpCode(HTTPCode.OK);
                response.setData(statusNPLCService.updateStatusNPLC("Start"));
            } else {
                response.setMessage("Access Denied");
                response.setError(true);
                response.setHttpCode(HTTPCode.FORBIDDEN);
                response.setData(new ErrorMessage(response.getHttpCode()));
            }
        } else {
            response.setMessage("Authorization Failed");
            response.setError(true);
            response.setHttpCode(HTTPCode.BAD_REQUEST);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
            return ResponseEntity
                    .status(response.getHttpCode().getStatus())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
    }

    @GetMapping("/stopGame")
    public ResponseEntity<Response> stopNPLC(@RequestHeader("Token") String sessionToken) {
        response.setService("NPLC Start");
        if (loginService.checkSessionAlive(sessionToken)) {
            if (loginService.checkSessionKetua(sessionToken)) {
                response.setMessage("Game Stopped");
                response.setError(false);
                response.setHttpCode(HTTPCode.OK);
                response.setData(statusNPLCService.updateStatusNPLC("Stop"));
            } else {
                response.setMessage("Access Denied");
                response.setError(true);
                response.setHttpCode(HTTPCode.FORBIDDEN);
                response.setData(new ErrorMessage(response.getHttpCode()));
            }
        } else {
            response.setMessage("Authorization Failed");
            response.setError(true);
            response.setHttpCode(HTTPCode.BAD_REQUEST);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
            return ResponseEntity
                    .status(response.getHttpCode().getStatus())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
    }

    @GetMapping("/restartGame")
    public ResponseEntity<Response> restartNPLC(@RequestHeader("Token") String sessionToken) {
        response.setService("NPLC Start");
        if (loginService.checkSessionAlive(sessionToken)) {
            if (loginService.checkSessionKetua(sessionToken)) {
                response.setMessage("Game Restarted");
                response.setError(false);
                response.setHttpCode(HTTPCode.OK);
                response.setData(statusNPLCService.updateStatusNPLC("Restart"));
            } else {
                response.setMessage("Access Denied");
                response.setError(true);
                response.setHttpCode(HTTPCode.FORBIDDEN);
                response.setData(new ErrorMessage(response.getHttpCode()));
            }
        } else {
            response.setMessage("Authorization Failed");
            response.setError(true);
            response.setHttpCode(HTTPCode.BAD_REQUEST);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
            return ResponseEntity
                    .status(response.getHttpCode().getStatus())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
    }

}

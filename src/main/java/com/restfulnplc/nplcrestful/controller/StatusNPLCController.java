package com.restfulnplc.nplcrestful.controller;

import java.time.temporal.ChronoUnit;
import java.time.LocalTime;
import java.util.Map;

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
import com.restfulnplc.nplcrestful.model.StatusNPLC;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/statusnplc")
public class StatusNPLCController {

    @Autowired
    private StatusNPLCService statusNPLCService;

    @Autowired
    private LoginService loginService;

    private Response response = new Response();

    @GetMapping("/getNPLCStatus")
    public ResponseEntity<Response> getNPLCStatus(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get NPLC Stats");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                StatusNPLC status = statusNPLCService.getStatusNPLC();
                Long durationSecond = LocalTime.now().until(status.getWaktuSelesai(),
                        ChronoUnit.SECONDS);
                if (durationSecond <= 0)
                    durationSecond = Long.valueOf(0);
                Long durationHour = durationSecond / 3600;
                Long durationMinute = durationSecond % 3600 / 60;
                durationSecond = durationSecond % 3600 % 60;
                response.setMessage("NPLC Status Retrieved");
                response.setError(false);
                response.setHttpCode(HTTPCode.OK);
                response.setData(Map.of(
                        "statusGame", status.getStatusGame().toString(),
                        "sisaWaktu", Map.of(
                                "jam", durationHour,
                                "menit", durationMinute,
                                "detik", durationSecond)));

            } else {
                response.setMessage("Authorization Failed");
                response.setError(true);
                response.setHttpCode(HTTPCode.BAD_REQUEST);
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

    @GetMapping("/startGame")
    public ResponseEntity<Response> startNPLC(HttpServletRequest request, TimeDTO timeDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("NPLC Start");
        try {
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

    @GetMapping("/stopGame")
    public ResponseEntity<Response> stopNPLC(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("NPLC Stop");
        try {
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

    @GetMapping("/restartGame")
    public ResponseEntity<Response> restartNPLC(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("NPLC Restart");
        try {
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

    @GetMapping("/setNplcGen/{gen}")
    public ResponseEntity<Response> setGen(HttpServletRequest request, @PathVariable("gen") int gen) {
        String sessionToken = request.getHeader("Token");
        response.setService("NPLC Gen Change");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionKetua(sessionToken)) {
                    response.setMessage("Game Gen Changed!");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(statusNPLCService.setNPLCGen(gen));
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

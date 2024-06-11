package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.PanitiaDTO;
import com.restfulnplc.nplcrestful.model.Panitia;
import com.restfulnplc.nplcrestful.service.PanitiaService;
import com.restfulnplc.nplcrestful.service.LoginService;
import com.restfulnplc.nplcrestful.util.ErrorMessage;
import com.restfulnplc.nplcrestful.util.HTTPCode;
import com.restfulnplc.nplcrestful.util.Response;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/panitia")
public class PanitiaController {

    @Autowired
    private PanitiaService panitiaService;

    @Autowired
    private LoginService loginService;

    private Response response = new Response();
    List<Panitia> panitiaList = Collections.<Panitia>emptyList();

    @PostMapping
    public ResponseEntity<Response> addPanitia(HttpServletRequest request,
            @RequestBody PanitiaDTO panitiaDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Add Panitia");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionKetua(sessionToken)) {
                    if (!panitiaService.checkUsernameExists(panitiaDTO.getUsername())) {
                        Panitia newPanitia = panitiaService.addPanitia(panitiaDTO);
                        panitiaList.add(newPanitia);
                        response.setMessage("Panitia Successfully Added");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.CREATED);
                        response.setData(panitiaList.stream().map(panitia -> Map.of(
                                "idPanitia", panitia.getIdPanitia(),
                                "username", panitia.getUsername(),
                                "nama", panitia.getNama(),
                                "angkatan", panitia.getAngkatan(),
                                "spesialisasi", panitia.getSpesialisasi().toString(),
                                "isAdmin", panitia.getIsAdmin(),
                                "divisi", panitia.getDivisi().toString())).collect(Collectors.toList()));
                    } else {
                        response.setMessage("Username Exists");
                        response.setError(true);
                        response.setHttpCode(HTTPCode.CONFLICT);
                        response.setData(new ErrorMessage(response.getHttpCode()));
                    }
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
        panitiaList.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response> getAllPanitia(HttpServletRequest request) {
        response.setService("Get All Panitia");
        String sessionToken = request.getHeader("Token");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                List<Panitia> panitiaList = panitiaService.getAllPanitia();
                if (panitiaList.size() > 0) {
                    response.setMessage("All Panitia Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(panitiaList.stream().map(panitia -> Map.of(
                            "idPanitia", panitia.getIdPanitia(),
                            "username", panitia.getUsername(),
                            "nama", panitia.getNama(),
                            "angkatan", panitia.getAngkatan(),
                            "spesialisasi", panitia.getSpesialisasi().toString(),
                            "isAdmin", panitia.getIsAdmin(),
                            "divisi", panitia.getDivisi().toString())).collect(Collectors.toList()));
                } else {
                    response.setMessage("No Panitia Found");
                    response.setError(true);
                    response.setHttpCode(HTTPCode.NO_CONTENT);
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
        panitiaList.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getPanitiaById(HttpServletRequest request,
            @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get Panitia By ID");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                Optional<Panitia> panitiaOptional = panitiaService.getPanitiaById(id);
                if (panitiaOptional.isPresent()) {
                    panitiaList.add(panitiaOptional.get());
                    response.setMessage("Panitia Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(panitiaList.stream().map(panitia -> Map.of(
                            "idPanitia", panitia.getIdPanitia(),
                            "username", panitia.getUsername(),
                            "nama", panitia.getNama(),
                            "angkatan", panitia.getAngkatan(),
                            "spesialisasi", panitia.getSpesialisasi().toString(),
                            "isAdmin", panitia.getIsAdmin(),
                            "divisi", panitia.getDivisi().toString())).collect(Collectors.toList()));
                } else {
                    response.setMessage("Panitia Not Found");
                    response.setError(true);
                    response.setHttpCode(HTTPCode.NO_CONTENT);
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
        panitiaList.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updatePanitia(HttpServletRequest request,
            @PathVariable("id") String id,
            @RequestBody PanitiaDTO panitiaDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Update Panitia");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionSelf(sessionToken, id)) {
                    if (!panitiaService.checkUsernameExists(panitiaDTO.getUsername())) {
                        Optional<Panitia> updatedPanitia = panitiaService.updatePanitia(id, panitiaDTO);
                        if (updatedPanitia.isPresent()) {
                            panitiaList.add(updatedPanitia.get());
                            response.setMessage("Panitia Updated Successfully");
                            response.setError(false);
                            response.setHttpCode(HTTPCode.OK);
                            response.setData(panitiaList.stream().map(panitia -> Map.of(
                                    "idPanitia", panitia.getIdPanitia(),
                                    "username", panitia.getUsername(),
                                    "nama", panitia.getNama(),
                                    "angkatan", panitia.getAngkatan(),
                                    "spesialisasi", panitia.getSpesialisasi().toString(),
                                    "isAdmin", panitia.getIsAdmin(),
                                    "divisi", panitia.getDivisi().toString())).collect(Collectors.toList()));
                        } else {
                            response.setMessage("Username Exists");
                            response.setError(true);
                            response.setHttpCode(HTTPCode.CONFLICT);
                            response.setData(new ErrorMessage(response.getHttpCode()));
                        }
                    } else {
                        response.setMessage("Panitia Not Found");
                        response.setError(true);
                        response.setHttpCode(HTTPCode.NO_CONTENT);
                        response.setData(new ErrorMessage(response.getHttpCode()));
                    }
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
        panitiaList.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deletePanitia(HttpServletRequest request,
            @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Delete Panitia");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    boolean isDeleted = panitiaService.deletePanitia(id);
                    if (isDeleted) {
                        response.setMessage("Panitia Deleted Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                    } else {
                        response.setMessage("Panitia Not Found");
                        response.setError(true);
                        response.setHttpCode(HTTPCode.NO_CONTENT);
                        response.setData(new ErrorMessage(response.getHttpCode()));
                    }
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
        panitiaList.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

}

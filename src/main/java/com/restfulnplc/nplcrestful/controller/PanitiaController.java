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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/panitia")
public class PanitiaController {

    @Autowired
    private PanitiaService panitiaService;

    @Autowired
    private LoginService loginService;

    private Response response = new Response();

    @PostMapping
    public ResponseEntity<Response> addPanitia(HttpServletRequest request, @RequestBody PanitiaDTO panitiaDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Add Panitia");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionKetua(sessionToken)) {
                    if (!panitiaService.checkUsernameExists(panitiaDTO.getUsername())) {
                        Panitia newPanitia = panitiaService.addPanitia(panitiaDTO);
                        response.setMessage("Panitia Successfully Added");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.CREATED);
                        response.setData(Map.of(
                                "idPanitia", newPanitia.getIdPanitia(),
                                "username", newPanitia.getUsername(),
                                "nama", newPanitia.getNama(),
                                "angkatan", newPanitia.getAngkatan(),
                                "spesialisasi", newPanitia.getSpesialisasi().toString(),
                                "isAdmin", newPanitia.getIsAdmin(),
                                "divisi", newPanitia.getDivisi().toString()
                        ));
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
        return ResponseEntity.status(response.getHttpCode().getStatus())
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(response);
    }

    @GetMapping
    public ResponseEntity<Response> getAllPanitia(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get All Panitia");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                List<Panitia> panitiaList = panitiaService.getAllPanitia();
                if (!panitiaList.isEmpty()) {
                    response.setMessage("All Panitia Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    ArrayList<Object> listData = new ArrayList<>();
                    for (Panitia panitia : panitiaList) {
                        listData.add(Map.of(
                                "idPanitia", panitia.getIdPanitia(),
                                "username", panitia.getUsername(),
                                "nama", panitia.getNama(),
                                "angkatan", panitia.getAngkatan(),
                                "spesialisasi", panitia.getSpesialisasi().toString(),
                                "isAdmin", panitia.getIsAdmin(),
                                "divisi", panitia.getDivisi().toString()
                        ));
                    }
                    response.setData(listData);
                } else {
                    response.setMessage("No Panitia Found");
                    response.setError(true);
                    response.setHttpCode(HTTPCode.OK);
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
        return ResponseEntity.status(response.getHttpCode().getStatus())
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getPanitiaById(HttpServletRequest request, @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get Panitia By ID");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                Optional<Panitia> panitiaOptional = panitiaService.getPanitiaById(id);
                if (panitiaOptional.isPresent()) {
                    Panitia panitia = panitiaOptional.get();
                    response.setMessage("Panitia Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(Map.of(
                            "idPanitia", panitia.getIdPanitia(),
                            "username", panitia.getUsername(),
                            "nama", panitia.getNama(),
                            "angkatan", panitia.getAngkatan(),
                            "spesialisasi", panitia.getSpesialisasi().toString(),
                            "isAdmin", panitia.getIsAdmin(),
                            "divisi", panitia.getDivisi().toString()
                    ));
                } else {
                    response.setMessage("Panitia Not Found");
                    response.setError(true);
                    response.setHttpCode(HTTPCode.OK);
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
        return ResponseEntity.status(response.getHttpCode().getStatus())
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updatePanitia(HttpServletRequest request, @PathVariable("id") String id, @RequestBody PanitiaDTO panitiaDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Update Panitia");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionSelf(sessionToken, id)) {
                    Optional<Panitia> existingPanitia = panitiaService.getPanitiaById(id);
                    if (existingPanitia.isPresent()) {
                        if (!panitiaService.checkUsernameExists(panitiaDTO.getUsername()) || existingPanitia.get().getUsername().equals(panitiaDTO.getUsername())) {
                            Optional<Panitia> updatedPanitia = panitiaService.updatePanitia(id, panitiaDTO);
                            if (updatedPanitia.isPresent()) {
                                Panitia panitia = updatedPanitia.get();
                                response.setMessage("Panitia Updated Successfully");
                                response.setError(false);
                                response.setHttpCode(HTTPCode.OK);
                                response.setData(Map.of(
                                        "idPanitia", panitia.getIdPanitia(),
                                        "username", panitia.getUsername(),
                                        "nama", panitia.getNama(),
                                        "angkatan", panitia.getAngkatan(),
                                        "spesialisasi", panitia.getSpesialisasi().toString(),
                                        "isAdmin", panitia.getIsAdmin(),
                                        "divisi", panitia.getDivisi().toString()
                                ));
                            } else {
                                response.setMessage("Panitia Not Found");
                                response.setError(true);
                                response.setHttpCode(HTTPCode.OK);
                                response.setData(new ErrorMessage(response.getHttpCode()));
                            }
                        } else {
                            response.setMessage("Username Exists");
                            response.setError(true);
                            response.setHttpCode(HTTPCode.CONFLICT);
                            response.setData(new ErrorMessage(response.getHttpCode()));
                        }
                    } else {
                        response.setMessage("Panitia Not Found");
                        response.setError(true);
                        response.setHttpCode(HTTPCode.OK);
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
        return ResponseEntity.status(response.getHttpCode().getStatus())
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deletePanitia(HttpServletRequest request, @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Delete Panitia");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionKetua(sessionToken)) {
                    Optional<Panitia> existingPanitia = panitiaService.getPanitiaById(id);
                    if (existingPanitia.isPresent()) {
                        panitiaService.deletePanitia(id);
                        response.setMessage("Panitia Deleted Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                        response.setData(Map.of("idPanitia", id));
                    } else {
                        response.setMessage("Panitia Not Found");
                        response.setError(true);
                        response.setHttpCode(HTTPCode.OK);
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
        return ResponseEntity.status(response.getHttpCode().getStatus())
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(response);
    }
}
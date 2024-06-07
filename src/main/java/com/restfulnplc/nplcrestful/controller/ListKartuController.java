package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.ListKartuDTO;
import com.restfulnplc.nplcrestful.model.ListKartu;
import com.restfulnplc.nplcrestful.service.ListKartuService;
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
@RequestMapping(value = "/api/listkartu")
public class ListKartuController {

    @Autowired
    private ListKartuService listKartuService;

    @Autowired
    private LoginService loginService;

    private Response response = new Response();
    List<ListKartu> listKartuList = Collections.<ListKartu>emptyList();

    @PostMapping("/addListKartu")
    public ResponseEntity<Response> addListKartu(HttpServletRequest request,
            @RequestBody ListKartuDTO listKartuDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Add List Kartu");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    ListKartu newListKartu = listKartuService.addListKartu(listKartuDTO);
                    listKartuList.add(newListKartu);
                    response.setMessage("List Kartu Successfully Added");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.CREATED);
                    response.setData(listKartuList.stream().map(listKartu -> Map.of(
                            "noKartu", listKartu.getNoKartu(),
                            "cardSkill", listKartu.getCardSkill(),
                            "ownedBy", listKartu.getOwnedBy(),
                            "isUsed", listKartu.getIsUsed())).collect(Collectors.toList()));
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
        listKartuList.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response> getAllListKartu(HttpServletRequest request) {
        response.setService("Get All List Kartu");
        String sessionToken = request.getHeader("Token");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                List<ListKartu> listKartuList = listKartuService.getAllListKartu();
                if (listKartuList.size() > 0) {
                    response.setMessage("All List Kartu Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(listKartuList.stream().map(listKartu -> Map.of(
                            "noKartu", listKartu.getNoKartu(),
                            "cardSkill", listKartu.getCardSkill(),
                            "ownedBy", listKartu.getOwnedBy(),
                            "isUsed", listKartu.getIsUsed())).collect(Collectors.toList()));
                } else {
                    response.setMessage("No List Kartu Found");
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
        listKartuList.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getListKartuById(HttpServletRequest request,
            @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get List Kartu By ID");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                Optional<ListKartu> listKartuOptional = listKartuService.getListKartuById(id);
                if (listKartuOptional.isPresent()) {
                    listKartuList.add(listKartuOptional.get());
                    response.setMessage("List Kartu Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(listKartuList.stream().map(listKartu -> Map.of(
                            "noKartu", listKartu.getNoKartu(),
                            "cardSkill", listKartu.getCardSkill(),
                            "ownedBy", listKartu.getOwnedBy(),
                            "isUsed", listKartu.getIsUsed())).collect(Collectors.toList()));
                } else {
                    response.setMessage("List Kartu Not Found");
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
        listKartuList.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateListKartu(HttpServletRequest request,
            @PathVariable("id") String id,
            @RequestBody ListKartuDTO listKartuDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Update List Kartu");
        try {
            if (loginService.checkSessionPanitia(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    Optional<ListKartu> updatedListKartu = listKartuService.updateListKartu(id, listKartuDTO);
                    if (updatedListKartu.isPresent()) {
                        listKartuList.add(updatedListKartu.get());
                        response.setMessage("List Kartu Updated Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                        response.setData(listKartuList.stream().map(listKartu -> Map.of(
                                "noKartu", listKartu.getNoKartu(),
                                "cardSkill", listKartu.getCardSkill(),
                                "ownedBy", listKartu.getOwnedBy(),
                                "isUsed", listKartu.getIsUsed())).collect(Collectors.toList()));
                    } else {
                        response.setMessage("List Kartu Not Found");
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
        listKartuList.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteListKartu(HttpServletRequest request,
            @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Delete List Kartu");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    boolean isDeleted = listKartuService.deleteListKartu(id);
                    if (isDeleted) {
                        response.setMessage("List Kartu Deleted Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                    } else {
                        response.setMessage("List Kartu Not Found");
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
        listKartuList.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}

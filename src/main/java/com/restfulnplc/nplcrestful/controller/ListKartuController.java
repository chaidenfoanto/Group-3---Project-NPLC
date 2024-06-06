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
    public ResponseEntity<Response> addListKartu(@RequestHeader("Token") String sessionToken,
            @RequestBody ListKartuDTO listKartuDTO) {
        response.setService("Add List Kartu");
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
        listKartuList.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response> getAllListKartu(@RequestHeader("Token") String sessionToken) {
        response.setService("Get All List Kartu");
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
        listKartuList.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getListKartuById(@RequestHeader("Token") String sessionToken,
            @PathVariable("id") String id) {
        response.setService("Get List Kartu By ID");
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
        listKartuList.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateListKartu(@RequestHeader("Token") String sessionToken,
            @PathVariable("id") String id,
            @RequestBody ListKartuDTO listKartuDTO) {
        response.setService("Update List Kartu");
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
        listKartuList.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteListKartu(@RequestHeader("Token") String sessionToken,
            @PathVariable("id") String id) {
        response.setService("Delete List Kartu");
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
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}

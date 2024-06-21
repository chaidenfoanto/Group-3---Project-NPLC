package com.restfulnplc.nplcrestful.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restfulnplc.nplcrestful.dto.LokasiDTO;
import com.restfulnplc.nplcrestful.model.Lokasi;
import com.restfulnplc.nplcrestful.service.LoginService;
import com.restfulnplc.nplcrestful.service.LokasiService;
import com.restfulnplc.nplcrestful.util.ErrorMessage;
import com.restfulnplc.nplcrestful.util.HTTPCode;
import com.restfulnplc.nplcrestful.util.Response;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/lokasi")
public class LokasiController {

    @Autowired
    private LokasiService lokasiService;

    @Autowired
    private LoginService loginService;

    private Response response = new Response();

    @PostMapping
    public ResponseEntity<Response> addLokasi(HttpServletRequest request,
            @RequestBody LokasiDTO lokasiDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Add Lokasi");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    Lokasi newLokasi = lokasiService.addLokasi(lokasiDTO);
                    response.setMessage("Lokasi Successfully Added");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.CREATED);
                    response.setData(Map.of(
                            "noRuangan", newLokasi.getNoRuangan(),
                            "lantai", newLokasi.getLantai()));
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

    @GetMapping
    public ResponseEntity<Response> getAllLokasi(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get All Lokasi");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                List<Lokasi> lokasiList = lokasiService.getAllLokasi();
                if (lokasiList.size() > 0) {
                    response.setMessage("All Lokasi Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    List<Object> listData = new ArrayList<>();
                    for (Lokasi lokasi : lokasiList) {
                        listData.add(Map.of(
                                "noRuangan", lokasi.getNoRuangan(),
                                "lantai", lokasi.getLantai()));
                    }
                    response.setData(listData);
                } else {
                    response.setMessage("No Lokasi Found");
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
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/getAllLantai")
    public ResponseEntity<Response> getAllLantai(HttpServletRequest request) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get All Lantai");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                List<Lokasi> lokasiList = lokasiService.getAllLokasi();
                if (lokasiList.size() > 0) {
                    response.setMessage("All Lantai Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(Map.of(
                            "dataLantai", lokasiService.getLantai(),
                            "jumlahLantai", lokasiService.getLantai().size(),
                            "jumlahLokasi", lokasiService.getAllLokasi().size()));
                } else {
                    response.setMessage("No Lantai Found");
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
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getLokasiById(HttpServletRequest request,
            @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Get Lokasi By ID");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                Optional<Lokasi> lokasiOptional = lokasiService.getLokasiById(id);
                if (lokasiOptional.isPresent()) {
                    Lokasi lokasi = lokasiOptional.get();
                    response.setMessage("Lokasi Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(Map.of(
                            "noRuangan", lokasi.getNoRuangan(),
                            "lantai", lokasi.getLantai()));
                } else {
                    response.setMessage("Lokasi Not Found");
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
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateLokasi(HttpServletRequest request,
            @PathVariable("id") String id,
            @RequestBody LokasiDTO lokasiDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Update Lokasi");
        try {
            if (loginService.checkSessionPanitia(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    Optional<Lokasi> updatedLokasi = lokasiService.updateLokasi(id, lokasiDTO);
                    if (updatedLokasi.isPresent()) {
                        Lokasi lokasi = updatedLokasi.get();
                        response.setMessage("Lokasi Updated Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                        response.setData(Map.of(
                                "noRuangan", lokasi.getNoRuangan(),
                                "lantai", lokasi.getLantai()));
                    } else {
                        response.setMessage("Lokasi Not Found");
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
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteLokasi(HttpServletRequest request,
            @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Delete Lokasi");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    boolean isDeleted = lokasiService.deleteLokasi(id);
                    if (isDeleted) {
                        response.setMessage("Lokasi Deleted Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                    } else {
                        response.setMessage("Lokasi Not Found");
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
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}

package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.LokasiDTO;
import com.restfulnplc.nplcrestful.model.Lokasi;
import com.restfulnplc.nplcrestful.service.LokasiService;
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
@RequestMapping(value = "/api/lokasi")
public class LokasiController {

    @Autowired
    private LokasiService lokasiService;

    @Autowired
    private LoginService loginService;

    private Response response = new Response();
    List<Lokasi> lokasiList = Collections.<Lokasi>emptyList();

    @PostMapping("/addLokasi")
    public ResponseEntity<Response> addLokasi(@RequestHeader("Token") String sessionToken,
            @RequestBody LokasiDTO lokasiDTO) {
        response.setService("Add Lokasi");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    Lokasi newLokasi = lokasiService.addLokasi(lokasiDTO);
                    lokasiList.add(newLokasi);
                    response.setMessage("Lokasi Successfully Added");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.CREATED);
                    response.setData(lokasiList.stream().map(lokasi -> Map.of(
                            "noRuangan", lokasi.getNoRuangan(),
                            "lantai", lokasi.getLantai())).collect(Collectors.toList()));
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
        lokasiList.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response> getAllLokasi(@RequestHeader("Token") String sessionToken) {
        response.setService("Get All Lokasi");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                List<Lokasi> lokasiList = lokasiService.getAllLokasi();
                if (lokasiList.size() > 0) {
                    response.setMessage("All Lokasi Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(lokasiList.stream().map(lokasi -> Map.of(
                            "noRuangan", lokasi.getNoRuangan(),
                            "lantai", lokasi.getLantai())).collect(Collectors.toList()));
                } else {
                    response.setMessage("No Lokasi Found");
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
        lokasiList.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getLokasiById(@RequestHeader("Token") String sessionToken,
            @PathVariable("id") String id) {
        response.setService("Get Lokasi By ID");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                Optional<Lokasi> lokasiOptional = lokasiService.getLokasiById(id);
                if (lokasiOptional.isPresent()) {
                    lokasiList.add(lokasiOptional.get());
                    response.setMessage("Lokasi Retrieved Successfully");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(lokasiList.stream().map(lokasi -> Map.of(
                            "noRuangan", lokasi.getNoRuangan(),
                            "lantai", lokasi.getLantai())).collect(Collectors.toList()));
                } else {
                    response.setMessage("Lokasi Not Found");
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
        lokasiList.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateLokasi(@RequestHeader("Token") String sessionToken,
            @PathVariable("id") String id,
            @RequestBody LokasiDTO lokasiDTO) {
        response.setService("Update Lokasi");
        try {
            if (loginService.checkSessionPanitia(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    Optional<Lokasi> updatedLokasi = lokasiService.updateLokasi(id, lokasiDTO);
                    if (updatedLokasi.isPresent()) {
                        lokasiList.add(updatedLokasi.get());
                        response.setMessage("Lokasi Updated Successfully");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                        response.setData(lokasiList.stream().map(lokasi -> Map.of(
                                "noRuangan", lokasi.getNoRuangan(),
                                "lantai", lokasi.getLantai())).collect(Collectors.toList()));
                    } else {
                        response.setMessage("Lokasi Not Found");
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
        lokasiList.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteLokasi(@RequestHeader("Token") String sessionToken,
            @PathVariable("id") String id) {
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
        lokasiList.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}

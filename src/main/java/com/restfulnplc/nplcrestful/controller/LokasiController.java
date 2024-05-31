package com.restfulnplc.nplcrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restfulnplc.nplcrestful.dto.LokasiDTO;
import com.restfulnplc.nplcrestful.model.Lokasi;
import com.restfulnplc.nplcrestful.service.LokasiService;
import com.restfulnplc.nplcrestful.util.Response;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/lokasi")
public class LokasiController {

    @Autowired
    private LokasiService lokasiService;

    @PostMapping("/addLokasi")
    public ResponseEntity<Response<Lokasi>> addLokasi(@RequestBody LokasiDTO lokasiDTO) {
        Response<Lokasi> response = new Response<>();
        response.setService("Add Lokasi");
        Lokasi newLokasi = lokasiService.addLokasi(lokasiDTO);
        response.setMessage("Lokasi Successfully Added");
        response.setData(newLokasi);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<Lokasi>>> getAllLokasi() {
        Response<List<Lokasi>> response = new Response<>();
        response.setService("Get All Lokasi");
        List<Lokasi> lokasiList = lokasiService.getAllLokasi();
        response.setData(lokasiList);
        response.setMessage("All Lokasi Retrieved Successfully");
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Lokasi>> getLokasiById(@PathVariable("id") String id) {
        Response<Lokasi> response = new Response<>();
        response.setService("Get Lokasi By ID");
        Optional<Lokasi> lokasi = lokasiService.getLokasiById(id);
        if (lokasi.isPresent()) {
            response.setData(lokasi.get());
            response.setMessage("Lokasi Retrieved Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("Lokasi Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Lokasi>> updateLokasi(@PathVariable("id") String id, @RequestBody LokasiDTO lokasiDTO) {
        Response<Lokasi> response = new Response<>();
        response.setService("Update Lokasi");
        Optional<Lokasi> updatedLokasi = lokasiService.updateLokasi(id, lokasiDTO);
        if (updatedLokasi.isPresent()) {
            response.setData(updatedLokasi.get());
            response.setMessage("Lokasi Updated Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("Lokasi Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> deleteLokasi(@PathVariable("id") String id) {
        Response<String> response = new Response<>();
        response.setService("Delete Lokasi");
        boolean isDeleted = lokasiService.deleteLokasi(id);
        if (isDeleted) {
            response.setMessage("Lokasi Deleted Successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.setMessage("Lokasi Not Found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }
}

// package com.restfulnplc.nplcrestful.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.restfulnplc.nplcrestful.dto.PanitiaDTO;
// import com.restfulnplc.nplcrestful.model.Panitia;
// import com.restfulnplc.nplcrestful.service.PanitiaService;
// import com.restfulnplc.nplcrestful.service.LoginService;
// import com.restfulnplc.nplcrestful.util.Response;

// import java.util.List;
// import java.util.Optional;

// @RestController
// @CrossOrigin
// @RequestMapping(value = "/api/panitia")
// public class PanitiaController {

//     @Autowired
//     private LoginService loginService;

//     @Autowired
//     private PanitiaService panitiaService;

//     @PostMapping("/addPanitia/{password}")
//     public ResponseEntity<Response<Panitia>> addPanitia(@PathVariable("password") String password, @RequestBody PanitiaDTO panitiaDTO) {
//         Response<Panitia> response = new Response<>();
//         response.setService("Add Panitia");
//         if (loginService.checkAdminPassword(password)) {
//             Panitia newPanitia = panitiaService.addPanitia(panitiaDTO);
//             response.setMessage("Panitia Successfully Added");
//             response.setData(newPanitia);
//             return ResponseEntity
//                     .status(HttpStatus.OK)
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .body(response);
//         }
//         response.setMessage("Admin Password Doesn't Match");
//         return ResponseEntity
//                 .status(HttpStatus.FORBIDDEN)
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .body(response);
//     }

//     @GetMapping
//     public ResponseEntity<Response<List<Panitia>>> getAllPanitia() {
//         Response<List<Panitia>> response = new Response<>();
//         response.setService("Get All Panitia");
//         List<Panitia> panitiaList = panitiaService.getAllPanitia();
//         response.setData(panitiaList);
//         response.setMessage("All Panitia Retrieved Successfully");
//         return ResponseEntity
//                 .status(HttpStatus.OK)
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .body(response);
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<Response<Panitia>> getPanitiaById(@PathVariable("id") String id) {
//         Response<Panitia> response = new Response<>();
//         response.setService("Get Panitia By ID");
//         Optional<Panitia> panitia = panitiaService.getPanitiaById(id);
//         if (panitia.isPresent()) {
//             response.setData(panitia.get());
//             response.setMessage("Panitia Retrieved Successfully");
//             return ResponseEntity
//                     .status(HttpStatus.OK)
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .body(response);
//         } else {
//             response.setMessage("Panitia Not Found");
//             return ResponseEntity
//                     .status(HttpStatus.NOT_FOUND)
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .body(response);
//         }
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<Response<Panitia>> updatePanitia(@PathVariable("id") String id, @RequestBody PanitiaDTO panitiaDTO) {
//         Response<Panitia> response = new Response<>();
//         response.setService("Update Panitia");
//         Optional<Panitia> updatedPanitia = panitiaService.updatePanitia(id, panitiaDTO);
//         if (updatedPanitia.isPresent()) {
//             response.setData(updatedPanitia.get());
//             response.setMessage("Panitia Updated Successfully");
//             return ResponseEntity
//                     .status(HttpStatus.OK)
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .body(response);
//         } else {
//             response.setMessage("Panitia Not Found");
//             return ResponseEntity
//                     .status(HttpStatus.NOT_FOUND)
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .body(response);
//         }
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Response<String>> deletePanitia(@PathVariable("id") String id) {
//         Response<String> response = new Response<>();
//         response.setService("Delete Panitia");
//         boolean isDeleted = panitiaService.deletePanitia(id);
//         if (isDeleted) {
//             response.setMessage("Panitia Deleted Successfully");
//             return ResponseEntity
//                     .status(HttpStatus.OK)
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .body(response);
//         } else {
//             response.setMessage("Panitia Not Found");
//             return ResponseEntity
//                     .status(HttpStatus.NOT_FOUND)
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .body(response);
//         }
//     }
// }
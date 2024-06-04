// package com.restfulnplc.nplcrestful.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.restfulnplc.nplcrestful.dto.StatusNPLCDTO;
// import com.restfulnplc.nplcrestful.model.StatusNPLC;
// import com.restfulnplc.nplcrestful.service.StatusNPLCService;
// import com.restfulnplc.nplcrestful.util.Response;

// import java.util.List;
// import java.util.Optional;

// @RestController
// @CrossOrigin
// @RequestMapping(value = "/api/statusnplc")
// public class StatusNPLCController {

//     @Autowired
//     private StatusNPLCService statusNPLCService;

//     @PostMapping("/addStatusNPLC")
//     public ResponseEntity<Response<StatusNPLC>> addStatusNPLC(@RequestBody StatusNPLCDTO statusNPLCDTO) {
//         Response<StatusNPLC> response = new Response<>();
//         response.setService("Add Status NPLC");
//         StatusNPLC newStatusNPLC = statusNPLCService.addStatusNPLC(statusNPLCDTO);
//         response.setMessage("Status NPLC Successfully Added");
//         response.setData(newStatusNPLC);
//         return ResponseEntity
//                 .status(HttpStatus.OK)
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .body(response);
//     }

//     @GetMapping
//     public ResponseEntity<Response<List<StatusNPLC>>> getAllStatusNPLC() {
//         Response<List<StatusNPLC>> response = new Response<>();
//         response.setService("Get All Status NPLC");
//         List<StatusNPLC> statusNPLCList = statusNPLCService.getAllStatusNPLC();
//         response.setData(statusNPLCList);
//         response.setMessage("All Status NPLC Retrieved Successfully");
//         return ResponseEntity
//                 .status(HttpStatus.OK)
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .body(response);
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<Response<StatusNPLC>> getStatusNPLCById(@PathVariable("id") int id) {
//         Response<StatusNPLC> response = new Response<>();
//         response.setService("Get Status NPLC By ID");
//         Optional<StatusNPLC> statusNPLC = statusNPLCService.getStatusNPLCById(id);
//         if (statusNPLC.isPresent()) {
//             response.setData(statusNPLC.get());
//             response.setMessage("Status NPLC Retrieved Successfully");
//             return ResponseEntity
//                     .status(HttpStatus.OK)
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .body(response);
//         } else {
//             response.setMessage("Status NPLC Not Found");
//             return ResponseEntity
//                     .status(HttpStatus.NOT_FOUND)
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .body(response);
//         }
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<Response<StatusNPLC>> updateStatusNPLC(@PathVariable("id") int id, @RequestBody StatusNPLCDTO statusNPLCDTO) {
//         Response<StatusNPLC> response = new Response<>();
//         response.setService("Update Status NPLC");
//         Optional<StatusNPLC> updatedStatusNPLC = statusNPLCService.updateStatusNPLC(id, statusNPLCDTO);
//         if (updatedStatusNPLC.isPresent()) {
//             response.setData(updatedStatusNPLC.get());
//             response.setMessage("Status NPLC Updated Successfully");
//             return ResponseEntity
//                     .status(HttpStatus.OK)
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .body(response);
//         } else {
//             response.setMessage("Status NPLC Not Found");
//             return ResponseEntity
//                     .status(HttpStatus.NOT_FOUND)
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .body(response);
//         }
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Response<String>> deleteStatusNPLC(@PathVariable("id") int id) {
//         Response<String> response = new Response<>();
//         response.setService("Delete Status NPLC");
//         boolean isDeleted = statusNPLCService.deleteStatusNPLC(id);
//         if (isDeleted) {
//             response.setMessage("Status NPLC Deleted Successfully");
//             return ResponseEntity
//                     .status(HttpStatus.OK)
//                     .contentType(MediaType.APPLICATION_JSON)
//                     body(response);
//         } else {
//             response.setMessage("Status NPLC Not Found");
//             return ResponseEntity
//                     .status(HttpStatus.NOT_FOUND)
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .body(response);
//         }
//     }
// }

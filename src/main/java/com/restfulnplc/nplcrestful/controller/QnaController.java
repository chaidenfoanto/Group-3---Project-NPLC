package com.restfulnplc.nplcrestful.controller;

import com.restfulnplc.nplcrestful.dto.BoothgamesDTO;
import com.restfulnplc.nplcrestful.dto.QnaDTO;
import com.restfulnplc.nplcrestful.model.Panitia;
import com.restfulnplc.nplcrestful.model.Players;
import com.restfulnplc.nplcrestful.model.Qna;
import com.restfulnplc.nplcrestful.service.LoginService;
import com.restfulnplc.nplcrestful.service.PanitiaService;
import com.restfulnplc.nplcrestful.service.PlayersService;
import com.restfulnplc.nplcrestful.service.QnaService;
import com.restfulnplc.nplcrestful.util.ErrorMessage;
import com.restfulnplc.nplcrestful.util.HTTPCode;
import com.restfulnplc.nplcrestful.util.Response;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/qna")
public class QnaController {

    @Autowired
    private QnaService qnaService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private PlayersService playersService;

    @Autowired
    private PanitiaService panitiaService;

    private Response response = new Response();
    List<Qna> listQna = Collections.<Qna>emptyList();

//     @PostMapping("/addQna")
//     public ResponseEntity<Response> addQna(@RequestHeader("Token") String sessionToken,
//             @RequestBody QnaDTO qnaDTO) {
//         if (loginService.checkSessionAlive(sessionToken)) {
//             response.setService("Add Qna");
//             Qna newQna = qnaService.addQna(qnaDTO);
//             listQna.add(newQna);
//             response.setMessage("Qna Successfully Added");
//             response.setError(false);
//             response.setHttpCode(HTTPCode.CREATED);
//             response.setData(listQna.stream().map(qna ->Map.of(
//                     "idPertanyaan", qna.getIdPertanyaan(),
//                     "pertanyaan", qna.getPertanyaan(),
//                     "waktuInput", qna.getWaktuInput(),
//                     "jawaban", qna.getJawaban(),
//                     "player", playersService.getPlayerById(qna.getPlayer().getIdPlayer()),
//                     "panitia", panitiaService.getPanitiaById(qna.getPanitia().getIdPanitia())).collect(Collectors.toList())));
//         } else {
//             response.setMessage("Authorization Failed");
//             response.setError(true);
//             response.setHttpCode(HTTPCode.BAD_REQUEST);
//             response.setData(new ErrorMessage(response.getHttpCode()));
//         }
//         return ResponseEntity
//                 .status(response.getHttpCode().getStatus())
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .body(response);
//     }

//     @GetMapping
//     public ResponseEntity<Response> getAllQnas() {
//         response.setService("Get All Qna");
//         List<Qna> qnaList = qnaService.getAllQnas();
//         if (!qnaList.isEmpty()) {
//             response.setMessage("All Qna Retrieved Successfully");
//             response.setError(false);
//             response.setHttpCode(HTTPCode.OK);
//             response.setData(qnaList.stream().map(qna -> Map.of(
//                     "idPertanyaan", qna.getIdPertanyaan(),
//                     "pertanyaan", qna.getPertanyaan(),
//                     "waktuInput", qna.getWaktuInput(),
//                     "jawaban", qna.getJawaban(),
//                     "player", playersService.getPlayerById(qna.getPlayer().getIdPlayer()),
//                     "panitia", panitiaService.getPanitiaById(qna.getPanitia().getIdPanitia())
//             )).collect(Collectors.toList()));
//         } else {
//             response.setMessage("No Qna Found");
//             response.setError(true);
//             response.setHttpCode(HTTPCode.NO_CONTENT);
//             response.setData(new ErrorMessage(response.getHttpCode()));
//         }
//         return ResponseEntity
//                 .status(response.getHttpCode().getStatus())
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .body(response);
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<Response> getQnaById(@PathVariable("id") int id) {
//         response.setService("Get Qna By ID");
//         Optional<Qna> qnaOptional = qnaService.getQnaById(id);
//         if (qnaOptional.isPresent()) {
//             Qna qna = qnaOptional.get();
//             response.setMessage("Pertanyaan dan Jawaban berhasil ditemukan");
//             response.setError(false);
//             response.setHttpCode(HTTPCode.OK);
//             response.setData(Map.of(
//                     "idPertanyaan", qna.getIdPertanyaan(),
//                     "pertanyaan", qna.getPertanyaan(),
//                     "waktuInput", qna.getWaktuInput(),
//                     "jawaban", qna.getJawaban(),
//                     "player", playersService.getPlayerById(qna.getPlayer().getIdPlayer()),
//                     "panitia", panitiaService.getPanitiaById(qna.getPanitia().getIdPanitia())
//             ));
//         } else {
//             response.setMessage("Pertanyaan dan Jawaban tidak ditemukan");
//             response.setError(true);
//             response.setHttpCode(HTTPCode.NO_CONTENT);
//             response.setData(new ErrorMessage(response.getHttpCode()));
//         }
//         return ResponseEntity
//                 .status(response.getHttpCode().getStatus())
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .body(response);
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<Response> updateQna(@PathVariable("id") int id, @RequestBody QnaDTO qnaDTO) {
//         response.setService("Perbarui Pertanyaan dan Jawaban (Q&A)");
//         Optional<Qna> updatedQna = qnaService.updateQna(id, qnaDTO);
//         if (updatedQna.isPresent()) {
//             Qna qna = updatedQna.get();
//             response.setMessage("Pertanyaan dan Jawaban berhasil diperbarui");
//             response.setError(false);
//             response.setHttpCode(HTTPCode.OK);
//             response.setData(Map.of(
//                     "idPertanyaan", qna.getIdPertanyaan(),
//                     "pertanyaan", qna.getPertanyaan(),
//                     "waktuInput", qna.getWaktuInput(),
//                     "jawaban", qna.getJawaban(),
//                     "player", playersService.getPlayerById(qna.getPlayer().getIdPlayer()),
//                     "panitia", panitiaService.getPanitiaById(qna.getPanitia().getIdPanitia())
//             ));
//         } else {
//             response.setMessage("Pertanyaan dan Jawaban tidak ditemukan");
//             response.setError(true);
//             response.setHttpCode(HTTPCode.NO_CONTENT);
//             response.setData(new ErrorMessage(response.getHttpCode()));
//         }
//         return ResponseEntity
//                 .status(response.getHttpCode().getStatus())
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .body(response);
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Response> deleteQna(@PathVariable("id") int id) {
//         response.setService("Hapus Pertanyaan dan Jawaban (Q&A)");
//         boolean isDeleted = qnaService.deleteQna(id);
//         if (isDeleted) {
//             response.setMessage("Pertanyaan dan Jawaban berhasil dihapus");
//             response.setError(false);
//             response.setHttpCode(HTTPCode.OK);
//         } else {
//             response.setMessage("Pertanyaan dan Jawaban tidak ditemukan");
//             response.setError(true);
//             response.setHttpCode(HTTPCode.NO_CONTENT);
//             response.setData(new ErrorMessage(response.getHttpCode()));
//         }
//         return ResponseEntity
//                 .status(response.getHttpCode().getStatus())
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .body(response);
//     }
// }

    @PostMapping("/ask")
    public ResponseEntity<Response> askQuestion(@RequestHeader("Token") String sessionToken,
            @RequestBody QnaDTO qnaDTO) {
        if (loginService.checkSessionAlive(sessionToken)) {
            response.setService("Tanya Pertanyaan");
            Qna newQna = qnaService.addQuestion(qnaDTO);
            listQna.add(newQna);
            response.setMessage("Pertanyaan Berhasil Diajukan");
            response.setError(false);
            response.setHttpCode(HTTPCode.CREATED);
            response.setData(listQna.stream().map(qna -> Map.of(
                    "idPertanyaan", qna.getIdPertanyaan(),
                    "pertanyaan", qna.getPertanyaan(),
                    "waktuInput", qna.getWaktuInput(),
                    "jawaban", qna.getJawaban(),
                    "panitia", panitiaService.getPanitiaById(qna.getIdPanitia()))).collect(Collectors.toList()));
        } else {
            response.setMessage("Authorization Failed");
            response.setError(true);
            response.setHttpCode(HTTPCode.BAD_REQUEST);
            response.setData(new ErrorMessage(response.getHttpCode()));
        }
        listQna.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping("/answer")
    public ResponseEntity<Response> answerQuestion(@RequestHeader("Token") String sessionToken,
            @RequestBody QnaDTO qnaDTO) {
        if (loginService.checkSessionAlive(sessionToken)) {
            if (loginService.checkSessionAdmin(sessionToken)) {
                response.setService("Menjawab Pertanyaan");
                Qna answeredQna = qnaService.answerQuestion(qnaDTO);
                listQna.add(answeredQna);
                response.setMessage("Pertanyaan Berhasil Dijawab");
                response.setError(false);
                response.setHttpCode(HTTPCode.CREATED);
                response.setData(listQna.stream().map(qna -> Map.of(
                        "idPertanyaan", qna.getIdPertanyaan(),
                        "pertanyaan", qna.getPertanyaan(),
                        "waktuInput", qna.getWaktuInput(),
                        "jawaban", qna.getJawaban(),
                        "panitia", panitiaService.getPanitiaById(qna.getIdPanitia()),
                        "player", playersService.getPlayerById(qna.getIdPlayer()))).collect(Collectors.toList()));
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
        listQna.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/questions")
    public ResponseEntity<Response> getAllQuestions(@RequestHeader("Token") String sessionToken) {
        if (loginService.checkSessionAlive(sessionToken)) {
            response.setService("Mendapatkan Semua Pertanyaan");
            List<Qna> questionsList = qnaService.getAllQuestions();
            if (!questionsList.isEmpty()) {
                response.setMessage("Semua Pertanyaan Berhasil Didapatkan");
                response.setError(false);
                response.setHttpCode(HTTPCode.OK);
                response.setData(questionsList.stream().map(qna -> Map.of(
                        "idPertanyaan", qna.getIdPertanyaan(),
                        "pertanyaan", qna.getPertanyaan(),
                        "waktuInput", qna.getWaktuInput(),
                        "panitia", panitiaService.getPanitiaById(qna.getIdPanitia()),
                        "player", playersService.getPlayerById(qna.getIdPlayer()))).collect(Collectors.toList()));
            } else {
                response.setMessage("Tidak Ada Pertanyaan yang Ditemukan");
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
        listQna.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/answers")
    public ResponseEntity<Response> getAllAnswers(@RequestHeader("Token") String sessionToken) {
        if (loginService.checkSessionAlive(sessionToken)) {
            response.setService("Mendapatkan Semua Jawaban");
            List<Qna> answersList = qnaService.getAllAnswers();
            if (!answersList.isEmpty()) {
                response.setMessage("Semua Jawaban Berhasil Didapatkan");
                response.setError(false);
                response.setHttpCode(HTTPCode.OK);
                response.setData(answersList.stream().map(qna -> Map.of(
                        "idPertanyaan", qna.getIdPertanyaan(),
                        "pertanyaan", qna.getPertanyaan(),
                        "waktuInput", qna.getWaktuInput(),
                        "jawaban", qna.getJawaban(),
                        "panitia", panitiaService.getPanitiaById(qna.getIdPanitia()),
                        "player", playersService.getPlayerById(qna.getIdPlayer()))).collect(Collectors.toList()));
            } else {
                response.setMessage("Tidak Ada Jawaban yang Ditemukan");
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
        listQna.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateAnswer(@RequestHeader("Token") String sessionToken,
            @PathVariable("id") int id, @RequestBody QnaDTO qnaDTO) {
        if (loginService.checkSessionAlive(sessionToken)) {
            if (loginService.checkSessionAdmin(sessionToken)) {
                response.setService("Update Jawaban");
                Optional<Qna> updatedAnswer = qnaService.updateAnswer(id, qnaDTO);
                if (updatedAnswer.isPresent()) {
                    listQna.add(updatedAnswer.get());
                    response.setMessage("Jawaban Berhasil Diperbarui");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(listQna.stream().map(qna -> Map.of(
                        "idPertanyaan", qna.getIdPertanyaan(),
                        "pertanyaan", qna.getPertanyaan(),
                        "waktuInput", qna.getWaktuInput(),
                        "jawaban", qna.getJawaban(),
                        "panitia", panitiaService.getPanitiaById(qna.getIdPanitia()),
                        "player", playersService.getPlayerById(qna.getIdPlayer()))).collect(Collectors.toList()));
            } else {
                response.setMessage("Pertanyaan Tidak Ditemukan");
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
    listQna.clear();
    return ResponseEntity
            .status(response.getHttpCode().getStatus())
            .contentType(MediaType.APPLICATION_JSON)
            .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteQuestion(@RequestHeader("Token") String sessionToken,
            @PathVariable("id") int id) {
        if (loginService.checkSessionAlive(sessionToken)) {
            if (loginService.checkSessionAdmin(sessionToken)) {
                response.setService("Hapus Pertanyaan");
                boolean isDeleted = qnaService.deleteQuestion(id);
                if (isDeleted) {
                    response.setMessage("Pertanyaan Berhasil Dihapus");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                } else {
                    response.setMessage("Pertanyaan Tidak Ditemukan");
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
        listQna.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}

                
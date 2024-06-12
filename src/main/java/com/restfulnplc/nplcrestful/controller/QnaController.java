package com.restfulnplc.nplcrestful.controller;

import com.restfulnplc.nplcrestful.dto.QnaPlayersDTO;
import com.restfulnplc.nplcrestful.dto.QnaPanitiaDTO;
import com.restfulnplc.nplcrestful.model.Qna;
import com.restfulnplc.nplcrestful.service.LoginService;
import com.restfulnplc.nplcrestful.service.QnaService;
import com.restfulnplc.nplcrestful.util.ErrorMessage;
import com.restfulnplc.nplcrestful.util.HTTPCode;
import com.restfulnplc.nplcrestful.util.Response;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/qna")
public class QnaController {

    @Autowired
    private QnaService qnaService;

    @Autowired
    private LoginService loginService;

    private Response response = new Response();
    List<Qna> listQna = Collections.<Qna>emptyList();

    @PostMapping("/ask")
    public ResponseEntity<Response> askQuestion(HttpServletRequest request,
            @RequestBody QnaPlayersDTO qnaplayerDTO) {
        String sessionToken = request.getHeader("Token");
        response.setService("Tanya Pertanyaan");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionTeam(sessionToken)) {
                    Qna newQna = qnaService.addQuestion(qnaplayerDTO);
                    listQna.add(newQna);
                    response.setMessage("Pertanyaan Berhasil Diajukan");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.CREATED);
                    response.setData(listQna.stream().map(qna -> Map.of(
                            "idPertanyaan", qna.getIdPertanyaan(),
                            "pertanyaan", qna.getPertanyaan(),
                            "waktuInput", qna.getWaktuInput(),
                            "team", qna.getTeam()))
                            .collect(Collectors.toList()));
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
        listQna.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping("/answer/{id}")
    public ResponseEntity<Response> answerQuestion(HttpServletRequest request,
            @RequestBody QnaPanitiaDTO qnapanitiaDTO, @PathVariable String idQna) {
        String sessionToken = request.getHeader("Token");
        response.setService("Menjawab Pertanyaan");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    Qna answeredQna = qnaService.answerQuestion(idQna, qnapanitiaDTO);
                    listQna.add(answeredQna);
                    response.setMessage("Pertanyaan Berhasil Dijawab");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.CREATED);
                    response.setData(listQna.stream().map(qna -> Map.of(
                            "idPertanyaan", qna.getIdPertanyaan(),
                            "pertanyaan", qna.getPertanyaan(),
                            "waktuInput", qna.getWaktuInput(),
                            "jawaban", qna.getJawaban(),
                            "panitia", qna.getPanitia(),
                            "player", qna.getTeam()))
                            .collect(Collectors.toList()));
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
        listQna.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/questions")
    public ResponseEntity<Response> getAllQuestions(HttpServletRequest request) {
        response.setService("Mendapatkan Semua Pertanyaan");
        String sessionToken = request.getHeader("Token");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                List<Qna> questionsList = qnaService.getAllQuestions();
                if (!questionsList.isEmpty()) {
                    response.setMessage("Semua Pertanyaan Berhasil Didapatkan");
                    response.setError(false);
                    response.setHttpCode(HTTPCode.OK);
                    response.setData(questionsList.stream().map(qna -> Map.of(
                            "idPertanyaan", qna.getIdPertanyaan(),
                            "pertanyaan", qna.getPertanyaan(),
                            "waktuInput", qna.getWaktuInput(),
                            "jawaban", qna.getJawaban(),
                            "panitia", qna.getPanitia(),
                            "player", qna.getTeam()))
                            .collect(Collectors.toList()));
                } else {
                    response.setMessage("Tidak Ada Pertanyaan yang Ditemukan");
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
        listQna.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteQuestion(HttpServletRequest request,
            @PathVariable("id") String id) {
        String sessionToken = request.getHeader("Token");
        response.setService("Hapus Pertanyaan");
        try {
            if (loginService.checkSessionAlive(sessionToken)) {
                if (loginService.checkSessionAdmin(sessionToken)) {
                    boolean isDeleted = qnaService.deleteQuestion(id);
                    if (isDeleted) {
                        response.setMessage("Pertanyaan Berhasil Dihapus");
                        response.setError(false);
                        response.setHttpCode(HTTPCode.OK);
                    } else {
                        response.setMessage("Pertanyaan Tidak Ditemukan");
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
        listQna.clear();
        return ResponseEntity
                .status(response.getHttpCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}

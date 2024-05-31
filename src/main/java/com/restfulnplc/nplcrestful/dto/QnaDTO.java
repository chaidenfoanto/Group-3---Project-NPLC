package com.restfulnplc.nplcrestful.dto;

import java.time.LocalDateTime;

public class QnaDTO {

    private int idPertanyaan;
    private String pertanyaan;
    private LocalDateTime waktuInput;
    private String idPlayer;
    private String jawaban;
    private String idPanitia;

    // Getter dan Setter
    public int getIdPertanyaan() {
        return idPertanyaan;
    }

    public void setIdPertanyaan(int idPertanyaan) {
        this.idPertanyaan = idPertanyaan;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public LocalDateTime getWaktuInput() {
        return waktuInput;
    }

    public void setWaktuInput(LocalDateTime waktuInput) {
        this.waktuInput = waktuInput;
    }

    public String getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(String idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }

    public String getIdPanitia() {
        return idPanitia;
    }

    public void setIdPanitia(String idPanitia) {
        this.idPanitia = idPanitia;
    }
}

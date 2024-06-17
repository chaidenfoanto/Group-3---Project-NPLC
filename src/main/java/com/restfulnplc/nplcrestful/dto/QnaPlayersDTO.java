package com.restfulnplc.nplcrestful.dto;

import java.time.LocalDateTime;

public class QnaPlayersDTO {

    private String pertanyaan;
    private LocalDateTime waktuInput;
    private String idTeam;

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

    public String getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(String idTeam) {
        this.idTeam = idTeam;
    }

}

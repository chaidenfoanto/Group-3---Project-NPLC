package com.restfulnplc.nplcrestful.dto;

import com.restfulnplc.nplcrestful.enums.StatusGame;
import java.time.LocalTime;

public class StatusNPLCDTO {

    private LocalTime waktuSelesai;
    private StatusGame statusGame;

    // Getter dan Setter
    public LocalTime getWaktuSelesai() {
        return waktuSelesai;
    }

    public void setWaktuSelesai(LocalTime waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

    public StatusGame getStatusGame() {
        return statusGame;
    }

    public void setStatusGame(StatusGame statusGame) {
        this.statusGame = statusGame;
    }
}

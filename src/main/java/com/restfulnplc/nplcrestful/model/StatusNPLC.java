package com.restfulnplc.nplcrestful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalTime;

@Entity
@Table(name = "statusNPLC")
public class StatusNPLC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "waktuSelesai", nullable = false)
    private LocalTime waktuSelesai;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusGame", nullable = false)
    private StatusGame statusGame;


    public LocalTime getWaktuSelesai() {
        return this.waktuSelesai;
    }

    public void setWaktuSelesai(LocalTime waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

    public StatusGame getStatusGame() {
        return this.statusGame;
    }

    public void setStatusGame(StatusGame statusGame) {
        this.statusGame = statusGame;
    }
    
}

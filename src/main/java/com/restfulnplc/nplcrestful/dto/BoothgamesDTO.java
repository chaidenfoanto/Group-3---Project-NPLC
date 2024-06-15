package com.restfulnplc.nplcrestful.dto;

import java.sql.Time;

import com.restfulnplc.nplcrestful.model.Tipegame;

public class BoothgamesDTO {
    private int idBooth;
    private String nama;
    private String idPenjaga1;
    private String idPenjaga2;
    private String sopGames;
    private String lokasi;
    private String tipeGame;
    private Time durasiPermainan;
    private byte[] fotoBooth;

    public int getIdBooth() {
        return idBooth;
    }

    public void setIdBooth(int idBooth) {
        this.idBooth = idBooth;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getIdPenjaga1() {
        return idPenjaga1;
    }

    public void setIdPenjaga1(String idPenjaga1) {
        this.idPenjaga1 = idPenjaga1;
    }

    public String getIdPenjaga2() {
        return idPenjaga2;
    }

    public void setIdPenjaga2(String idPenjaga2) {
        this.idPenjaga2 = idPenjaga2;
    }

    public String getSopGames() {
        return sopGames;
    }

    public void setSopGames(String sopGames) {
        this.sopGames = sopGames;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTipeGame() {
        return tipeGame;
    }

    public Tipegame getTipeGameClass() {
        return Tipegame.fromString(tipeGame);
    }

    public void setTipeGame(String tipeGame) {
        this.tipeGame = tipeGame;
    }

    public Time getDurasiPermainan() {
        return durasiPermainan;
    }

    public void setDurasiPermainan(Time durasiPermainan) {
        this.durasiPermainan = durasiPermainan;
    }

    public byte[] getFotoBooth() {
        return this.fotoBooth;
    }

    public void setFotoBooth(byte[] fotoBooth) {
        this.fotoBooth = fotoBooth;
    }
}

package com.restfulnplc.nplcrestful.dto;

import com.restfulnplc.nplcrestful.model.Boothgames.TipeGame;

import java.sql.Time;

public class BoothgamesDTO {
    private int idBooth;
    private String nama;
    private String idPenjaga1;
    private String idPenjaga2;
    private String sopGames;
    private String lokasi;
    private TipeGame tipeGame;
    private Time durasiPermainan;

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

    public TipeGame getTipeGame() {
        return tipeGame;
    }

    public void setTipeGame(TipeGame tipeGame) {
        this.tipeGame = tipeGame;
    }

    public Time getDurasiPermainan() {
        return durasiPermainan;
    }

    public void setDurasiPermainan(Time durasiPermainan) {
        this.durasiPermainan = durasiPermainan;
    }
}

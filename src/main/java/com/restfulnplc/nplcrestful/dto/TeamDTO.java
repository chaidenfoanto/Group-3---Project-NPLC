package com.restfulnplc.nplcrestful.dto;

import com.restfulnplc.nplcrestful.model.Kategori;

public class TeamDTO {

    private String nama;
    private String username;
    private String passUsr;
    private String asalSekolah;
    private String kategori;
    private String namaPlayer1;
    private String fotoPlayer1;
    private String namaPlayer2;
    private String fotoPlayer2;
    private String namaPlayer3;
    private String fotoPlayer3;

    public boolean checkPlayer(int playerNumber) {
        switch(playerNumber) {
            case 1: return (namaPlayer1.equals("")) ? false : true;
            case 2: return (namaPlayer2.equals("")) ? false : true;
            default: return (namaPlayer3.equals("")) ? false : true;
        }
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassUsr() {
        return this.passUsr;
    }

    public void setPassUsr(String passUsr) {
        this.passUsr = passUsr;
    }

    public String getAsalSekolah() {
        return this.asalSekolah;
    }

    public void setAsalSekolah(String asalSekolah) {
        this.asalSekolah = asalSekolah;
    }

    public String getKategori() {
        return this.kategori;
    }

    public Kategori getKategoriClass() {
        return Kategori.fromString(this.kategori);
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getNamaPlayer1() {
        return this.namaPlayer1;
    }

    public void setNamaPlayer1(String namaPlayer1) {
        this.namaPlayer1 = namaPlayer1;
    }

    public String getFotoPlayer1() {
        return this.fotoPlayer1;
    }

    public void setFotoPlayer1(String fotoPlayer1) {
        this.fotoPlayer1 = fotoPlayer1;
    }

    public String getNamaPlayer2() {
        return this.namaPlayer2;
    }

    public void setNamaPlayer2(String namaPlayer2) {
        this.namaPlayer2 = namaPlayer2;
    }

    public String getFotoPlayer2() {
        return this.fotoPlayer2;
    }

    public void setFotoPlayer2(String fotoPlayer2) {
        this.fotoPlayer2 = fotoPlayer2;
    }

    public String getNamaPlayer3() {
        return this.namaPlayer3;
    }

    public void setNamaPlayer3(String namaPlayer3) {
        this.namaPlayer3 = namaPlayer3;
    }

    public String getFotoPlayer3() {
        return this.fotoPlayer3;
    }

    public void setFotoPlayer3(String fotoPlayer3) {
        this.fotoPlayer3 = fotoPlayer3;
    }

}


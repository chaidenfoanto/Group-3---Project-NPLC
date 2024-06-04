package com.restfulnplc.nplcrestful.dto;

import com.restfulnplc.nplcrestful.enums.Divisi;
import com.restfulnplc.nplcrestful.enums.Spesialisasi;

public class PanitiaDTO {
    private String idPanitia;
    private String username;
    private String nama;
    private String password;
    private int angkatan;
    private String spesialisasi;
    private boolean isAdmin;
    private String divisi;

    public PanitiaDTO() {
    }

    public PanitiaDTO(String idPanitia, String username, String nama, String password, int angkatan, String spesialisasi, boolean isAdmin, String divisi) {
        this.idPanitia = idPanitia;
        this.username = username;
        this.nama = nama;
        this.password = password;
        this.angkatan = angkatan;
        this.spesialisasi = spesialisasi;
        this.isAdmin = isAdmin;
        this.divisi = divisi;
    }

    public String getIdPanitia() {
        return this.idPanitia;
    }

    public void setIdPanitia(String idPanitia) {
        this.idPanitia = idPanitia;
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAngkatan() {
        return this.angkatan;
    }

    public void setAngkatan(int angkatan) {
        this.angkatan = angkatan;
    }

    public String getSpesialisasi() {
        return this.spesialisasi.toString();
    }

    public Spesialisasi getSpesialisasiClass() {
        return Spesialisasi.fromString(this.spesialisasi);
    }

    public void setSpesialisasi(String spesialisasi) {
        this.spesialisasi = spesialisasi;
    }

    public boolean isIsAdmin() {
        return this.isAdmin;
    }

    public boolean getIsAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getDivisi() {
        return this.divisi.toString();
    }

    public Divisi getDivisiClass() {
        return Divisi.fromString(this.divisi);
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

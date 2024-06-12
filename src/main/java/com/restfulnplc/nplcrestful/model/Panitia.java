package com.restfulnplc.nplcrestful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "panitia")
public class Panitia {

    @Id
    @Column(name = "idpanitia", length = 14, nullable = false)
    private String idPanitia;

    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = "nama", length = 50, nullable = false)
    private String nama;

    @Column(name = "passusr", length = 255, nullable = false)
    private String password;

    @Column(name = "angkatan", nullable = false)
    private int angkatan;

    @Enumerated(EnumType.STRING)
    @Column(name = "spesialisasi", nullable = false)
    private Spesialisasi spesialisasi;

    @Column(name = "isAdmin", nullable = false)
    private boolean isAdmin;

    @Enumerated(EnumType.STRING)
    @Column(name = "divisi", nullable = false)
    private Divisi divisi;

    public Panitia() {
    }

    public Panitia(String idPanitia, String username, String nama, String password, int angkatan, Spesialisasi spesialisasi, boolean isAdmin, Divisi divisi) {
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

    public Spesialisasi getSpesialisasi() {
        return this.spesialisasi;
    }

    public void setSpesialisasi(Spesialisasi spesialisasi) {
        this.spesialisasi = spesialisasi;
    }

    public boolean getIsAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Divisi getDivisi() {
        return this.divisi;
    }

    public void setDivisi(Divisi divisi) {
        this.divisi = divisi;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}

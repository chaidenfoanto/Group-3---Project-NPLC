package com.restfulnplc.nplcrestful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @Column(name = "idteam", length = 20)
    private String idTeam;

    @Column(name = "nama", length = 50, unique = true)
    private String nama;

    @Column(name = "usernameTeam", length = 50, unique = true)
    private String username;

    @Column(name = "passusr", length = 255)
    private String passUsr;

    @Column(name = "asalsekolah", length = 50)
    private String asalSekolah;

    @Enumerated(EnumType.STRING)
    @Column(name = "kategori")
    private KategoriTeam kategoriTeam;

    @OneToMany(mappedBy = "team")
    private Set<Players> players;

    @Column(name = "chanceroll")
    private int chanceRoll;

    @Column(name = "totalPoin")
    private int totalPoin;

    public String getIdTeam() {
        return this.idTeam;
    }

    public void setIdTeam(String idTeam) {
        this.idTeam = idTeam;
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

    public KategoriTeam getKategoriTeam() {
        return this.kategoriTeam;
    }

    public void setKategoriTeam(KategoriTeam kategoriTeam) {
        this.kategoriTeam = kategoriTeam;
    }

    public Set<Players> getPlayers() {
        return this.players;
    }

    public void setPlayers(Set<Players> players) {
        this.players = players;
    }

    public int getChanceRoll() {
        return this.chanceRoll;
    }

    public void setChanceRoll(int chanceRoll) {
        this.chanceRoll = chanceRoll;
    }

    public int getTotalPoin() {
        return this.totalPoin;
    }

    public void setTotalPoin(int totalPoin) {
        this.totalPoin = totalPoin;
    }

}


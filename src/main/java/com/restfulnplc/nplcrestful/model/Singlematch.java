package com.restfulnplc.nplcrestful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalTime;

@Entity
@Table(name = "singlematch")
public class Singlematch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nomatch", nullable = false)
    private String noMatch;

    @ManyToOne
    @JoinColumn(name = "idteam", referencedColumnName = "idteam", nullable = false, foreignKey = @ForeignKey(name = "fk_idteamsingle"))
    private Team team;

    @Column(name = "waktumulai")
    private LocalTime waktuMulai;

    @Column(name = "waktuselesai")
    private LocalTime waktuSelesai;

    @ManyToOne
    @JoinColumn(name = "nokartu", referencedColumnName = "nokartu", foreignKey = @ForeignKey(name = "fk_nokartu"))
    private ListKartu listKartu;

    @Column(name = "inputby", length = 14, nullable = false)
    private String inputBy;

    @Column(name = "totalpoin")
    private int totalPoin;

    @ManyToOne
    @JoinColumn(name = "idbooth", referencedColumnName = "idbooth", nullable = false, foreignKey = @ForeignKey(name = "fk_idboothsingle"))
    private Boothgames boothGames;

    public String getNoMatch() {
        return this.noMatch;
    }

    public void setNoMatch(String noMatch) {
        this.noMatch = noMatch;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public LocalTime getWaktuMulai() {
        return this.waktuMulai;
    }

    public void setWaktuMulai(LocalTime waktuMulai) {
        this.waktuMulai = waktuMulai;
    }

    public LocalTime getWaktuSelesai() {
        return this.waktuSelesai;
    }

    public void setWaktuSelesai(LocalTime waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

    public ListKartu getListKartu() {
        return this.listKartu;
    }

    public void setListKartu(ListKartu listKartu) {
        this.listKartu = listKartu;
    }

    public String getInputBy() {
        return this.inputBy;
    }

    public void setInputBy(String inputBy) {
        this.inputBy = inputBy;
    }

    public int getTotalPoin() {
        return this.totalPoin;
    }

    public void setTotalPoin(int totalPoin) {
        this.totalPoin = totalPoin;
    }

    public Boothgames getBoothGames() {
        return this.boothGames;
    }

    public void setBoothGames(Boothgames boothGames) {
        this.boothGames = boothGames;
    }
    
}


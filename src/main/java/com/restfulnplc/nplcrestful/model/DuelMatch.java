package com.restfulnplc.nplcrestful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Time;

@Entity
@Table(name = "duelmatch")
public class DuelMatch {

    @Id
    @Column(name = "nomatch", length = 15, nullable = false)
    private String noMatch;

    @ManyToOne
    @JoinColumn(name = "idteam1", referencedColumnName = "idteam", foreignKey = @ForeignKey(name = "fk_idteam1"), nullable = false)
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "idteam2", referencedColumnName = "idteam", foreignKey = @ForeignKey(name = "fk_idteam2"), nullable = false)
    private Team team2;

    @Column(name = "waktumulai")
    private Time waktuMulai;

    @Column(name = "waktuselesai")
    private Time waktuSelesai;

    @Column(name = "inputby", length = 14, nullable = false)
    private String inputBy;

    @ManyToOne
    @JoinColumn(name = "timmenang", referencedColumnName = "idteam", foreignKey = @ForeignKey(name = "fk_timmenang"), nullable = false)
    private Team timMenang;

    @ManyToOne
    @JoinColumn(name = "idbooth", referencedColumnName = "idbooth", foreignKey = @ForeignKey(name = "fk_idboothduel"), nullable = false)
    private Boothgames boothGames;

    public String getNoMatch() {
        return this.noMatch;
    }

    public void setNoMatch(String noMatch) {
        this.noMatch = noMatch;
    }

    public Team getTeam1() {
        return this.team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return this.team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public Time getWaktuMulai() {
        return this.waktuMulai;
    }

    public void setWaktuMulai(Time waktuMulai) {
        this.waktuMulai = waktuMulai;
    }

    public Time getWaktuSelesai() {
        return this.waktuSelesai;
    }

    public void setWaktuSelesai(Time waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

    public String getInputBy() {
        return this.inputBy;
    }

    public void setInputBy(String inputBy) {
        this.inputBy = inputBy;
    }

    public Team getTimMenang() {
        return this.timMenang;
    }

    public void setTimMenang(Team timMenang) {
        this.timMenang = timMenang;
    }

    public Boothgames getBoothGames() {
        return this.boothGames;
    }

    public void setBoothGames(Boothgames boothGames) {
        this.boothGames = boothGames;
    }
}
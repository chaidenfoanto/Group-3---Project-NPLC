package com.restfulnplc.nplcrestful.dto;

import com.restfulnplc.nplcrestful.model.Boothgames;
import com.restfulnplc.nplcrestful.model.Team;

import java.sql.Time;

public class DuelMatchDTO {
    private String noMatch;
    private Team team1;
    private Team team2;
    private Time waktuMulai;
    private Time waktuSelesai;
    private String inputBy;
    private Team timMenang;
    private Boothgames boothGames;

    public String getNoMatch() {
        return noMatch;
    }

    public void setNoMatch(String noMatch) {
        this.noMatch = noMatch;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public Time getWaktuMulai() {
        return waktuMulai;
    }

    public void setWaktuMulai(Time waktuMulai) {
        this.waktuMulai = waktuMulai;
    }

    public Time getWaktuSelesai() {
        return waktuSelesai;
    }

    public void setWaktuSelesai(Time waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

    public String getInputBy() {
        return inputBy;
    }

    public void setInputBy(String inputBy) {
        this.inputBy = inputBy;
    }

    public Team getTimMenang() {
        return timMenang;
    }

    public void setTimMenang(Team timMenang) {
        this.timMenang = timMenang;
    }

    public Boothgames getBoothGames() {
        return boothGames;
    }

    public void setBoothGames(Boothgames boothGames) {
        this.boothGames = boothGames;
    }
}

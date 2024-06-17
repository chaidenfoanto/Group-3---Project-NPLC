package com.restfulnplc.nplcrestful.dto;

import java.sql.Time;

public class DuelMatchDTO {
    private String noMatch;
    private String team1;
    private String team2;
    private Time waktuMulai;
    private Time waktuSelesai;
    private String inputBy;
    private String timMenang;
    private String boothGames;

    public String getNoMatch() {
        return noMatch;
    }

    public void setNoMatch(String noMatch) {
        this.noMatch = noMatch;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
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

    public String getTimMenang() {
        return timMenang;
    }

    public void setTimMenang(String timMenang) {
        this.timMenang = timMenang;
    }

    public String getBoothGames() {
        return boothGames;
    }

    public void setBoothGames(String boothGames) {
        this.boothGames = boothGames;
    }
}

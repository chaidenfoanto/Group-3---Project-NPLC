package com.restfulnplc.nplcrestful.dto;

import java.time.LocalTime;

public class SinglematchDTO {

    private String noMatch;
    private String idTeam;
    private LocalTime waktuMulai;
    private LocalTime waktuSelesai;
    private String noKartu;
    private String inputBy;
    private int totalPoin;
    private int totalBintang;
    private String idBooth;

    // Getter dan Setter
    public String getNoMatch() {
        return noMatch;
    }

    public void setNoMatch(String noMatch) {
        this.noMatch = noMatch;
    }

    public String getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(String idTeam) {
        this.idTeam = idTeam;
    }

    public LocalTime getWaktuMulai() {
        return waktuMulai;
    }

    public void setWaktuMulai(LocalTime waktuMulai) {
        this.waktuMulai = waktuMulai;
    }

    public LocalTime getWaktuSelesai() {
        return waktuSelesai;
    }

    public void setWaktuSelesai(LocalTime waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

    public String getNoKartu() {
        return noKartu;
    }

    public void setNoKartu(String noKartu) {
        this.noKartu = noKartu;
    }

    public String getInputBy() {
        return inputBy;
    }

    public void setInputBy(String inputBy) {
        this.inputBy = inputBy;
    }

    public int getTotalPoin() {
        return totalPoin;
    }

    public void setTotalPoin(int totalPoin) {
        this.totalPoin = totalPoin;
    }

    public int getTotalBintang() {
        return this.totalBintang;
    }

    public void setTotalBintang(int totalBintang) {
        this.totalBintang = totalBintang;
    }

    public String getIdBooth() {
        return idBooth;
    }

    public void setIdBooth(String idBooth) {
        this.idBooth = idBooth;
    }
}

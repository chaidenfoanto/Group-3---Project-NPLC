package com.restfulnplc.nplcrestful.dto;

public class SinglematchDTO {
    private String idTeam;
    private String noKartu;
    private int totalBintang;

    public String getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(String idTeam) {
        this.idTeam = idTeam;
    }

    public String getNoKartu() {
        return noKartu;
    }

    public void setNoKartu(String noKartu) {
        this.noKartu = noKartu;
    }

    public int getTotalBintang() {
        return this.totalBintang;
    }

    public void setTotalBintang(int totalBintang) {
        this.totalBintang = totalBintang;
    }

}

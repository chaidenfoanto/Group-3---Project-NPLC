package com.restfulnplc.nplcrestful.dto;

public class CardSkillDTO {
    private String namaKartu;
    private String rules;
    private int totalKartu;
    private String gambarKartu;

    public String getNamaKartu() {
        return namaKartu;
    }

    public void setNamaKartu(String namaKartu) {
        this.namaKartu = namaKartu;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public int getTotalKartu() {
        return totalKartu;
    }

    public void setTotalKartu(int totalKartu) {
        this.totalKartu = totalKartu;
    }

    public String getGambarKartu() {
        return gambarKartu;
    }

    public void setGambarKartu(String gambarKartu) {
        this.gambarKartu = gambarKartu;
    }
}

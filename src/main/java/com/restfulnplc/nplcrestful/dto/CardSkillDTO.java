package com.restfulnplc.nplcrestful.dto;

public class CardSkillDTO {
    private String namaKartu;
    private String rules;
    private int totalKartu;
    private byte[] gambarKartu;

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

    public byte[] getGambarKartu() {
        return gambarKartu;
    }

    public void setGambarKartu(byte[] gambarKartu) {
        this.gambarKartu = gambarKartu;
    }
}

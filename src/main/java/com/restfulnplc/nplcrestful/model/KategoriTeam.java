package com.restfulnplc.nplcrestful.model;

public enum KategoriTeam{
    KATEGORI1("Kategori 1"),
    KATEGORI2("Kategori 2");

    private final String kategoriTeam;

    KategoriTeam(String kategoriTeam) {
        this.kategoriTeam = kategoriTeam;
    }

    public String toString() {
        return kategoriTeam;
    }

    public static KategoriTeam fromString(String kategoriTeam) {
        for (KategoriTeam d : KategoriTeam.values()) {
            if (d.kategoriTeam.equalsIgnoreCase(kategoriTeam)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Unknown game type: " + kategoriTeam);
    }
}
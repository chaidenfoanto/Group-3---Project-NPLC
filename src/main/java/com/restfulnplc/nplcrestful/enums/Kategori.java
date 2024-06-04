package com.restfulnplc.nplcrestful.enums;

public enum Kategori{
    SINGLE("Single"),
    DUEL("Duel");

    private final String kategori;

    Kategori(String kategori) {
        this.kategori = kategori;
    }

    public String toString() {
        return kategori;
    }

    public static Kategori fromString(String kategori) {
        for (Kategori d : Kategori.values()) {
            if (d.kategori.equalsIgnoreCase(kategori)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Unknown game type: " + kategori);
    }
}
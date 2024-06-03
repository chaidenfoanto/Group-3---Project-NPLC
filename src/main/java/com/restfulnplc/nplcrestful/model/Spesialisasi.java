package com.restfulnplc.nplcrestful.model;

public enum Spesialisasi {
    ARTIFICIALINTELLIGENCE("Artificial Intelligence"),
    FULLSTACKDEVELOPER("Fullstack Developer");

    private final String spesialisasi;

    Spesialisasi(String spesialisasi) {
        this.spesialisasi = spesialisasi;
    }

    public String toString() {
        return spesialisasi;
    }

    public static Spesialisasi fromString(String spesialisasi) {
        for (Spesialisasi s : Spesialisasi.values()) {
            if (s.spesialisasi.equalsIgnoreCase(spesialisasi)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown specialization: " + spesialisasi);
    }
}


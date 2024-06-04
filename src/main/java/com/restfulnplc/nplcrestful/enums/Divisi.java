package com.restfulnplc.nplcrestful.enums;

public enum Divisi {
    KETUAACARA("Ketua Acara"),
    EVENT("Event"),
    BENDAHARA("Treasurer"),
    SEKRETARIS("Secretary"),
    PENGURUSKATEGORI1("Pengurus Kategori 1"),
    PENGURUSKATEGORI2("Pengurus Kategori 2"),
    LIAISONOFFICER("Liaison Officer"),
    MARKETINGANDSPONSORSHIP("Marketing & Sponsorship"),
    INVENTORY("Inventory"),
    PUBLICATIONANDDOCUMENTATION("Publication & Documentation");

    private final String divisi;

    Divisi(String divisi) {
        this.divisi = divisi;
    }

    public String toString() {
        return divisi;
    }

    public static Divisi fromString(String divisi) {
        for (Divisi d : Divisi.values()) {
            if (d.divisi.equalsIgnoreCase(divisi)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Unknown division: " + divisi);
    }
}

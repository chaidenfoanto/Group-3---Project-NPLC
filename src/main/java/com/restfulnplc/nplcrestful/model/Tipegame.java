package com.restfulnplc.nplcrestful.model;

public enum Tipegame {
    SINGLE("Single"),
    DUEL("Duel");

    private final String tipegame;

    Tipegame(String tipegame) {
        this.tipegame = tipegame;
    }

    public String toString() {
        return tipegame;
    }

    public static Tipegame fromString(String tipegame) {
        for (Tipegame d : Tipegame.values()) {
            if (d.tipegame.equalsIgnoreCase(tipegame)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Unknown game type: " + tipegame);
    }
}
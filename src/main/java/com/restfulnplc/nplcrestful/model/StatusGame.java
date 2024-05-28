package com.restfulnplc.nplcrestful.model;

public enum StatusGame {
    NOTSTARTED("Not Started"),
    INPROGRESS("In Progress"),
    DONE("Done");

    private final String statusgame;

    StatusGame(String statusgame) {
        this.statusgame = statusgame;
    }

    public String getStatusgame() {
        return statusgame;
    }

    public static StatusGame fromString(String statusgame) {
        for (StatusGame d : StatusGame.values()) {
            if (d.statusgame.equalsIgnoreCase(statusgame)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Unknown game type: " + statusgame);
    }
}
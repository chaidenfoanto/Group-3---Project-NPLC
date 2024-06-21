package com.restfulnplc.nplcrestful.model;

public enum MatchStatus {
    STARTED("Started"),
    FINISHED("Finished"),
    SUBMITTED("Submitted");

    private final String matchStatus;

    MatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public String toString() {
        return matchStatus;
    }

    public static MatchStatus fromString(String matchStatus) {
        for (MatchStatus d : MatchStatus.values()) {
            if (d.matchStatus.equalsIgnoreCase(matchStatus)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Unknown match status: " + matchStatus);
    }
}
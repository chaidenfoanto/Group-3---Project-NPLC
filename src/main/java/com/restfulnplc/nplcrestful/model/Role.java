package com.restfulnplc.nplcrestful.model;

public enum Role{
    PANITIA("Panitia"),
    ADMIN("Admin"),
    KETUA("Ketua"),
    PLAYERS("Players"),
    LOSINGLE("Penjaga Single"),
    LODUEL("Penjaga Duel");
    
    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String toString() {
        return role;
    }

    public static Role fromString(String role) {
        for (Role s : Role.values()) {
            if (s.role.equalsIgnoreCase(role)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + role);
    }
}
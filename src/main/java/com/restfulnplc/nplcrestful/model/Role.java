package com.restfulnplc.nplcrestful.model;

    public enum Role{
        PANITIA, PLAYERS;

    @Override
    public String toString(){
        switch(this){
            case PANITIA: return "Panitia";
            case PLAYERS: return "Players";
            default: throw new IllegalArgumentException();
        }
    }
}
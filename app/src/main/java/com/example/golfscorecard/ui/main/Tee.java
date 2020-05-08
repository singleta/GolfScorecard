package com.example.golfscorecard.ui.main;

public enum Tee {
    RED("Red"), WHITE("White"), YELLOW("Yellow");

    private String teeColour;

    Tee(String teeColour) {
        this.teeColour = teeColour;
    }

    public String getTeeColour() { return teeColour; }
}

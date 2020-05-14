package com.example.golfscorecard.buttons;

public enum Tee {
    RED("Red"), WHITE("White"), YELLOW("Yellow");

    private String teeColour;

    Tee(String teeColour) {
        this.teeColour = teeColour;
    }

    public String getTeeColour() { return teeColour; }
}

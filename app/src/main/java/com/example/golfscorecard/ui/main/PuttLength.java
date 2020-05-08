package com.example.golfscorecard.ui.main;

import com.example.golfscorecard.R;

public enum PuttLength {
    ZERO( "0 Putts", 0),
    SHORT("0-10", 1),
    MEDIUM( "10-20", 2),
    LONG("20-30", 3),
    HUGE( "30+", 4);


    private String puttName;
    private Integer distance;

    PuttLength(String puttName, Integer distance) {

        this.puttName = puttName;
        this.distance = distance;
    }



    public String getPuttName() {
        return puttName;
    }
    public Integer getDistance() { return distance; }
}

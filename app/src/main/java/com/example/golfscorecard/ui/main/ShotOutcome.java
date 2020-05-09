package com.example.golfscorecard.ui.main;

import com.example.golfscorecard.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ShotOutcome {
    LEFT(R.id.driveLeft,"Left", "L"),
    FAIRWAY(R.id.driveFairway,"Fair", "F"),
    RIGHT(R.id.driveRight,"Right", "R"),
    BUNKER(R.id.approachBunker,"Bunker", "B"),
    TREE(R.id.driveTree,"Tree", "T"),
    OB(R.id.driveOB,"OB", "OB"),
    GIR(R.id.approachHitGreen,"GIR", "GIR"),
    GREEN(R.id.green,"Green", "X"),
    PENALTY(R.id.drivePenalty,"Pen", "P"),
    SHORT(R.id.approachShort,"Short", "S"),
    MIDDLE(R.id.approachMiddle,"Middle", "M"),
    LONG(R.id.approachLong,"Long", "L"),
    FRINGE(R.id.approachFringe,"Fringe", "F"),
    FORTY(R.id.approach40,"0-40", "40"),
    EIGHTY(R.id.approach80,"40-80", "80"),
    ONE_TWENTY(R.id.approach120,"80-120", "120"),
    ONE_SIXTY(R.id.approach160,"120-160", "160"),
    ONE_SIXTY_PLUS(R.id.approach160Plus, "160+", "160+");

//    D_LEFT(R.id.driveLeft,"Left", "L"),
//    D_FAIRWAY(R.id.driveFairway,"Fair", "F"),
//    D_RIGHT(R.id.driveRight,"Right", "R"),
//    D_BUNKER(R.id.driveBunker,"Bunker", "B"),
//    D_TREE(R.id.driveTree,"Tree", "T"),
//    D_OB(R.id.driveOB,"OB", "OB"),
//    D_PENALTY(R.id.drivePenalty,"Pen", "P"),
//    D_SHORT(R.id.approachShort,"Short", "S"),
//    A_LEFT(R.id.approachLeft,"Left", "L"),
//    A_RIGHT(R.id.approachRight,"Right", "R"),
//    A_BUNKER(R.id.approachBunker,"Bunker", "B"),
//    A_TREE(R.id.approachTree,"Tree", "T"),
//    A_OB(R.id.approachOB,"OB", "OB"),
//    A_PENALTY(R.id.approachPenalty,"Pen", "P"),
//    A_SHORT(R.id.approachShort,"Short", "S"),
//    GIR(R.id.gir,"GIR", "GIR"),
//    GREEN(R.id.green,"Green", "X"),



    private int id;
    private String off;
    private String on;

    public static ShotOutcome[] approachOnly = new ShotOutcome[]{GREEN, GIR, MIDDLE, LONG, FRINGE, FORTY, EIGHTY, ONE_TWENTY, ONE_SIXTY, ONE_SIXTY_PLUS};

    ShotOutcome(int id, String off, String on) {
        this.id = id;
        this.off = off;
        this.on = on;
    }

    public String getOff() {
        return off;
    }

    public String getOn() {
        return on;
    }

    public int getId() {
        return id;
    }

    public static List<ShotOutcome> getApproachResults () {

        List<ShotOutcome> approachResults = new ArrayList<>();
        // Add all the buttons then remove the unwanted ones
        approachResults.addAll(Arrays.stream(ShotOutcome.values()).collect(Collectors.toList()));
        approachResults.remove(FAIRWAY);
        return approachResults;
    }

    public static List<ShotOutcome> getDriveResults () {

        List<ShotOutcome> driveResults = new ArrayList<>();
        // Add all the buttons then remove the unwanted ones
        driveResults.addAll(Arrays.stream(ShotOutcome.values()).collect(Collectors.toList()));
        for (ShotOutcome sr : approachOnly ) {
            driveResults.remove(sr);

        }

        return driveResults;
    }

    public static ShotOutcome[] getExclusiveApproachButtonNames() {
        return approachOnly;
    }
}

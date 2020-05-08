package com.example.golfscorecard.ui.main;

import com.example.golfscorecard.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ShotOutcome {
    LEFT(R.id.driveLeft,"Left", "L"),
    FAIRWAY(R.id.driveFairway,"Fair", "F"),
    GREEN(R.id.approachHitGreen,"Green", "GIR"),
    RIGHT(R.id.driveRight,"Right", "R"),
    BUNKER(R.id.approachBunker,"Bunker", "Sand"),
    TREE(R.id.driveTree,"Tree", "T"),
    OB(R.id.driveOB,"OB", "OB"),
    SEMI(R.id.driveSemi,"Semi", "0-5"),
    DEEP(R.id.driveDeep,"Deep", "5-10"),
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

    private int id;
    private String off;
    private String on;

    public static ShotOutcome[] approachOnly = new ShotOutcome[]{GREEN, MIDDLE, LONG, FRINGE, FORTY, EIGHTY, ONE_TWENTY, ONE_SIXTY, ONE_SIXTY_PLUS};

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

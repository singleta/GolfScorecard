package com.example.golfscorecard.shots;

import com.example.golfscorecard.R;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ShotOutcome {
    D_LEFT(R.string.dLeft, R.string.leftOff, R.string.leftOn),
    D_FAIRWAY(R.string.dFairway, R.string.fairwayOff, R.string.fairwayOn),
    D_RIGHT(R.string.dRight, R.string.rightOff, R.string.rightOn),
    D_BUNKER(R.string.dSand, R.string.sandOff, R.string.sandOn),
    D_TREE(R.string.dTree, R.string.treeOff, R.string.treeOn),
    D_OB(R.string.dOB, R.string.obOff, R.string.obOn),
    D_PENALTY(R.string.dPenalty, R.string.penaltyOff, R.string.penaltyOn),
    D_SHORT(R.string.dShort, R.string.shortOff, R.string.shortOn),
    A_LEFT(R.string.aLeft, R.string.leftOff, R.string.leftOn),
    A_RIGHT(R.string.aRight, R.string.rightOff, R.string.rightOn),
    A_BUNKER(R.string.aSand, R.string.sandOff, R.string.sandOn),
    A_TREE(R.string.aTree, R.string.treeOff, R.string.treeOn),
    A_OB(R.string.aOB, R.string.obOff, R.string.obOn),
    A_GIR(R.string.gir, R.string.girOff, R.string.girOn),
    A_GREEN(R.string.green, R.string.greenOff, R.string.greenOn),
    A_PENALTY(R.string.aPenalty, R.string.penaltyOff, R.string.penaltyOn),
    A_SHORT(R.string.aShort, R.string.shortOff, R.string.shortOn),
    A_MIDDLE(R.string.middle, R.string.middleOff, R.string.middleOn),
    A_LONG(R.string.longApproach, R.string.longOff, R.string.longOn),
    A_FRINGE(R.string.fringe, R.string.fringeOff, R.string.fringeOn),
    A_FORTY(R.string.approach40off, R.string.approach40off, R.string.approach40on),
    A_EIGHTY(R.string.approach80off, R.string.approach80off, R.string.approach80on),
    A_ONE_TWENTY(R.string.approach120off, R.string.approach120off, R.string.approach120on),
    A_ONE_SIXTY(R.string.approach160off, R.string.approach160off, R.string.approach160on),
    A_TWO_HUNDRED(R.string.approach200Off, R.string.approach200Off, R.string.approach200On),
    A_TWO_HUNDRED_PLUS(R.string.approach200PlusOff, R.string.approach200PlusOff, R.string.approach200PlusOn);

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
    private int off;
    private int on;

    public static ShotOutcome[] approachOnly  = new ShotOutcome[]{A_LEFT, A_RIGHT, A_BUNKER, A_TREE,
            A_OB, A_GREEN, A_GIR, A_PENALTY, A_SHORT, A_MIDDLE, A_LONG, A_FRINGE, A_FORTY, A_EIGHTY,
            A_ONE_TWENTY, A_ONE_SIXTY, A_TWO_HUNDRED, A_TWO_HUNDRED_PLUS};

    public static ShotOutcome[] driveOnly = new ShotOutcome[] {D_LEFT, D_FAIRWAY, D_RIGHT, D_BUNKER,
            D_TREE, D_OB, D_PENALTY, D_SHORT};

    public static ShotOutcome[] approachDistances = new ShotOutcome[]{ A_FORTY,  A_EIGHTY,  A_ONE_TWENTY,
            A_ONE_SIXTY,  A_TWO_HUNDRED, A_TWO_HUNDRED_PLUS};

    public static ShotOutcome[] approachOutcomes = new ShotOutcome[]{A_LEFT, A_RIGHT, A_BUNKER, A_TREE,
            A_OB, A_GREEN, A_GIR, A_PENALTY, A_SHORT, A_MIDDLE, A_LONG, A_FRINGE};


    ShotOutcome(int id, int off, int on) {
        this.id = id;
        this.off = off;
        this.on = on;
    }

    public int getOff() {
        return off;
    }

    public int getOn() {
        return on;
    }

    public int getId() {
        return id;
    }

    public static List<ShotOutcome> getApproachResults () {
        return Arrays.stream(approachOnly).collect(Collectors.toList());
    }

    public static List<ShotOutcome> getDriveResults () {
        return Arrays.stream(driveOnly).collect(Collectors.toList());
    }

}

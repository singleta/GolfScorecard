package com.example.golfscorecard.buttons;

import android.view.View;
import android.widget.ToggleButton;

import com.example.golfscorecard.R;
import com.example.golfscorecard.shots.ShotOutcome;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static com.example.golfscorecard.buttons.Tee.RED;
import static com.example.golfscorecard.buttons.Tee.WHITE;
import static com.example.golfscorecard.buttons.Tee.YELLOW;
import static com.example.golfscorecard.shots.ShotOutcome.A_BUNKER;
import static com.example.golfscorecard.shots.ShotOutcome.A_EIGHTY;
import static com.example.golfscorecard.shots.ShotOutcome.A_FORTY;
import static com.example.golfscorecard.shots.ShotOutcome.A_FRINGE;
import static com.example.golfscorecard.shots.ShotOutcome.A_GIR;
import static com.example.golfscorecard.shots.ShotOutcome.A_GREEN;
import static com.example.golfscorecard.shots.ShotOutcome.A_LEFT;
import static com.example.golfscorecard.shots.ShotOutcome.A_LONG;
import static com.example.golfscorecard.shots.ShotOutcome.A_MIDDLE;
import static com.example.golfscorecard.shots.ShotOutcome.A_OB;
import static com.example.golfscorecard.shots.ShotOutcome.A_ONE_SIXTY;
import static com.example.golfscorecard.shots.ShotOutcome.A_ONE_TWENTY;
import static com.example.golfscorecard.shots.ShotOutcome.A_TWO_HUNDRED;
import static com.example.golfscorecard.shots.ShotOutcome.A_TWO_HUNDRED_PLUS;
import static com.example.golfscorecard.shots.ShotOutcome.A_PENALTY;
import static com.example.golfscorecard.shots.ShotOutcome.A_RIGHT;
import static com.example.golfscorecard.shots.ShotOutcome.A_SHORT;
import static com.example.golfscorecard.shots.ShotOutcome.A_TREE;
import static com.example.golfscorecard.shots.ShotOutcome.D_BUNKER;
import static com.example.golfscorecard.shots.ShotOutcome.D_FAIRWAY;
import static com.example.golfscorecard.shots.ShotOutcome.D_LEFT;
import static com.example.golfscorecard.shots.ShotOutcome.D_OB;
import static com.example.golfscorecard.shots.ShotOutcome.D_PENALTY;
import static com.example.golfscorecard.shots.ShotOutcome.D_RIGHT;
import static com.example.golfscorecard.shots.ShotOutcome.D_TREE;
import static com.example.golfscorecard.shots.ShotOutcome.approachDistances;
import static com.example.golfscorecard.shots.ShotOutcome.approachOutcomes;

public class ButtonActions {

    private View view;
    static View staticView;
    ButtonActions(View view) {
        this.view = view;
    }

    void toggleButtonStates(ToggleButton tb, Map<String, View> driveButtons, Map<String, View> approachButtons) {
        Map<Integer, ToggleButton> buttons = new HashMap<>();

        driveButtons.forEach((k, v) -> {
            buttons.put(v.getId(), (ToggleButton) v);
        });

        int id = tb.getId();
        if (id == D_LEFT.getId() || id == D_RIGHT.getId()) {
            removeButtons(buttons, D_TREE, D_OB, D_PENALTY, D_BUNKER);
        } else if (id == D_BUNKER.getId() || id == D_OB.getId() || id == D_PENALTY.getId() || id == D_TREE.getId()) {
            removeButtons(buttons, D_LEFT, D_RIGHT);
        } else if (id == D_FAIRWAY.getId()) {
            removeButtons(buttons);
        } else {
            buttons.clear();
        }

        approachButtons.forEach((k, v) -> {
            buttons.put(v.getId(), (ToggleButton)v);
        });

        if (id == A_LEFT.getId() || id == A_RIGHT.getId()) {
            removeButtons(buttons, approachDistances, A_TREE, A_OB, A_PENALTY, A_BUNKER);
        } else if (id == A_BUNKER.getId() || id == A_OB.getId() || id == A_PENALTY.getId() || id == A_TREE.getId()) {
            removeButtons(buttons, approachDistances, A_LEFT, A_RIGHT, A_SHORT, A_GIR);
        } else if (id == A_GIR.getId()) {
            removeButtons(buttons, approachDistances, A_LEFT, A_RIGHT, A_PENALTY, A_BUNKER, A_TREE, A_MIDDLE, A_LONG, A_SHORT, A_FRINGE);
        } else if (id == A_SHORT.getId() || id == A_MIDDLE.getId() || id == A_LONG.getId() || id == A_FRINGE.getId()) {
            removeButtons(buttons, approachDistances, A_RIGHT, A_BUNKER, A_PENALTY, A_GIR);
        } else if (id == A_FORTY.getId() || id == A_EIGHTY.getId() || id == A_ONE_TWENTY.getId() ||
                id == A_ONE_SIXTY.getId() || id == A_TWO_HUNDRED.getId() || id == A_TWO_HUNDRED_PLUS.getId()) {
            removeButtons(buttons, approachOutcomes);
        } else if (id == A_GREEN.getId()) {
            removeButtons(buttons, approachDistances);
        }

//            case SHORT:
//                buttons.remove(text);
//                break;

        buttons.remove(id);

        turnOffToggleButtons(buttons);

    }


    private void removeButtons(Map<Integer, ToggleButton> buttons, ShotOutcome... names) {
        System.out.println("remove the buttons");
        Arrays.stream(names).forEach(so -> {
            buttons.remove(so.getId());
        });
    }

    private void removeButtons(Map<Integer, ToggleButton> buttons, ShotOutcome[] shotOutcomes, ShotOutcome... names) {
        ShotOutcome[] newArray = Stream.concat(Arrays.stream(shotOutcomes), Arrays.stream(names))
                .toArray(ShotOutcome[]::new);
        removeButtons(buttons, newArray);
    }


    private void turnOffToggleButtons(Map<Integer, ToggleButton> buttons) {
        buttons.forEach((k, v) -> v.setChecked(false));
    }

     void resetPuttButtons(Map<String, View> puttButtons) {
        puttButtons.forEach(this::setPuttsToZero);
    }

//    public void toggleTeeVisibility(View view, Map<String, View> buttons) {
//        Map<String, View> twoTeeButtons = buttons;
//        if (view.getTag() == RED.getTeeColour()) {
//            twoTeeButtons.remove(RED.getTeeColour());
//            changeVisibility(twoTeeButtons, RED);
////                changeVisibility(Objects.requireNonNull(buttons.get(WHITE.getTeeColour())), Objects.requireNonNull(buttons.get(YELLOW.getTeeColour())));
//        } else if (view.getTag() == WHITE.getTeeColour()) {
//            changeVisibility(Objects.requireNonNull(buttons.get(RED.getTeeColour())), Objects.requireNonNull(buttons.get(YELLOW.getTeeColour())));
//        } else {
//            changeVisibility(Objects.requireNonNull(buttons.get(RED.getTeeColour())), Objects.requireNonNull(buttons.get(WHITE.getTeeColour())));
//        }
//    }
//    public static void toggleTeeVisibility(View view) {
//        if (view.getId() == R.id.redTee) {
//            changeVisibility(R.id.whiteTee, R.id.yellowTee);
////                changeVisibility(Objects.requireNonNull(buttons.get(WHITE.getTeeColour())), Objects.requireNonNull(buttons.get(YELLOW.getTeeColour())));
//        } else if (view.getId() == R.id.whiteTee) {
//            changeVisibility(R.id.redTee, R.id.yellowTee);
//        } else {
//            changeVisibility(R.id.redTee, R.id.whiteTee);
//        }
//    }
//
//    private static void changeVisibility(int id1, int id2) {
//
//        View v1 = staticView.findViewById(id1);
//        View v2 = staticView.findViewById(id2);
//        if (v1.getVisibility() == View.VISIBLE) {
//            v1.setVisibility(View.INVISIBLE);
//        } else {
//            v1.setVisibility(View.VISIBLE);
//        }
//        if (v2.getVisibility() == View.VISIBLE) {
//            v2.setVisibility(View.INVISIBLE);
//        } else {
//            v2.setVisibility(View.VISIBLE);
//        }
//    }
//    private void changeVisibility(Map<String, View> twoButtons, Tee tee) {
//        View v1;
//        View v2;
//        switch (tee) {
//            case RED:
//                v1 = twoButtons.get(WHITE.getTeeColour());
//                v2 = twoButtons.get(YELLOW.getTeeColour());
//                break;
//            case WHITE:
//                v1 = twoButtons.get(RED.getTeeColour());
//                v2 = twoButtons.get(YELLOW.getTeeColour());
//                break;
//            case YELLOW:
//                v1 = twoButtons.get(WHITE.getTeeColour());
//                v2 = twoButtons.get(RED.getTeeColour());
//                break;
//            default:
//                throw new IllegalStateException("Unexpected value: " + tee);
//        }
//        assert v1 != null;
//        if (v1.getVisibility() == View.VISIBLE) {
//            v1.setVisibility(View.INVISIBLE);
//        } else {
//            v1.setVisibility(View.VISIBLE);
//        }
//        if (v2.getVisibility() == View.VISIBLE) {
//            v2.setVisibility(View.INVISIBLE);
//        } else {
//            v2.setVisibility(View.VISIBLE);
//        }
//    }

    void setPuttsToZero(String k, View v) {
        PuttButton pb = ((PuttButton) v);
        pb.setText("0");
        pb.performLongClick();

    }



}

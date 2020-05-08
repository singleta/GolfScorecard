package com.example.golfscorecard;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ToggleButton;

import com.example.golfscorecard.buttons.PuttButton;
import com.example.golfscorecard.ui.main.HoleScoreFragment;
import com.example.golfscorecard.ui.main.PuttLength;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import androidx.constraintlayout.widget.ConstraintLayout;

public class SaveHoleData {

    private static final String APPROACH = "approach";
    private static final String FAIRWAY = "fairway";
    private static final String PUTTING = "putting";

    List<HoleScoreFragment> holePages;
    HoleDetails holeDetails;

    public SaveHoleData(List<HoleScoreFragment> holePages) {
        this.holePages = holePages;
    }

    public void saveData() {
        for (HoleScoreFragment holePage : holePages) {
            saveHoleData(holePage);
            System.out.println(holeDetails.toString());
        }
    }

    public void saveHoleData(HoleScoreFragment holeScoreFragment) {
        holeDetails = new HoleDetails();
        holeDetails.setHole(holeScoreFragment.get);
        ConstraintLayout cl = (ConstraintLayout) holeScoreFragment.getView();
        int childCount = cl.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = cl.getChildAt(i);
            if (v instanceof TableLayout) {
                processTable(v);
            }
        }
        System.out.println("Saving view data");
    }

    public void processTable(View v) {
        TableLayout tl = (TableLayout) v;
        int childCount = tl.getChildCount();
        for (int i = 0; i < childCount; i++) {
            TableRow tr = (TableRow) tl.getChildAt(i);
            for (int j = 0; j < tr.getChildCount(); j++) {
                View child = tr.getChildAt(j);
                if (child instanceof ToggleButton) {
                    if (((ToggleButton) child).isChecked())
                        saveToggleButtonValue(child, tl);
                } else if (child instanceof ImageButton) {
                    if (((ImageButton) child).getVisibility() == View.VISIBLE) {
                        saveTeeValue(child);
                    }
                } else if (child instanceof PuttButton) {
                     savePuttButtonValue(child);
                }
            }
        }
        saveFirstPuttDistance();
    }

    private List<PuttLength> puttDistances = new ArrayList<>();
    private void savePuttButtonValue(View v) {
        PuttButton puttButton = (PuttButton)v;
        if (puttButton.getText().length()==1) {
            puttDistances.add(puttButton.getPuttLength());
        }
    }

    private void saveFirstPuttDistance() {
        Optional<PuttLength> puttLength = puttDistances.stream().max((putt1, putt2) -> putt1.getDistance().compareTo(putt2.getDistance()));
        puttLength.ifPresent(this::accept);
    }

    private void saveTeeValue(View child) {
        ImageButton imageButton = (ImageButton)child;
        holeDetails.setTees(String.valueOf(imageButton.getTooltipText()));
    }

    private void saveToggleButtonValue(View v, View tableLayout) {
        ToggleButton tb = (ToggleButton) v;
        switch (tableLayout.getId()) {
            case R.id.driveTableLayout:
                holeDetails.setFairway(addToStringValues(tb, holeDetails.getFairway()));
                break;
            case R.id.approachTableLayout:
                holeDetails.setApproach(addToStringValues(tb, holeDetails.getApproach()));
                break;
        }


    }

    private StringBuilder addToStringValues(ToggleButton tb, StringBuilder values) {
        if (values == null) {
            values = new StringBuilder(tb.getTextOn());
        } else {
            values.append(tb.getTextOn());
        }
        return values;
    }


    private void accept(PuttLength puttLength1) {
        holeDetails.setDistanceToHole(puttLength1.getPuttName());
    }
}

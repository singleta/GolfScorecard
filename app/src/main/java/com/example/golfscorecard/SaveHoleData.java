package com.example.golfscorecard;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.golfscorecard.buttons.PuttButton;
import com.example.golfscorecard.ui.main.HoleScoreFragment;
import com.example.golfscorecard.shots.PuttLength;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import androidx.constraintlayout.widget.ConstraintLayout;

import static com.example.golfscorecard.ui.main.HoleScoreFragment.ARG_SECTION_NUMBER;

public class SaveHoleData {

    private static final String APPROACH = "approach";
    private static final String FAIRWAY = "fairway";
    private static final String PUTTING = "putting";
    private List<PuttLength> puttDistances = new ArrayList<>();

    List<HoleScoreFragment> holePages;
    HoleDetails holeDetails;


    public SaveHoleData(List<HoleScoreFragment> holePages) {
        this.holePages = holePages;
    }

    public List<HoleDetails> saveData() {
        List<HoleDetails> holeDetailsList = new ArrayList<>(18);
        for (HoleScoreFragment holePage : holePages) {
            saveHoleData(holePage);
            System.out.println(holeDetails.toString());
            holeDetailsList.add(holeDetails);
        }
        return holeDetailsList;
    }

    public void saveHoleData(HoleScoreFragment holeScoreFragment) {
        holeDetails = new HoleDetails();
        holeDetails.setDate(LocalDate.now());
        holeDetails.setHole((Integer) holeScoreFragment.getArguments().get(ARG_SECTION_NUMBER));
        holeDetails.setPar(holeDetails.parsByHole[holeDetails.getHole() - 1]);
        ConstraintLayout cl = (ConstraintLayout) holeScoreFragment.getView();
        int childCount = cl.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = cl.getChildAt(i);
            if (v instanceof TableLayout) {
                processTable(v);
            } else { // It's the totals
                saveTotal(v);
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

    protected void savePuttButtonValue(View v) {
        PuttButton puttButton = (PuttButton) v;
        if (puttButton.getText().length() == 1) {
            puttDistances.add(puttButton.getPuttLength());
        }
    }

    protected void saveFirstPuttDistance() {
        Optional<PuttLength> puttLength = puttDistances.stream().max((putt1, putt2) -> putt1.getDistance().compareTo(putt2.getDistance()));
        puttLength.ifPresent(this::accept);
    }

    private void saveTeeValue(View child) {
        ImageButton imageButton = (ImageButton) child;
        holeDetails.setTees(String.valueOf(imageButton.getTooltipText()));
    }

    private void saveToggleButtonValue(View v, View tableLayout) {
        ToggleButton tb = (ToggleButton) v;
        String text = tb.getTextOn().toString();
        switch (tableLayout.getId()) {
            case R.id.driveTableLayout:
                holeDetails.setFairway(addToStringValues(text, holeDetails.getFairway()));
                break;
            case R.id.approachTableLayout:
                // If the button value contains a number it's the approach distance
                // otherwise it's the shot result
                if (text.contains("0")) {
                    holeDetails.setApproachDistance(text);
                } else {
                    holeDetails.setApproach(addToStringValues(text, holeDetails.getApproach()));
                }
                break;
        }
    }

    private void saveTotal(View view) {
        String text;
        if (view.getId() == R.id.totalsLayout) {
            LinearLayout layout = (LinearLayout)view;
            int children = layout.getChildCount();
            for (int i=0; i<children; i++) {
                View v = layout.getChildAt(i);
                if (v instanceof TextView) {
                    text = ((TextView) v).getText().toString();
                    if (!text.equals("") && Character.isDigit(text.charAt(0))) {
                        holeDetails.setTotalPutts(text);
                    }
                } else if (v instanceof Spinner) {
                    text = ((Spinner) v).getSelectedItem().toString();
                    holeDetails.setScore(text);
                }
            }
        }
    }

    private StringBuilder addToStringValues(String text, StringBuilder values) {
        if (values == null) {
            values = new StringBuilder(text);
        } else {
            values.append(text);
        }
        return values;
    }


    private void accept(PuttLength puttLength1) {
        holeDetails.setDistanceToHole(puttLength1.getPuttName());
    }

    public List<PuttLength> getPuttDistances() {
        return puttDistances;
    }
}

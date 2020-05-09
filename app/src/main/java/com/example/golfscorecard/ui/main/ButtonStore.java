package com.example.golfscorecard.ui.main;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.golfscorecard.R;
import com.example.golfscorecard.buttons.PuttButton;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ButtonStore {

    private Map<String, View> driveButtons = new LinkedHashMap<>();
    private Map<String, View> approachButtons = new LinkedHashMap<>();
    private Map<String, View> puttButtons = new LinkedHashMap<>();
    private Map<String, View> teeButtons = new LinkedHashMap<>();
    private TextView totalPuttsView;
    private int totalPutts;
    private int redId;
    private int whiteId;
    private int yellowId;
    int randomId = new Random().nextInt();

    private static String ZERO = "0 Putts";
    //    public static final String SHORT="0-10";
//    public static final String MEDIUM="10-20";
//    public static final String LONG="20-30";
//    public static final String HUGE="30+ ";
    public static final String D_LEFT = "dLeft";
    public static final String D_RIGHT = "dRight";
    public static final String D_FAIRWAY = "dFair";
    public static final String D_BUNKER = "dBunker";
    public static final String D_TREE = "dTree";
    public static final String D_OB = "dOB";
    public static final String D_PEN = "dPen";
    public static final String D_SHORT = "dShort";
    public static final String A_LEFT = "aLeft";
    public static final String A_RIGHT = "aRight";
    public static final String A_FAIRWAY = "aFair";
    public static final String A_BUNKER = "aBunker";
    public static final String A_TREE = "aTree";
    public static final String A_OB = "aOB";
    public static final String A_PEN = "aPen";
    public static final String A_SHORT = "aShort";
    public static final String A_MIDDLE = "aMiddle";
    public static final String A_LONG = "aLong";
    public static final String A_FRINGE = "aFringe";
    public static final String FORTY = "0-40";
    public static final String EIGHTY = "40-80";
    public static final String ONE_TWENTY = "80-120";
    public static final String ONE_SIXTY = "120-160";
    public static final String ONE_SIXTY_PLUS = "160+";

    private View view;

    public ButtonStore(View view) {
        this.view = view;
        setupButtons();
    }

    private static void setPuttsToZero(String k, View v) {
        PuttButton pb = ((PuttButton) v);
        pb.setText("0");
        pb.performLongClick();

    }


    private void setupButtons() {

        setupTeeButtons();
        setupPuttButtons();
        setupDriveButtons();
        setupApproachButtons();
        totalPuttsView = new TextView(view.getContext());
        totalPuttsView.setWidth(40);
        totalPuttsView.setTextColor(Color.BLUE);

    }

    public void setupDriveButtons() {
        List<ShotOutcome> driveResults = ShotOutcome.getDriveResults();
        driveButtons = createShotOutcomeButtons(driveResults);
    }

    private void setupApproachButtons() {
        List<ShotOutcome> approachResults = ShotOutcome.getApproachResults();
        approachButtons = createShotOutcomeButtons(approachResults);
    }

    private Map<String, View> createShotOutcomeButtons(List<ShotOutcome> shotOutcomes) {
        Map<String, View> toggleButtons = new LinkedHashMap<>();
        // Create Buttons
        shotOutcomes.forEach(shotOutcome -> {
            ToggleButton toggleButton = new ToggleButton(view.getContext());
            String label = shotOutcome.getOff();
//            String key = shotOutcome.getKey();
            toggleButton.setText(label);
            toggleButton.setTextOff(label);
            toggleButton.setTextOn(shotOutcome.getOn());
            toggleButtons.put(label, toggleButton);
            toggleButton.setOnClickListener(new ToggleButton.OnClickListener() {

                @Override
                public void onClick(View view) {
                    toggleDriveButtonStates(toggleButton);
                }
            });
        });
        return toggleButtons;
    }

    private void toggleDriveButtonStates(ToggleButton tb) {
        Map<String, ToggleButton> buttons = new HashMap<>();
        approachButtons.forEach((k, v) -> {
            buttons.put(k, (ToggleButton) v);
        });

        String text = String.valueOf(tb.getText());
        switch (text) {
            case D_FAIRWAY:
                buttons.remove(text);
                break;
            case D_LEFT:
            case D_RIGHT:
                removeButtons(buttons, text,  D_TREE, D_OB, D_PEN, D_BUNKER, D_SHORT);
                break;
            case D_BUNKER:
            case D_OB:
            case D_PEN:
            case D_TREE:
                removeButtons(buttons, text, D_LEFT, D_RIGHT);
                break;
            case D_SHORT:
                buttons.remove(text);
                break;
        }
        turnOffToggleButtons(buttons);

    }


    private void removeButtons(Map<String, ToggleButton> buttons, String... names) {
        System.out.println("remove the buttons");
        Arrays.stream(names).forEach(buttons::remove);
    }


    private void turnOffToggleButtons(Map<String, ToggleButton> buttons) {
        buttons.forEach((k, v) -> v.setChecked(false));
    }


    private void setupTeeButtons() {
        for (Tee tee : Tee.values()) {
            ImageButton teeImageButton = new ImageButton(view.getContext());
            switch (tee) {
                case RED:
                    teeImageButton.setImageResource(R.drawable.redtee);
                    teeImageButton.setTooltipText("Winter/Red");
                    teeImageButton.setId(randomId);
                    redId = teeImageButton.getId();
                    break;
                case WHITE:
                    teeImageButton.setImageResource(R.drawable.whitetee);
                    teeImageButton.setTooltipText("White");
                    teeImageButton.setId(randomId + 1);
                    whiteId = teeImageButton.getId();
                    break;
                case YELLOW:
                    teeImageButton.setImageResource(R.drawable.yellowtee);
                    teeImageButton.setTooltipText("Yellow");
                    teeImageButton.setId(randomId + 2);
                    yellowId = teeImageButton.getId();
                    break;
            }
            teeImageButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleTeeVisibility(view);

                    Toast.makeText(view.getContext(), "tee selected", Toast.LENGTH_SHORT).show();
                }


            });

            teeImageButton.setOnLongClickListener(new Button.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    toggleTeeVisibility(view);
                    Toast.makeText(view.getContext(), "tee de-selected", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            teeButtons.put(tee.getTeeColour(), teeImageButton);
        }
    }

    private void setupPuttButtons() {
        for (PuttLength puttLength : PuttLength.values()) {
            final String puttName = puttLength.getPuttName();
            PuttButton b = new PuttButton(view.getContext(), puttLength);
            if (puttLength == PuttLength.ZERO) {
                b.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        totalPuttsView.setText("0");
                        resetPuttButtons();
                        b.setText(puttLength.getPuttName());
                    }
                });
            } else {
                b.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changePuttCount(view, puttLength, true);
                    }


//                Toast.makeText(view.getContext(), "increase putts", Toast.LENGTH_SHORT).show();

                });

                b.setOnLongClickListener(new Button.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        changePuttCount(view, puttLength, false);
                        return true;
                    }
                });
            }
//                Toast.makeText(view.getContext(), "decrease putts", Toast.LENGTH_SHORT).show();
            puttButtons.put(puttName, b);
        }

        // Add  Putts Button
    }

    public void changePuttCount(View v, PuttLength puttLength, boolean increase) {

        PuttButton puttButton = (PuttButton) v;
        int i = -1;
        if (increase) {
            i = 1;
        }
        int numberOfPutts = 0;
        switch (puttLength) {
            case SHORT:
                numberOfPutts = Math.max(getShortPutts() + i, 0);
                setShortPutts(numberOfPutts);
                break;

            case MEDIUM:
                numberOfPutts = Math.max(getMediumPutts() + i, 0);
                setMediumPutts(numberOfPutts);
                break;
            case LONG:
                numberOfPutts = Math.max(getLongPutts() + i, 0);
                setLongPutts(numberOfPutts);
                break;
            case HUGE:
                numberOfPutts = Math.max(getHugePutts() + i, 0);
                setHugePutts(numberOfPutts);
                break;
        }
        String text = String.valueOf(numberOfPutts);
        if (numberOfPutts == 0) {
            text = puttLength.getPuttName();
        }
        puttButton.setText(text);
        setTotalPutts(Math.max(getTotalPutts() + i, 0));
        totalPuttsView.setText(String.valueOf(getTotalPutts()));

    }

    private void resetPuttButtons() {
        puttButtons.forEach(ButtonStore::setPuttsToZero);
    }

    private void toggleTeeVisibility(View view) {
        if (view.getId() == redId) {
            changeVisibility(whiteId, yellowId);
        } else if (view.getId() == whiteId) {
            changeVisibility(redId, yellowId);
        } else {
            changeVisibility(redId, whiteId);
        }
    }

    private void changeVisibility(int id1, int id2) {
        View v1 = view.findViewById(id1);
        View v2 = view.findViewById(id2);
        if (v1.getVisibility() == View.VISIBLE) {
            v1.setVisibility(View.INVISIBLE);
        } else {
            v1.setVisibility(View.VISIBLE);
        }
        if (v2.getVisibility() == View.VISIBLE) {
            v2.setVisibility(View.INVISIBLE);
        } else {
            v2.setVisibility(View.VISIBLE);
        }
    }

    public Map<String, View> getTeeButtons() {
        return teeButtons;
    }

    public Map<String, View> getDriveButtons() {
        return driveButtons;
    }

    public Map<String, View> getApproachButtons() {
        return approachButtons;
    }

    public Map<String, View> getPuttButtons() {
        return puttButtons;
    }

    public View getView() {
        return view;
    }

    public int getShortPutts() {
        return shortPutts;
    }

    public void setShortPutts(int shortPutts) {
        this.shortPutts = shortPutts;
    }

    public int getMediumPutts() {
        return mediumPutts;
    }

    public void setMediumPutts(int mediumPutts) {
        this.mediumPutts = mediumPutts;
    }

    public int getLongPutts() {
        return longPutts;
    }

    public void setLongPutts(int longPutts) {
        this.longPutts = longPutts;
    }

    public int getHugePutts() {
        return hugePutts;
    }

    public void setHugePutts(int hugePutts) {
        this.hugePutts = hugePutts;
    }

    public int getTotalPutts() {
        return totalPutts;
    }

    public void setTotalPutts(int totalPutts) {
        this.totalPutts = totalPutts;
    }

    public TextView getTotalPuttsView() {
        return totalPuttsView;
    }

    private int shortPutts;
    private int mediumPutts;
    private int longPutts;
    private int hugePutts;
}

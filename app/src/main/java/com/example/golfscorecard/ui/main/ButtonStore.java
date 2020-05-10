package com.example.golfscorecard.ui.main;

import android.graphics.Color;
import android.graphics.PathEffect;
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
import java.util.stream.Stream;

import static com.example.golfscorecard.ui.main.ShotOutcome.A_BUNKER;
import static com.example.golfscorecard.ui.main.ShotOutcome.A_EIGHTY;
import static com.example.golfscorecard.ui.main.ShotOutcome.A_FORTY;
import static com.example.golfscorecard.ui.main.ShotOutcome.A_FRINGE;
import static com.example.golfscorecard.ui.main.ShotOutcome.A_GIR;
import static com.example.golfscorecard.ui.main.ShotOutcome.A_GREEN;
import static com.example.golfscorecard.ui.main.ShotOutcome.A_LEFT;
import static com.example.golfscorecard.ui.main.ShotOutcome.A_LONG;
import static com.example.golfscorecard.ui.main.ShotOutcome.A_MIDDLE;
import static com.example.golfscorecard.ui.main.ShotOutcome.A_OB;
import static com.example.golfscorecard.ui.main.ShotOutcome.A_ONE_SIXTY;
import static com.example.golfscorecard.ui.main.ShotOutcome.A_ONE_SIXTY_PLUS;
import static com.example.golfscorecard.ui.main.ShotOutcome.A_ONE_TWENTY;
import static com.example.golfscorecard.ui.main.ShotOutcome.A_PENALTY;
import static com.example.golfscorecard.ui.main.ShotOutcome.A_RIGHT;
import static com.example.golfscorecard.ui.main.ShotOutcome.A_SHORT;
import static com.example.golfscorecard.ui.main.ShotOutcome.A_TREE;
import static com.example.golfscorecard.ui.main.ShotOutcome.D_BUNKER;
import static com.example.golfscorecard.ui.main.ShotOutcome.D_FAIRWAY;
import static com.example.golfscorecard.ui.main.ShotOutcome.D_LEFT;
import static com.example.golfscorecard.ui.main.ShotOutcome.D_OB;
import static com.example.golfscorecard.ui.main.ShotOutcome.D_PENALTY;
import static com.example.golfscorecard.ui.main.ShotOutcome.D_RIGHT;
import static com.example.golfscorecard.ui.main.ShotOutcome.D_SHORT;
import static com.example.golfscorecard.ui.main.ShotOutcome.D_TREE;
import static com.example.golfscorecard.ui.main.ShotOutcome.approachDistances;
import static com.example.golfscorecard.ui.main.ShotOutcome.approachOutcomes;

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
    public static final int DLEFT = D_LEFT.getId();
    public static final int DRIGHT = D_RIGHT.getId();
    public static final int DFAIRWAY = D_FAIRWAY.getId();
    public static final int DBUNKER = D_BUNKER.getId();
    public static final int DTREE = D_TREE.getId();
    public static final int DOB = D_OB.getId();
    public static final int DPEN = D_PENALTY.getId();
    public static final int DSHORT = D_SHORT.getId();
    public static final int ALEFT = A_LEFT.getId();
    public static final int ARIGHT = A_RIGHT.getId();
    public static final int ABUNKER = A_BUNKER.getId();
    public static final int ATREE = A_TREE.getId();
    public static final int AOB = A_OB.getId();
    public static final int APEN = A_PENALTY.getId();
    public static final int ASHORT = A_SHORT.getId();
    public static final int AMIDDLE = A_MIDDLE.getId();
    public static final int ALONG = A_LONG.getId();

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
            String label = toggleButton.getContext().getResources().getString(shotOutcome.getOff());
//            String key = shotOutcome.getKey();
            toggleButton.setId(shotOutcome.getId());
            toggleButton.setText(label);
            toggleButton.setTextOff(label);
            toggleButton.setTextOn(toggleButton.getContext().getResources().getString(shotOutcome.getOn()));
            toggleButtons.put(shotOutcome.name(), toggleButton);
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
                id == A_ONE_SIXTY.getId() || id == A_ONE_SIXTY_PLUS.getId()) {
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

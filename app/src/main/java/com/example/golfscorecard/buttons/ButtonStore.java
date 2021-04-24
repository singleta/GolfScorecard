package com.example.golfscorecard.buttons;

import android.app.Application;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.golfscorecard.R;
import com.example.golfscorecard.databinding.FragmentHoleTemplateBinding;
import com.example.golfscorecard.shots.PuttLength;
import com.example.golfscorecard.shots.ShotOutcome;
import com.example.golfscorecard.ui.main.HoleScoreFragment;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import static com.example.golfscorecard.shots.ShotOutcome.A_BUNKER;
import static com.example.golfscorecard.shots.ShotOutcome.A_LEFT;
import static com.example.golfscorecard.shots.ShotOutcome.A_LONG;
import static com.example.golfscorecard.shots.ShotOutcome.A_MIDDLE;
import static com.example.golfscorecard.shots.ShotOutcome.A_OB;
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
import static com.example.golfscorecard.shots.ShotOutcome.D_SHORT;
import static com.example.golfscorecard.shots.ShotOutcome.D_TREE;

public class ButtonStore {

    private Map<String, View> driveButtons = new LinkedHashMap<>();
    private Map<String, View> approachButtons = new LinkedHashMap<>();
    private Map<String, View> puttButtons = new LinkedHashMap<>();
    private static Map<String, View> teeButtons = new LinkedHashMap<>();
    private TextView totalPuttsView;
    private int totalPutts;
    private EditText approachDistanceView;
    private int approachDistance;

    public static final String RED = "Red";
    public static final String WHITE = "White";
    public static final String YELLOW = "Yellow";

    private static String ZERO = "0 Putts";
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
    private ButtonActions buttonActions;
    private static View teeView;

    public ButtonStore(View view) {
        this.view = view;
        buttonActions = new ButtonActions(view);
        teeView = view;
        setupButtons();
    }


    private void setupButtons() {

        setupTeeButtons(view);
        setupPuttButtons();
        setupDriveButtons();
        setupApproachButtons();
        totalPuttsView = new TextView(view.getContext());
        totalPuttsView.setWidth(40);
        totalPuttsView.setTextColor(Color.BLUE);
        approachDistanceView = new EditText(view.getContext());
        approachDistanceView.setWidth(200);
        approachDistanceView.setTextColor(Color.MAGENTA);

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
                    buttonActions.toggleButtonStates(toggleButton, driveButtons, approachButtons);
                }
            });
        });
        return toggleButtons;
    }



    private void setupTeeButtons(View v) {
        for (Tee tee : Tee.values()) {
            ImageButton teeImageButton = new ImageButton(v.getContext());
            switch (tee) {
                case RED:
                    teeImageButton.setImageResource(R.drawable.redtee);
                    teeImageButton.setTooltipText("Winter/Red");
                    break;
                case WHITE:
                    teeImageButton.setImageResource(R.drawable.whitetee);
                    teeImageButton.setTooltipText("White");
                    break;
                case YELLOW:
                    teeImageButton.setImageResource(R.drawable.yellowtee);
                    teeImageButton.setTooltipText("Yellow");
                    break;
            }
            teeImageButton.setTag(tee.getTeeColour());
            teeImageButton.setBackgroundColor(Color.TRANSPARENT);
            teeImageButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleTeeVisibility(view, tee);

                    Toast.makeText(view.getContext(), "tee selected", Toast.LENGTH_SHORT).show();
                }


            });

            teeButtons.put(tee.getTeeColour(), teeImageButton);
        }
    }

    public void toggleTeeVisibility(View view, Tee tee) {
        Map<String, View> changeButtons = new HashMap<>(2);
        if (tee == Tee.RED) {
            changeButtons.put(WHITE, teeButtons.get(WHITE));
            changeButtons.put(YELLOW, teeButtons.get(YELLOW));
//                changeVisibility(Objects.requireNonNull(buttons.get(WHITE.getTeeColour())), Objects.requireNonNull(buttons.get(YELLOW.getTeeColour())));
        } else if (tee == Tee.WHITE) {
            changeButtons.put(RED, teeButtons.get(RED));
            changeButtons.put(YELLOW, teeButtons.get(YELLOW));
        } else if (tee == Tee.YELLOW) {
            changeButtons.put(WHITE, teeButtons.get(WHITE));
            changeButtons.put(RED, teeButtons.get(RED));

        }
            changeVisibility(changeButtons);
    }


    private void changeVisibility(Map<String, View> buttons) {
        buttons.forEach((k, b) -> {
            View v = view.findViewWithTag(k);
            if (v.getVisibility() == View.VISIBLE) {
                v.setVisibility(View.INVISIBLE);
            } else {
                v.setVisibility(View.VISIBLE);
            }

        });
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
                        buttonActions.resetPuttButtons(puttButtons);
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

//        PagerAdapter adapter = ((ViewPager) view.getParent()).getAdapter();
//        FragmentStatePagerAdapter fspa = (FragmentStatePagerAdapter)adapter;
//        Fragment currentFragment = fspa.getItem(0);
        totalPuttsView.setText(String.valueOf(getTotalPutts()));

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

    public EditText getApproachDistanceView() {
        return approachDistanceView;
    }

    public int getApproachDistance() {
        return approachDistance;
    }

    public void setApproachDistance(int approachDistance) {
        this.approachDistance = approachDistance;
    }

    private int shortPutts;
    private int mediumPutts;
    private int longPutts;
    private int hugePutts;
}

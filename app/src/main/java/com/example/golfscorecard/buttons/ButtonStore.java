package com.example.golfscorecard.buttons;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
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
    private static int redId;
    private static int whiteId;
    private static int yellowId;

    public static final String RED = "red";
    public static final String WHITE = "white";
    public static final String YELLOW = "yellow";
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
    private ButtonActions buttonActions;
    private static View teeView;

    public ButtonStore(View view) {
        this.view = view;
        buttonActions = new ButtonActions(view);
        teeView = view;
        setupTeeButtons(teeView);
        setupButtons();
    }


    private void setupButtons() {

//        setupTeeButtons();
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
                    buttonActions.toggleButtonStates(toggleButton, driveButtons, approachButtons);
                }
            });
        });
        return toggleButtons;
    }



    private static void setupTeeButtons(View v) {
        for (Tee tee : Tee.values()) {
            ImageButton teeImageButton = new ImageButton(v.getContext());
//            int id = View.generateViewId();
            switch (tee) {
                case RED:
                    teeImageButton.setImageResource(R.drawable.redtee);
                    teeImageButton.setTooltipText("Winter/Red");
                    redId = R.id.redTee;
                    teeImageButton.setId(redId);
                    break;
                case WHITE:
                    teeImageButton.setImageResource(R.drawable.whitetee);
                    teeImageButton.setTooltipText("White");
                    whiteId = R.id.whiteTee;
                    teeImageButton.setId(whiteId);
                    break;
                case YELLOW:
                    teeImageButton.setImageResource(R.drawable.yellowtee);
                    teeImageButton.setTooltipText("Yellow");
                    yellowId = R.id.yellowTee;
                    teeImageButton.setId(yellowId);
                    break;
            }
            teeImageButton.setTag(tee.getTeeColour());
//            teeImageButton.setId(id);
            teeImageButton.setBackgroundColor(Color.TRANSPARENT);
//            teeImageButton.setOnClickListener(new Button.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    toggleTeeVisibility(view);
//
//                    Toast.makeText(view.getContext(), "tee selected", Toast.LENGTH_SHORT).show();
//                }
//
//
//            });
//
//            teeImageButton.setOnLongClickListener(new Button.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//                    toggleTeeVisibility(view);
//                    Toast.makeText(view.getContext(), "tee de-selected", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//            });

            teeButtons.put(tee.getTeeColour(), teeImageButton);
        }
    }

//    private static void toggleTeeVisibility(View view) {
//
//        buttonActions.toggleTeeVisibility(view);
//    }

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

    private Map<String, Integer> getTeeIds() {
        Map<String, Integer> ids = new HashMap<>();
        ids.put(RED, getRedId());
        ids.put(WHITE, getWhiteId());
        ids.put(YELLOW, getYellowId());
        return  ids;
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

    public int getRedId() {
        return redId;
    }

    public int getWhiteId() {
        return whiteId;
    }

    public int getYellowId() {
        return yellowId;
    }

    private int shortPutts;
    private int mediumPutts;
    private int longPutts;
    private int hugePutts;
}

package com.example.golfscorecard.ui.main;

import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.golfscorecard.R;
import com.example.golfscorecard.buttons.ButtonStore;
import com.example.golfscorecard.databinding.FragmentHoleTemplateBinding;
import com.example.golfscorecard.shots.PuttLength;
import com.example.golfscorecard.shots.ShotOutcome;

import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class HoleScoreFragment extends Fragment {

    public static final int TEE = 1;
    public static final int DRIVE = 2;
    public static final int APPROACH = 3;
    public static final int PUTTING = 4;
    Spinner scoreSelector;

    ButtonStore buttonStore;

    public static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public HoleScoreFragment newInstance(int index) {
        HoleScoreFragment fragment = new HoleScoreFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hole_template, container, false);
//        View root = FragmentHoleTemplateBinding.inflate(inflater).constraintLayout;
        buttonStore = new ButtonStore(root);
        placeButtons(root);
        return root;
    }

    private void placeButtons(View view) {
        placeButtons(view, buttonStore.getTeeButtons(), TEE);
        placeButtons(view, buttonStore.getDriveButtons(), DRIVE);
        placeButtons(view, buttonStore.getApproachButtons(), APPROACH);
        placeButtons(view, buttonStore.getPuttButtons(), PUTTING);
        addTotalFields(view);
    }

    private void placeButtons(View view, Map<String, View> buttons,
                              int section) {
        int id = 0;
        int label = 0;
        switch (section) {
            case DRIVE:
                id = R.id.driveTableLayout;
                label = R.string.drive;
                break;
            case APPROACH:
                id = R.id.approachTableLayout;
                label = R.string.approach;
                break;
            case PUTTING:
                id = R.id.puttsTableLayout;
                label = R.string.putting;
                break;
            case TEE:
                id = R.id.teeLayout;
                label = R.string.tees;
                break;
        }
        TableLayout layout = view.findViewById(id);
        TableRow tr = new TableRow(layout.getContext());
        if (section == TEE) {
            tr.setGravity(Gravity.END);
        }
        layout.addView(tr);
        TextView rowLabel = new TextView(layout.getContext());
        rowLabel.setText(label);
//        rowLabel.setTextAppearance(R.style.TextAppearance_AppCompat_Button);
        tr.setLayoutParams(new TableRow.LayoutParams(0));
        tr.addView(rowLabel);
        for (Map.Entry<String, View> es : buttons.entrySet()) {
            if (tr.getChildCount() == 5 ||
                    es.getKey().equals(ShotOutcome.A_FORTY.name()) ||
                    es.getKey().equals(ShotOutcome.A_TWO_HUNDRED.name()) ||
                    es.getKey().equals(getString(R.string.puttZero))) {
                tr = new TableRow(layout.getContext());
                layout.addView(tr);
            }
            tr.addView(es.getValue());
        }
    }

    private void addTotalFields(View v) {
        Integer[] score = new Integer[] {1,2,3,4,5,6,7,8,9,10};
        TextView totalPuttsLabel = new TextView(v.getContext());
        totalPuttsLabel.setText(getString(R.string.totalPutts));
        TextView totalPutts = buttonStore.getTotalPuttsView();
        totalPutts.setMinWidth(50);
        totalPutts.setGravity(Gravity.CENTER);
        totalPutts.setTextSize(18);
        totalPutts.setText("0");
        TextView totalScore = new TextView(v.getContext());
        totalScore.setText(getString(R.string.totalScore));
        totalScore.setGravity(Gravity.END);
        scoreSelector = new Spinner(v.getContext());
        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<Integer> adapter = ArrayAdapter.createFromResource(this,
//                R.array.planets_array, android.R.layout.simple_spinner_item);
        ArrayAdapter adapter = new ArrayAdapter<Integer>(v.getContext(), R.layout.support_simple_spinner_dropdown_item, score);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        scoreSelector.setAdapter(adapter);
        EditText tScore = new EditText(v.getContext());
        tScore.setInputType(InputType.TYPE_CLASS_NUMBER);
        tScore.setGravity(Gravity.END);
        LinearLayout layout = v.findViewById(R.id.totalsLayout);
        Space space = new Space(getContext());
        space.setMinimumWidth(200);
        layout.addView(space);
        layout.addView(totalPuttsLabel);
        layout.addView(totalPutts);
        layout.addView(totalScore);
        layout.addView(scoreSelector);
        layout.addView(tScore);
//        v.add
    }

    public void changeHoleScore(int score) {
        scoreSelector.setSelection(scoreSelector.getSelectedItemPosition() + score);
    }
}
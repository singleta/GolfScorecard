package com.example.golfscorecard.ui.main;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.golfscorecard.R;
import com.example.golfscorecard.buttons.PuttButton;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class HoleScoreFragment extends Fragment {

    public static final int SHORT = R.id.putt0;
    public static final int MEDIUM = R.id.putt1;
    public static final int LONG = R.id.putt2;
    public static final int HUGE = R.id.putt3;
    public static final int TEE = 1;

    ButtonStore buttonStore;

    private static final String ARG_SECTION_NUMBER = "section_number";

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
        final TextView textView = root.findViewById(R.id.section_label);
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        buttonStore = new ButtonStore(root);
        placeButtons(root);
        return root;
    }

    private void placeButtons(View view) {
        placeButtons(view, buttonStore.getTeeButtons(), TEE);
        placeButtons(view, buttonStore.getDriveButtons(), R.id.drive);
        placeButtons(view, buttonStore.getApproachButtons(), R.id.approach);
        placeButtons(view, buttonStore.getPuttButtons(), R.id.putts);
        addTotalFields(view);
    }

    private void placeButtons(View view, Map<String, View> buttons,
                              int section) {
        int id = 0;
        int label = 0;
        switch (section) {
            case R.id.drive:
                id = R.id.driveTableLayout;
                label = R.string.drive;
                break;
            case R.id.approach:
                id = R.id.approachTableLayout;
                label = R.string.approach;
                break;
            case R.id.putts:
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
        tr.setGravity(Gravity.RIGHT);
        layout.addView(tr);
        TextView rowLabel = new TextView(layout.getContext());
        rowLabel.setText(label);
        tr.setLayoutParams(new TableRow.LayoutParams(0));
        tr.addView(rowLabel);
        for (Map.Entry<String, View> es : buttons.entrySet()) {
            if (tr.getChildCount() == 5 ||
                    es.getKey().equals(ShotOutcome.FORTY.getOff()) ||
                    es.getKey().equals(PuttLength.SHORT.getPuttName())) {
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

        TextView totalScore = new TextView(v.getContext());
        totalScore.setText(getString(R.string.totalScore));
        totalScore.setGravity(Gravity.RIGHT);
        Spinner scoreSelector = new Spinner(v.getContext());
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
        tScore.setGravity(Gravity.RIGHT);
        LinearLayout layout = v.findViewById(R.id.totalsLayout);
        layout.addView(totalPuttsLabel);
        layout.addView(totalPutts);
        layout.addView(totalScore);
        layout.addView(scoreSelector);
        layout.addView(tScore);
//        v.add
    }
}
package com.example.golfscorecard;

import android.os.Bundle;

import com.example.golfscorecard.ui.main.HolePage;
import com.example.golfscorecard.ui.main.HoleScoreFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ToggleButton;

import com.example.golfscorecard.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter sectionsPagerAdapter;
    private SaveHoleData saveHoleData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Saved to file", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                saveData();
            }
        });
    }

    public void saveData() {
        List<HoleScoreFragment> holePages = sectionsPagerAdapter.getHolePages();
        saveHoleData = new SaveHoleData(holePages);
        saveHoleData.saveData();
    }

}
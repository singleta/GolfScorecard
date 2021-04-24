package com.example.golfscorecard;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.golfscorecard.ui.main.HoleScoreFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.golfscorecard.ui.main.SectionsPagerAdapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter sectionsPagerAdapter;
    private SaveHoleData saveHoleData;

    private static final String TAG = MainActivity.class.getSimpleName();

    public static int OVERALL_SCORE=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(17);
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
        FloatingActionButton mail = findViewById(R.id.mail);

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sent by mail", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                String filename = saveData();
                emailData(filename);
            }
        });
    }

    public String saveData() {
        List<HoleScoreFragment> holePages = sectionsPagerAdapter.getHolePages();
        saveHoleData = new SaveHoleData(holePages);
        List<HoleDetails> allHoles = saveHoleData.saveData();

        String[] requiredPermissions = { Manifest.permission.WRITE_EXTERNAL_STORAGE };
        ActivityCompat.requestPermissions(this, requiredPermissions, 0);

        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//        File file = Environment.getExternalStorageDirectory();
        String filename = file.getPath() + "/" + "GolfScorecard" + LocalDateTime.now().toString().replace(":", "") + ".csv";
        System.out.println("File dir: " + getFilesDir());
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(filename)))
        {
            System.out.println("writing filename: " + filename);
            for (HoleDetails h : allHoles) {
                pw.println(h.toString());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public void emailData(String filename) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setType("text/csv");

        Uri uri = Uri.fromFile(new File(filename));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"andrew_singleton@yahoo.co.uk"}); // recipients
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Scorecard: " + filename);

        List<HoleScoreFragment> holePages = sectionsPagerAdapter.getHolePages();
        saveHoleData = new SaveHoleData(holePages);
        List<HoleDetails> allHoles = saveHoleData.saveData();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (PrintWriter pw = new PrintWriter(baos))
        {
            System.out.println("writing email text");
            for (HoleDetails h : allHoles) {
                pw.println(h.toString());
            }
        }


        emailIntent.putExtra(Intent.EXTRA_TEXT, baos.toString());
        emailIntent.putExtra(Intent.ACTION_ATTACH_DATA, uri);
//        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(emailIntent);
    }

    public int getOverallScore() {
        int score = 0;
        for (HoleScoreFragment holeScoreFragment : sectionsPagerAdapter.getHolePages()) {
            if (holeScoreFragment!=null) {
                score += holeScoreFragment.getHoleScore();
            }
        }
        return score;

    }

    public static void setOverallScore(int overallScore) {
        OVERALL_SCORE = overallScore;
    }


//    public void selectTee(View view) {
//        if (view.getId() == R.id.redTee) {
//            toggleVisibility(R.id.whiteTee, R.id.yellowTee);
//        } else if (view.getId() == R.id.whiteTee) {
//            toggleVisibility(R.id.redTee, R.id.yellowTee);
//        } else {
//            toggleVisibility(R.id.whiteTee, R.id.redTee);
//        }
//    }
//
//    private void toggleVisibility(int tee1, int tee2) {
//        int[] tees = new int[]{tee1, tee2};
//        Arrays.stream(tees).forEach(tee -> {
//
//        View v = findViewById(tee);
//        if (v.getVisibility() == View.VISIBLE) {
//            v.setVisibility(View.INVISIBLE);
//        } else {
//            v.setVisibility(View.VISIBLE);
//        }
//        });
//    }
}
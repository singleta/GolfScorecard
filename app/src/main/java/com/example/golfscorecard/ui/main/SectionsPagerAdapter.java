package com.example.golfscorecard.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.golfscorecard.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public static final int HOLES = 18;
    private List<HoleScoreFragment> holePages = new ArrayList<>(HOLES);
    private HoleScoreFragment holeScoreFragment = new HoleScoreFragment();

    @StringRes
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        HoleScoreFragment fragment = holeScoreFragment.newInstance(position + 1);
        holePages.add(fragment);
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(R.string.hole) + " " + (position + 1);
    }

    @Override
    public int getCount() {
        // Show total pages.
        return HOLES;
    }

    public List<HoleScoreFragment> getHolePages() {
        return holePages;
    }
}
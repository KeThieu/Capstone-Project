package com.udacity_k.capstone.lovelivesif_songdatabase.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.udacity_k.capstone.lovelivesif_songdatabase.fragments.DifficultyDetailsFragment;

/**
 * Created by Kenneth on 11/20/2016.
 */
public class DifficultiesPageAdapter extends FragmentPagerAdapter {
    private static final int NUM_PAGES = 5;
    private Context mContext;
    private Uri mUri;

    public DifficultiesPageAdapter(FragmentManager fm, Uri uri, Context context) {
        super(fm);
        mContext = context;
        mUri = uri;
    }
    @Override
    public Fragment getItem(int position) {
        return DifficultyDetailsFragment.newInstance(position, mUri);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}

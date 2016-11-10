package com.udacity_k.capstone.lovelivesif_songdatabase.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.udacity_k.capstone.lovelivesif_songdatabase.fragments.SongGridFragment;

/**
 * Created by Kenneth on 11/9/2016.
 */
public class AttributePageAdapter extends FragmentPagerAdapter {
    private static int NUM_PAGES = 3;
    private String[] tabTitles = new String[] {"Smile", "Pure", "Cool"};

    public AttributePageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return SongGridFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}

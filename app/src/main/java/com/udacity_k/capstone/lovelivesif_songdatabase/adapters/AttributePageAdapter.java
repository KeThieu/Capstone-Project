package com.udacity_k.capstone.lovelivesif_songdatabase.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.udacity_k.capstone.lovelivesif_songdatabase.R;
import com.udacity_k.capstone.lovelivesif_songdatabase.fragments.SongGridFragment;

/**
 * Created by Kenneth on 11/9/2016.
 */
public class AttributePageAdapter extends FragmentPagerAdapter {
    private static final int NUM_PAGES = 3;
    private String[] tabTitles;
    private Context mContext;

    public AttributePageAdapter(FragmentManager fm, Context context) {
        super(fm);
        tabTitles = new String[] {
                context.getResources().getString(R.string.smile_string),
                context.getResources().getString(R.string.pure_string),
                context.getResources().getString(R.string.cool_string)};
        mContext = context;
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

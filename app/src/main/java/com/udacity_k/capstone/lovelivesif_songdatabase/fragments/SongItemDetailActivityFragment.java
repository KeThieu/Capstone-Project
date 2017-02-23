package com.udacity_k.capstone.lovelivesif_songdatabase.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.udacity_k.capstone.lovelivesif_songdatabase.R;
import com.udacity_k.capstone.lovelivesif_songdatabase.adapters.DifficultiesPageAdapter;
import com.udacity_k.capstone.lovelivesif_songdatabase.data.SongContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class SongItemDetailActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final String LOG_TAG = SongItemDetailActivityFragment.class.getSimpleName();
    public static final String Uri_BundleKey = "URI";
    public static Uri queryUri = null;

    private static final int SONGDETAILLOADER = 0;

    private static final String[] songDetailColumns = {
            SongContract.SmileEntry._ID,
            SongContract.SmileEntry.COLUMN_SONGNAME,
            SongContract.SmileEntry.COLUMN_ROMANIZEDNAME,
            SongContract.SmileEntry.COLUMN_TRANSLATEDNAME,
            SongContract.SmileEntry.COLUMN_ATTRIBUTE,
            SongContract.SmileEntry.COLUMN_MAINUNIT,
            SongContract.SmileEntry.COLUMN_LENGTH,
            SongContract.SmileEntry.COLUMN_BPM,
            SongContract.SmileEntry.COLUMN_IMAGE,
            SongContract.SmileEntry.COLUMN_AVAILABLE,
            SongContract.SmileEntry.COLUMN_EASYDIFFICULTY,
            SongContract.SmileEntry.COLUMN_EASYNOTES,
            SongContract.SmileEntry.COLUMN_NORMALDIFFICULTY,
            SongContract.SmileEntry.COLUMN_NORMALNOTES,
            SongContract.SmileEntry.COLUMN_HARDDIFFICULTY,
            SongContract.SmileEntry.COLUMN_HARDNOTES,
            SongContract.SmileEntry.COLUMN_EXPERTDIFFICULTY,
            SongContract.SmileEntry.COLUMN_EXPERTNOTES,
            SongContract.SmileEntry.COLUMN_MASTERDIFFICULTY,
            SongContract.SmileEntry.COLUMN_MASTERNOTES
    };

    private static final int COL_SONG_ID = 0;
    private static final int COL_SONGNAME = 1;
    private static final int COL_ROMANIZEDNAME = 2;
    private static final int COL_TRANSLATEDNAME = 3;
    private static final int COL_ATTRIBUTE = 4;
    private static final int COL_MAINUNIT = 5;
    private static final int COL_LENGTH = 6;
    private static final int COL_BPM = 7;
    private static final int COL_IMAGE = 8;
    private static final int COL_AVAILABLE = 9;
    private static final int COL_EASYDIFFICULTY = 10;
    private static final int COL_EASYNOTES = 11;
    private static final int COL_NORMALDIFFICULTY = 12;
    private static final int COL_NORMALNOTES = 13;
    private static final int COL_HARDDIFFCULTY = 14;
    private static final int COL_HARDNOTES = 15;
    private static final int COL_EXPERTDIFFICULTY = 16;
    private static final int COL_EXPERTNOTES = 17;
    private static final int COL_MASTERDIFFICULTY = 18;
    private static final int COL_MASTERNOTES = 19;

    private View mRootView;
    private Toolbar mToolbar;

    private AppBarLayout mAppBar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private NestedScrollView mNestedScrollView;
    private ImageView mImageView;
    private TextView mSongNameView;
    private TextView mRomanizedNameView;
    private TextView mTranslatedNameView;
    private TextView mUnitNameView;
    private TextView mAttributeView;
    private TextView mSongLengthView;
    private TextView mBPMView;
    private TextView mAvailableView;

    private String mTitle = "";
    private int mMinutesValue = 0;
    private int mSecondsValues = 0;

    private ViewPager mDifficultiesViewPager;

    public SongItemDetailActivityFragment() {
        //requires empty public constructor, use newInstance instead
    }

    public static SongItemDetailActivityFragment newInstance(Uri uri) {
        Bundle args = new Bundle();
        args.putParcelable(Uri_BundleKey, uri);
        SongItemDetailActivityFragment fragment = new SongItemDetailActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if(arguments != null) {
            Uri uri = arguments.getParcelable(Uri_BundleKey);
            queryUri = uri;
            //Log.v(LOG_TAG, "Detail URI is : " + queryUri);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_song_item_detail, container, false);
        mToolbar = (Toolbar) mRootView.findViewById(R.id.frag_toolbar);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) mRootView.findViewById(R.id.frag_collapsingToolbarLayout);
        mNestedScrollView = (NestedScrollView) mRootView.findViewById(R.id.frag_scrollview);
        mImageView = (ImageView) mRootView.findViewById(R.id.frag_songThumbnail);
        mSongNameView = (TextView) mRootView.findViewById(R.id.detail_songName);
        mRomanizedNameView = (TextView) mRootView.findViewById(R.id.detail_romanizedName);
        mTranslatedNameView = (TextView) mRootView.findViewById(R.id.detail_translatedName);
        mUnitNameView = (TextView) mRootView.findViewById(R.id.detail_unitName);
        mAttributeView = (TextView) mRootView.findViewById(R.id.detail_attribute);
        mSongLengthView = (TextView) mRootView.findViewById(R.id.detail_songLength);
        mBPMView = (TextView) mRootView.findViewById(R.id.detail_songBPM);
        mAvailableView = (TextView) mRootView.findViewById(R.id.detail_available);

        mAppBar = (AppBarLayout) mRootView.findViewById(R.id.frag_appBarLayout);

        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if(scrollRange + verticalOffset == 0) {
                    //when fully collapsed, show title
                    mCollapsingToolbarLayout.setTitle(mTitle);
                    mToolbar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                    isShow = true;
                } else if (isShow) {
                    //remove the title, set color back to transparent
                    mCollapsingToolbarLayout.setTitle("");
                    mToolbar.setBackgroundColor(Color.TRANSPARENT);
                    isShow=false;
                }
            }
        });

        //mNestedScrollView.setFillViewport(true); //PROBLEM STATEMENT

        mDifficultiesViewPager = (ViewPager) mRootView.findViewById(R.id.frag_difficultiesViewPager);
        mDifficultiesViewPager.setAdapter(new DifficultiesPageAdapter(getChildFragmentManager(), queryUri, getActivity()));

        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if(queryUri != null) {
            getLoaderManager().initLoader(SONGDETAILLOADER, null, this);
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

            return new CursorLoader(getActivity(),
                    queryUri,
                    songDetailColumns,
                    null,
                    null,
                    null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //populate the xml views here
        if(data != null && data.moveToFirst()) {
            String imageURL = data.getString(COL_IMAGE);

            //as of 2/22/2017, the original imageURL wasn't loaded by Glide. Need to append http
            imageURL = "http:" + imageURL;

            int songLength = data.getInt(COL_LENGTH);

            if (null != imageURL) {
                Glide.with(getActivity())
                        .load(imageURL)
                        .error(R.drawable.not_available_image)
                        .crossFade()
                        .into(mImageView);
            }

            //setting values and content descriptions too
            mImageView.setContentDescription(imageURL);

            mTitle = data.getString(COL_SONGNAME);
            mSongNameView.setText(mTitle);
            mSongNameView.setContentDescription(mTitle);

            mRomanizedNameView.setText(data.getString(COL_ROMANIZEDNAME));
            mRomanizedNameView.setContentDescription(data.getString(COL_ROMANIZEDNAME));

            mTranslatedNameView.setText(data.getString(COL_TRANSLATEDNAME));
            mTranslatedNameView.setContentDescription(data.getString(COL_TRANSLATEDNAME));

            mUnitNameView.setText(data.getString(COL_MAINUNIT));
            mUnitNameView.setContentDescription(data.getString(COL_MAINUNIT));

            mAttributeView.setText(data.getString(COL_ATTRIBUTE));
            mAttributeView.setContentDescription(data.getString(COL_ATTRIBUTE));

            mSongLengthView.setText(convertSongLength(songLength));
            mSongLengthView.setContentDescription(convertSongLength(songLength));

            mBPMView.setText(data.getString(COL_BPM)); //its an int, but printing as string is fine too
            mBPMView.setContentDescription(data.getString(COL_BPM));

            mAvailableView.setText(data.getString(COL_AVAILABLE));
            mAvailableView.setContentDescription(data.getString(COL_AVAILABLE));

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public String convertSongLength(int value) {
        mMinutesValue = (int) Math.floor(value / 60);
        mSecondsValues = value % 60;
        return String.format("%d:%02d", mMinutesValue, mSecondsValues);
    }

}

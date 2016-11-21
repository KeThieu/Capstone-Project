package com.udacity_k.capstone.lovelivesif_songdatabase.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity_k.capstone.lovelivesif_songdatabase.R;
import com.udacity_k.capstone.lovelivesif_songdatabase.data.SongContract;

import org.w3c.dom.Text;

public class DifficultyDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static String LOG_TAG = DifficultyDetailsFragment.class.getSimpleName();
    private View mRootView;
    private static final String ARG_DIFFICULTY_PAGE = "ARG_DIFFICULTY_PAGE";
    private static final String DIFFICULT_URI_KEY = "DIFFICULTY_URI_KEY";
    private static final int DIFFICULTYLOADERCURSOR = 0;

    private Uri difficultyQueryUri;
    private int mPagePosition;

    private static final String[] difficulty_columns = {
            SongContract.SmileEntry._ID,
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
    private static final int COL_EASYDIFFICULTY = 1;
    private static final int COL_EASYNOTES = 2;
    private static final int COL_NORMALDIFFICULTY = 3;
    private static final int COL_NORMALNOTES = 4;
    private static final int COL_HARDDIFFCULTY = 5;
    private static final int COL_HARDNOTES = 6;
    private static final int COL_EXPERTDIFFICULTY = 7;
    private static final int COL_EXPERTNOTES = 8;
    private static final int COL_MASTERDIFFICULTY = 9;
    private static final int COL_MASTERNOTES = 10;

    public DifficultyDetailsFragment() {
        // Required empty public constructor
    }

    public static DifficultyDetailsFragment newInstance(int position, Uri uri) {
        DifficultyDetailsFragment fragment = new DifficultyDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_DIFFICULTY_PAGE, position);
        args.putParcelable(DIFFICULT_URI_KEY, uri);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPagePosition = this.getArguments().getInt(ARG_DIFFICULTY_PAGE);
        difficultyQueryUri = this.getArguments().getParcelable(DIFFICULT_URI_KEY);
        Log.v(LOG_TAG, "check check check x5");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_difficulty_details, container, false);

        return mRootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

package com.udacity_k.capstone.lovelivesif_songdatabase.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity_k.capstone.lovelivesif_songdatabase.R;
import com.udacity_k.capstone.lovelivesif_songdatabase.adapters.SongAdapter;
import com.udacity_k.capstone.lovelivesif_songdatabase.data.SongContract;

public class SongGridFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String LOG_TAG = SongGridFragment.class.getSimpleName();

    private OnSongGridFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private SongAdapter mSongAdapter;

    public static final int SONGS_LOADER = 0;

    //Since all three tables are the same columns besides Table Names
    //which aren't joined, it may be safe to simply use Smile's name for convenience.
    private static final String[] songGridColumns = {
            SongContract.SmileEntry._ID,
            SongContract.SmileEntry.COLUMN_SONGNAME,
            SongContract.SmileEntry.COLUMN_IMAGE
    };

    /*
    * These constants are tied to the columns array above
     */
    public static final int COL_SONG_ID = 0;
    public static final int COL_SONGNAME = 1;
    public static final int COL_SONGIMAGE = 2;

    public SongGridFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SongGridFragment.
     */
    public static SongGridFragment newInstance() {
        SongGridFragment fragment = new SongGridFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_song_grid, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);

        mSongAdapter = new SongAdapter(getActivity());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(mSongAdapter);

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSongGridFragmentInteractionListener) {
            mListener = (OnSongGridFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(SONGS_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //query Content Provider here with relevant projection

        //figure out a way to use the other two table content uris as well
        Uri queryUri = SongContract.SmileEntry.CONTENT_URI;

        return new CursorLoader(getActivity(),
                queryUri,
                songGridColumns,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mSongAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mSongAdapter.swapCursor(null);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnSongGridFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

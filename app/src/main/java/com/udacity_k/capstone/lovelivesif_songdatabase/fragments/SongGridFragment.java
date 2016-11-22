package com.udacity_k.capstone.lovelivesif_songdatabase.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.ParallelExecutorCompat;
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
    private static final String ARG_PAGE = "ARG_PAGE";
    private static Uri attribute_query = SongContract.SmileEntry.CONTENT_URI; //default smile for now

    private OnSongGridFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private SongAdapter mSongAdapter;

    public static final int SONGS_LOADER = 0;

    //Since all three tables are the same columns besides Table Names
    //which aren't joined, it may be safe to simply use Smile's name for convenience.
    private static final String[] songGridColumns = {
            SongContract.SmileEntry._ID,
            SongContract.SmileEntry.COLUMN_SONGNAME,
            SongContract.SmileEntry.COLUMN_IMAGE,
            SongContract.SmileEntry.COLUMN_MAINUNIT,
            SongContract.SmileEntry.COLUMN_ATTRIBUTE
    };

    /*
    * These constants are tied to the columns array above
     */
    public static final int COL_SONG_ID = 0;
    public static final int COL_SONGNAME = 1;
    public static final int COL_SONGIMAGE = 2;
    public static final int COL_SONGMAINUNIT = 3;
    public static final int COL_ATTRIBUTE = 4;

    private String smileSTR;
    private String pureSTR;
    private String coolSTR;

    public SongGridFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SongGridFragment.
     */
    public static SongGridFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, position);
        SongGridFragment fragment = new SongGridFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        smileSTR = getActivity().getResources().getString(R.string.smile_string);
        pureSTR = getActivity().getResources().getString(R.string.pure_string);
        coolSTR = getActivity().getResources().getString(R.string.cool_string);

        int arg_position = this.getArguments().getInt(ARG_PAGE);

        /*
        * For use with CursorLoader to get the right queryUri
         */
        switch(arg_position) {
            case 0 : {
                attribute_query = SongContract.SmileEntry.CONTENT_URI;
                break;
            }

            case 1 : {
                attribute_query = SongContract.PureEntry.CONTENT_URI;
                break;
            }

            case 2 : {
                attribute_query = SongContract.CoolEntry.CONTENT_URI;
                break;
            }
            default : {
                throw new UnsupportedOperationException(
                        getActivity().getResources().getString(R.string.UnsupportedOperation) + String.valueOf(arg_position));
            }
        }

        //moved from OnActivityCreated to here
        getLoaderManager().initLoader(SONGS_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_song_grid, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);

        mSongAdapter = new SongAdapter(getActivity(), new SongAdapter.SongAdapterOnClickHandler() {
            @Override
            public void onClick(long id, String attribute, SongAdapter.songImageViewHolder vh) {


                //get the correct Uri to send to Main Activity
                Uri queryUri;
                if(attribute.equals(smileSTR)) {
                    queryUri = SongContract.SmileEntry.buildSmileUri(id);
                } else if(attribute.equals(pureSTR)) {
                    queryUri = SongContract.PureEntry.buildPureUri(id);
                } else if(attribute.equals(coolSTR)) {
                    queryUri = SongContract.CoolEntry.buildCoolUri(id);
                } else {
                    //shouldn't reach here
                    throw new RuntimeException(getActivity().getResources().getString(R.string.QueryUriUnknownError));
                }

                //calling MainActivity fragment interaction here
                mListener.onFragmentInteraction(queryUri, vh);
            }
        });

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.grid_span_count)));
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
                    + getActivity().getResources().getString(R.string.FragmentInteractionError));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //query Content Provider here with relevant projection

        //figure out a way to use the other two table content uris as well
        //Uri queryUri = SongContract.PureEntry.CONTENT_URI;

        return new CursorLoader(getActivity(),
                attribute_query,
                songGridColumns,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.v(LOG_TAG, "Count from LoadFinished and " + attribute_query.toString() + " is: " + data.getCount());
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
        void onFragmentInteraction(Uri uri, SongAdapter.songImageViewHolder vh);
    }
}

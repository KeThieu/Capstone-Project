package com.udacity_k.capstone.lovelivesif_songdatabase;

import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.udacity_k.capstone.lovelivesif_songdatabase.adapters.AttributePageAdapter;
import com.udacity_k.capstone.lovelivesif_songdatabase.adapters.SongAdapter;
import com.udacity_k.capstone.lovelivesif_songdatabase.dataRequest.fetchSongsIntentService;
import com.udacity_k.capstone.lovelivesif_songdatabase.fragments.SongGridFragment;
import com.udacity_k.capstone.lovelivesif_songdatabase.fragments.SongItemDetailActivityFragment;

public class MainActivity extends AppCompatActivity implements SongGridFragment.OnSongGridFragmentInteractionListener {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private CoordinatorLayout mCoordinatorLayout;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Snackbar updateSnackbar;
    private Snackbar networkSnackbar;
    private FirebaseAnalytics mFirebaseAnalytics;

    private final String updatingKey = "UPDATING";
    private boolean isUpdating = false; //flag for updating snackbar
    private boolean isConnected = true; //flag for network error, prevent user from touching for details
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        if(findViewById(R.id.tablet_detail_container) != null) {
            mTwoPane = true;
            //For now, when tablet implementation happens, don't set orientation to portrait
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.tablet_detail_container, new SongItemDetailActivityFragment())
                    .commit();
        } else {
            mTwoPane = false;
            //For now, when tablet implementation happens, don't set orientation to portrait
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        mCoordinatorLayout = (CoordinatorLayout) this.findViewById(R.id.coordinator_root);
        mTabLayout = (TabLayout) this.findViewById(R.id.tab_Layout);
        mViewPager = (ViewPager) this.findViewById(R.id.main_viewPager);

        mViewPager.setAdapter(new AttributePageAdapter(getSupportFragmentManager(), this));
        mTabLayout.setupWithViewPager(mViewPager);

//        //setting up Content Descriptions for Tabs
//        mTabLayout.getTabAt(0).setContentDescription(R.string.smile_string);
//        mTabLayout.getTabAt(1).setContentDescription(R.string.pure_string);
//        mTabLayout.getTabAt(2).setContentDescription(R.string.cool_string);

        if(savedInstanceState == null) {
            startService(new Intent(this, fetchSongsIntentService.class));
        } else {
            //check for bundle for boolean
            boolean checkCurrentlyUpdating = savedInstanceState.getBoolean(updatingKey);
            if(!checkCurrentlyUpdating) {
                startService(new Intent(this, fetchSongsIntentService.class));
            }
        }


    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Resources resources = context.getResources();

            if(fetchSongsIntentService.FETCH_ACTION.equals(intent.getAction())) {
                if(intent.getBooleanExtra(fetchSongsIntentService.NETWORK_ERROR, false)) {
                    //Network Error, pop a snackbar with a retry action
                    isConnected = false;

                    if(null == networkSnackbar) {
                        networkSnackbar = Snackbar.make(mCoordinatorLayout, resources.getString(R.string.NetworkError_Str), Snackbar.LENGTH_INDEFINITE);
                        networkSnackbar.setAction(resources.getString(R.string.NetworkErrorSnackbarAction_Str), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Retry button clicked, try intentservice again
                                networkSnackbar.dismiss();
                                networkSnackbar = null;
                                startService(new Intent(MainActivity.this, fetchSongsIntentService.class));
                            }
                        });
                        networkSnackbar.show();
                    }
                } else {
                    //Network is fine
                    isConnected = true;

                    if (intent.getBooleanExtra(fetchSongsIntentService.FETCHING_EXTRA, false)) {
                        if (null == updateSnackbar) {
                            isUpdating = true;
                            updateSnackbar = Snackbar.make(mCoordinatorLayout, resources.getString(R.string.Updating_Str), Snackbar.LENGTH_INDEFINITE);
                            updateSnackbar.show();
                        }
                    } else if (!(intent.getBooleanExtra(fetchSongsIntentService.FETCHING_EXTRA, false))) {
                        if (updateSnackbar != null) {
                            updateSnackbar.dismiss();
                            isUpdating = false;
                            updateSnackbar = null;
                        }
                    }
                }
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
        if(updateSnackbar != null) {
            updateSnackbar.dismiss();
            updateSnackbar = null;
            //isUpdating = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver,
                new IntentFilter(fetchSongsIntentService.FETCH_ACTION));
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        //if updating, on screen orietentation change, don't run update again
        savedInstanceState.putBoolean(updatingKey, isUpdating);
        isUpdating = false;
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onFragmentInteraction(Uri uri, SongAdapter.songImageViewHolder vh) {
        //this is called when item is clicked, launch detail activity here

        if(mTwoPane) {
            //Tablet
            SongItemDetailActivityFragment fragment = SongItemDetailActivityFragment.newInstance(uri);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.tablet_detail_container, fragment)
                    .commit();
        } else {
            //Phone
            Intent intent = new Intent(this, SongItemDetailActivity.class).setData(uri);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle() );
            } else {
                startActivity(intent);
            }
        }
    }
}

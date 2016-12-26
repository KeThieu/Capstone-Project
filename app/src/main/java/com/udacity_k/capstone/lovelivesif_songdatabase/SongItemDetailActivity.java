package com.udacity_k.capstone.lovelivesif_songdatabase;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.udacity_k.capstone.lovelivesif_songdatabase.fragments.SongItemDetailActivityFragment;

public class SongItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_item_detail);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if(savedInstanceState == null) {
            //use newInstance method for fragment and argument creation
            Uri receivedIntentUri = getIntent().getData();

            SongItemDetailActivityFragment detailFragment = SongItemDetailActivityFragment.newInstance(receivedIntentUri);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.song_detail_container, detailFragment)
                    .commit();


        }
    }
}

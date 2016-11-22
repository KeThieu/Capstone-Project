package com.udacity_k.capstone.lovelivesif_songdatabase.widgets;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.udacity_k.capstone.lovelivesif_songdatabase.R;
import com.udacity_k.capstone.lovelivesif_songdatabase.SongItemDetailActivity;
import com.udacity_k.capstone.lovelivesif_songdatabase.data.SongContract;

import java.util.Random;

/**
 * Created by Kenneth on 11/22/2016.
 */
public class DetailWidgetIntentService extends IntentService {
    private static final String TAG = "DetailWidgetIntentService";

    public DetailWidgetIntentService() {
        super(TAG);
    }

    private String[] detail_cols = {
            SongContract.SmileEntry._ID,
            SongContract.SmileEntry.COLUMN_SONGNAME,
            SongContract.SmileEntry.COLUMN_MAINUNIT,
            SongContract.SmileEntry.COLUMN_IMAGE
    };

    private int COL_SONGID = 0;
    private int COL_SONGNAME = 1;
    private int COL_MAINUNIT = 2;
    private int COL_IMAGE = 3;

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v("WIDGET", "Widget Handle Intent");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, DetailWidgetProvider.class));

        //get random attribute to query
        Uri queryUri = SongContract.SmileEntry.CONTENT_URI; // default smile
        int attributeNum = getRandomIntInRange(0, 2); //0 for Smile, 1 for Pure, 2 for Cool
        switch(attributeNum) {
            case 0:
                queryUri = SongContract.SmileEntry.CONTENT_URI;
                break;
            case 1:
                queryUri = SongContract.PureEntry.CONTENT_URI;
                break;
            case 2:
                queryUri = SongContract.CoolEntry.CONTENT_URI;
                break;
            default:
                queryUri = SongContract.SmileEntry.CONTENT_URI; //redundant
        }

        Cursor data = getContentResolver().query(queryUri, detail_cols, null, null, null);

        if(data != null && data.moveToFirst()) {
            int randomSongPosition = getRandomIntInRange(0, data.getCount() - 1);

            data.moveToPosition(randomSongPosition);

            //start extracting data and use it
            long song_id = data.getLong(COL_SONGID);
            final String imageURL = data.getString(COL_IMAGE);
            final String songName = data.getString(COL_SONGNAME);
            final String mainUnit = data.getString(COL_MAINUNIT);

            RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.detail_widget);
            final AppWidgetTarget appWidgetTarget = new AppWidgetTarget(this, views, R.id.widget_imageview, appWidgetIds);

            // Get a handler that can be used to post to the main thread
            Handler mainHandler = new Handler(this.getMainLooper());

            Runnable myRunnable = new Runnable() {
                @Override
                public void run() { Glide.with(getApplicationContext())
                        .load(imageURL)
                        .asBitmap()
                        .into(appWidgetTarget);}
            };
            mainHandler.post(myRunnable);

            for(int id : appWidgetIds) {
                //populate the rest of these widget values
                views.setTextViewText(R.id.widget_songNameTextView, songName);
                views.setTextViewText(R.id.widget_unitTextView, mainUnit);

                Uri detailQueryUri;
                switch(attributeNum) {
                    case 0:
                        detailQueryUri = SongContract.SmileEntry.buildSmileUri(song_id);
                        break;
                    case 1:
                        detailQueryUri = SongContract.PureEntry.buildPureUri(song_id);
                        break;
                    case 2:
                        detailQueryUri = SongContract.CoolEntry.buildCoolUri(song_id);
                        break;
                    default:
                        throw new UnsupportedOperationException("Error");
                }

                Intent launchIntent = new Intent(this, SongItemDetailActivity.class).setData(detailQueryUri);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
                views.setOnClickPendingIntent(R.id.widget_frameRoot, pendingIntent);
                appWidgetManager.updateAppWidget(id, views);
            }
        }

    }

    public int getRandomIntInRange(int min, int max) {
        if(min > max) {
            throw new IllegalArgumentException("max must greater than or equal to min");
        }

        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}

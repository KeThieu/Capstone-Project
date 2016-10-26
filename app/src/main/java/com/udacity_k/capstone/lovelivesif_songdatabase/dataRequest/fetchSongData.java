package com.udacity_k.capstone.lovelivesif_songdatabase.dataRequest;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.util.Log;

import com.udacity_k.capstone.lovelivesif_songdatabase.R;
import com.udacity_k.capstone.lovelivesif_songdatabase.data.SongContract;
import com.udacity_k.capstone.lovelivesif_songdatabase.fragments.SongGridFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

public class fetchSongData extends AsyncTask<Void, Void, Void> {
    private static final String LOG_TAG = fetchSongData.class.getSimpleName();
    private Context mContext;
    private Vector<ContentValues> finalSmileList;
    private Vector<ContentValues> finalPureList;
    private Vector<ContentValues> finalCoolList;
    private int mCount;

    public fetchSongData(Context context) {

        mContext = context;
        mCount = 0;
        Log.v(LOG_TAG, "count at start of task is: " + mCount);
        finalSmileList = new Vector<>();
        finalPureList = new Vector<>();
        finalCoolList = new Vector<>();
    }
    /*
    / function for parsing a jsonobject into a ContentValue
     */

    public ContentValues parseJsonObject(JSONObject songData) throws JSONException {
        ContentValues values = new ContentValues();
        //below are the json objects from an element of the jsonarray
        final String songNameSTR = "name";
        final String romajiNameSTR = "romaji_name";
        final String translatedNameSTR = "translated_name";
        final String attributeNameSTR = "attribute";
        final String mainUnitNameSTR = "main_unit";
        final String lengthNameSTR = "time"; //time is on the API, but I used length.
        final String BPMNameSTR = "BPM";
        final String imageNameSTR = "image"; //this is a link to be used with Glide
        final String availableNameSTR = "available";
        final String easyDiffNameSTR = "easy_difficulty";
        final String easyNotesNameSTR = "easy_notes";
        final String normalDiffNameSTR = "normal_difficulty";
        final String normalNotesNameSTR = "normal_notes";
        final String hardDiffNameSTR = "hard_difficulty";
        final String hardNotesNameSTR = "hard_notes";
        final String expertDiffNameSTR = "expert_difficulty"; //ignoring expert_random_difficulty altogether for this project
        final String expertNotesNameSTR = "expert_notes";
        final String masterDiffNameSTR = "master_difficulty";
        final String masterNotesNameSTR = "master_notes";

        //defaults to store into database in case of null
        final String noInfo = "No information available";
        final int noIntInfo = 0;

        //All the columns names in all three tables are the same, so I'll default
        //the keys for these entries from the Smile Table
        if(!songData.isNull(songNameSTR)) {
            values.put(SongContract.SmileEntry.COLUMN_SONGNAME, songData.getString(songNameSTR));
        } else {
            values.put(SongContract.SmileEntry.COLUMN_SONGNAME, noInfo);
        }
        if(!songData.isNull(romajiNameSTR)) {
            values.put(SongContract.SmileEntry.COLUMN_ROMANIZEDNAME, songData.getString(romajiNameSTR));
        } else {
            values.put(SongContract.SmileEntry.COLUMN_ROMANIZEDNAME, noInfo);
        }
        if(!songData.isNull(translatedNameSTR)) {
            values.put(SongContract.SmileEntry.COLUMN_TRANSLATEDNAME, songData.getString(translatedNameSTR));
        } else {
            values.put(SongContract.SmileEntry.COLUMN_TRANSLATEDNAME, noInfo);
        }
        if(!songData.isNull(attributeNameSTR)) {
            values.put(SongContract.SmileEntry.COLUMN_ATTRIBUTE, songData.getString(attributeNameSTR));
        } else {
            values.put(SongContract.SmileEntry.COLUMN_ATTRIBUTE, noInfo);
        }
        if(!songData.isNull(mainUnitNameSTR)) {
            values.put(SongContract.SmileEntry.COLUMN_MAINUNIT, songData.getString(mainUnitNameSTR));
        } else {
            values.put(SongContract.SmileEntry.COLUMN_MAINUNIT, noInfo);
        }
        if(!songData.isNull(lengthNameSTR)) {
            values.put(SongContract.SmileEntry.COLUMN_LENGTH, songData.getInt(lengthNameSTR));
        } else {
            values.put(SongContract.SmileEntry.COLUMN_LENGTH, noIntInfo);
        }
        if(!songData.isNull(BPMNameSTR)) {
            values.put(SongContract.SmileEntry.COLUMN_BPM, songData.getInt(BPMNameSTR));
        } else {
            values.put(SongContract.SmileEntry.COLUMN_BPM, noIntInfo);
        }
        if(!songData.isNull(imageNameSTR)) {
            values.put(SongContract.SmileEntry.COLUMN_IMAGE, songData.getString(imageNameSTR));
        } else {
            values.put(SongContract.SmileEntry.COLUMN_IMAGE, noInfo);
        }
        if(!songData.isNull(availableNameSTR)) {
            values.put(SongContract.SmileEntry.COLUMN_AVAILABLE, songData.getBoolean(availableNameSTR));
        } else {
            values.put(SongContract.SmileEntry.COLUMN_AVAILABLE, false);
        }
        if(!songData.isNull(easyDiffNameSTR)) {
            values.put(SongContract.SmileEntry.COLUMN_EASYDIFFICULTY, songData.getInt(easyDiffNameSTR));
        } else {
            values.put(SongContract.SmileEntry.COLUMN_EASYDIFFICULTY, noIntInfo);
        }
        if(!songData.isNull(easyNotesNameSTR)) {
            values.put(SongContract.SmileEntry.COLUMN_EASYNOTES, songData.getInt(easyNotesNameSTR));
        } else {
            values.put(SongContract.SmileEntry.COLUMN_EASYNOTES, noIntInfo);
        }
        if(!songData.isNull(normalDiffNameSTR)) {
            values.put(SongContract.SmileEntry.COLUMN_NORMALDIFFICULTY, songData.getInt(normalDiffNameSTR));
        } else {
            values.put(SongContract.SmileEntry.COLUMN_NORMALDIFFICULTY, noIntInfo);
        }
        if(!songData.isNull(normalNotesNameSTR)) {
            values.put(SongContract.SmileEntry.COLUMN_NORMALNOTES, songData.getInt(normalNotesNameSTR));
        } else {
            values.put(SongContract.SmileEntry.COLUMN_NORMALNOTES, noIntInfo);
        }
        if(!songData.isNull(hardDiffNameSTR)) {
            values.put(SongContract.SmileEntry.COLUMN_HARDDIFFICULTY, songData.getInt(hardDiffNameSTR));
        } else {
            values.put(SongContract.SmileEntry.COLUMN_HARDDIFFICULTY, noIntInfo);
        }
        if(!songData.isNull(hardNotesNameSTR)) {
            values.put(SongContract.SmileEntry.COLUMN_HARDNOTES, songData.getInt(hardNotesNameSTR));
        } else {
            values.put(SongContract.SmileEntry.COLUMN_HARDNOTES, noIntInfo);
        }
        if(!songData.isNull(expertDiffNameSTR)) {
            values.put(SongContract.SmileEntry.COLUMN_EXPERTDIFFICULTY, songData.getInt(expertDiffNameSTR));
        } else {
            values.put(SongContract.SmileEntry.COLUMN_EXPERTDIFFICULTY, noIntInfo);
        }
        if(!songData.isNull(expertNotesNameSTR)) {
            values.put(SongContract.SmileEntry.COLUMN_EXPERTNOTES, songData.getInt(expertNotesNameSTR));
        } else {
            values.put(SongContract.SmileEntry.COLUMN_EXPERTNOTES, noIntInfo);
        }
        if(!songData.isNull(masterDiffNameSTR)) {
            values.put(SongContract.SmileEntry.COLUMN_MASTERDIFFICULTY, songData.getInt(masterDiffNameSTR));
        } else {
            values.put(SongContract.SmileEntry.COLUMN_EXPERTDIFFICULTY, noIntInfo);
        }
        if(!songData.isNull(masterNotesNameSTR)) {
            values.put(SongContract.SmileEntry.COLUMN_MASTERNOTES, songData.getInt(masterNotesNameSTR));
        } else {
            values.put(SongContract.SmileEntry.COLUMN_MASTERNOTES, noIntInfo);
        }

        return values;
    }

    public void parseJson(String data) throws JSONException {
        //convert these for a bulk insert into the database
        Vector<ContentValues> smileSongs = new Vector<ContentValues>();
        Vector<ContentValues> pureSongs = new Vector<ContentValues>();
        Vector<ContentValues> coolSongs = new Vector<ContentValues>();

        final String nextObjSTR = "next"; //next URI link
        final String resultsObjSTR = "results"; //JsonArray

        //only using this to place correct ContentValue into appropriate Vector
        final String attributeNameSTR = "attribute";
        final String Smile = "Smile";
        final String Pure = "Pure";
        final String Cool = "Cool";

        JSONObject initial = new JSONObject(data);
        //gonna start from the "last" page and recursively return to the 1st page
        if(!initial.isNull(nextObjSTR)) {
            //there is another page
            String next = initial.optString(nextObjSTR);
            makeConnection(Uri.parse(next));
        }

        //begin parsing the results array here
        JSONArray results = initial.getJSONArray(resultsObjSTR);

        for(int i = 0; i < results.length(); i++) {
            JSONObject element = results.getJSONObject(i);
            String attr = element.getString(attributeNameSTR);

            switch(attr) {
                case Smile : {
                    smileSongs.add(parseJsonObject(element));
                    break;
                }
                case Pure : {
                    pureSongs.add(parseJsonObject(element));
                    break;
                }
                case Cool : {
                    coolSongs.add(parseJsonObject(element));
                    break;
                }
                default:
                    //shouldn't reach here, but in the event a song hits this code, I'll log it for now.
                    Log.v(LOG_TAG, "Null Attribute song detected" );
                    throw new UnsupportedOperationException("null or unknonwn attribute");
            }
        }

        //Parsed through all the elements, bulk insert them into the Database if possible
        //Just gonna delete all rows of the table first

        if(smileSongs.size() > 0) {
            mCount += smileSongs.size();
            finalSmileList.addAll(smileSongs);
            /*
            ContentValues[] smileArray = new ContentValues[smileSongs.size()];
            smileSongs.toArray(smileArray);
            //mContext.getContentResolver().delete(SongContract.SmileEntry.CONTENT_URI, null, null);
            mContext.getContentResolver().bulkInsert(SongContract.SmileEntry.CONTENT_URI, smileArray);
            */
        }
        if(pureSongs.size() > 0) {
            mCount += pureSongs.size();
            finalPureList.addAll(pureSongs);
            /*
            ContentValues[] pureArray = new ContentValues[pureSongs.size()];
            pureSongs.toArray(pureArray);
           // mContext.getContentResolver().delete(SongContract.PureEntry.CONTENT_URI, null, null);
            mContext.getContentResolver().bulkInsert(SongContract.PureEntry.CONTENT_URI, pureArray);
            */
        }
        if(coolSongs.size() > 0) {
            mCount += coolSongs.size();
            finalCoolList.addAll(coolSongs);
            /*
            ContentValues[] coolArray = new ContentValues[coolSongs.size()];
            coolSongs.toArray(coolArray);
          //  mContext.getContentResolver().delete(SongContract.CoolEntry.CONTENT_URI, null, null);
            mContext.getContentResolver().bulkInsert(SongContract.CoolEntry.CONTENT_URI, coolArray);
            */
        }

        /*
        //again, should be 121
        Log.v(LOG_TAG,
                "Insertion complete :"
                        + Integer.valueOf(smileSongs.size() + pureSongs.size() + coolSongs.size()).toString() + " inserted.");
        */
    }

    public Void makeConnection(Uri uri) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(uri.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if(inputStream == null) return null;

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if(buffer.length() == 0) return null;

            String songJSON = buffer.toString();
            Log.v(LOG_TAG, songJSON + "\n + \n");
            parseJson(songJSON);

            return null;

        } catch (IOException ioexception) {
            Log.e(LOG_TAG, "Error in IO " + ioexception + " " + uri.toString());

        } catch (JSONException jsonexception) {
            Log.e(LOG_TAG, "Error in JSON " + jsonexception);

        } finally {
            if(connection != null) {
                connection.disconnect();
            }
            if(reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }

    public boolean checkToDelete() {
        Log.i(LOG_TAG, "calling checkToDelete");
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        int current_songCount = sp.getInt(mContext.getString(R.string.db_songCount), 0); //default to 0
        if(mCount > current_songCount) {
            //current fetch total count is greater, delete contents of db before inserting.
            Log.v(LOG_TAG, "Beginning delete");
            mContext.getContentResolver().delete(SongContract.SmileEntry.CONTENT_URI, null, null);
            mContext.getContentResolver().delete(SongContract.PureEntry.CONTENT_URI, null, null);
            mContext.getContentResolver().delete(SongContract.CoolEntry.CONTENT_URI, null, null);
            return true;
        } else {
            Log.v(LOG_TAG, "count is 0 or songcount is the same, so don't delete");
            return false;
        }
    }

    public void insertSongData() {
        Log.i(LOG_TAG, "calling insertSongData");
        Log.v(LOG_TAG, "Total Song Count is: " + mCount + " songs.");

        boolean upToDate = checkToDelete();

        if(upToDate) {
            Log.v(LOG_TAG, "Insert because list isn't up to date");
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
            sp.edit().putInt(mContext.getString(R.string.db_songCount), mCount).apply();

            ContentValues[] newFinalSmile = new ContentValues[finalSmileList.size()];
            finalSmileList.toArray(newFinalSmile);
            mContext.getContentResolver().bulkInsert(SongContract.SmileEntry.CONTENT_URI, newFinalSmile);
            ContentValues[] newFinalPure = new ContentValues[finalPureList.size()];
            finalPureList.toArray(newFinalPure);
            mContext.getContentResolver().bulkInsert(SongContract.PureEntry.CONTENT_URI, newFinalPure);
            ContentValues[] newFinalCool = new ContentValues[finalCoolList.size()];
            finalCoolList.toArray(newFinalCool);
            mContext.getContentResolver().bulkInsert(SongContract.CoolEntry.CONTENT_URI, newFinalCool);
        }
    }

    public Void doInBackground(Void... params) {

        String authorityStr = "http://schoolido.lu/api/songs/";
        //no queries I am interested in
        makeConnection(Uri.parse(authorityStr));
        insertSongData();
        return null;
    }

    public Void onPostExecute() {

        return null;
    }

}

package com.udacity_k.capstone.lovelivesif_songdatabase.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kenneth on 8/29/2016.
 */
public class SongDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "songs.db";

    public SongDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //only the song name is guaranteed to not be null according to the API, so a check
        //may be required for queries later on.
        final String SQL_SMILE_TABLE = "CREATE TABLE " + SongContract.SmileEntry.TABLE_NAME + " (" +
                SongContract.SmileEntry._ID + " INTEGER PRIMARY KEY, " +
                SongContract.SmileEntry.COLUMN_SONGNAME + " TEXT NOT NULL, " +
                SongContract.SmileEntry.COLUMN_ROMANIZEDNAME + " TEXT, " +
                SongContract.SmileEntry.COLUMN_TRANSLATEDNAME + " TEXT, " +
                SongContract.SmileEntry.COLUMN_ATTRIBUTE + " TEXT, " +
                SongContract.SmileEntry.COLUMN_MAINUNIT + " TEXT, " +
                SongContract.SmileEntry.COLUMN_LENGTH + " INTEGER, " +
                SongContract.SmileEntry.COLUMN_BPM + " INTEGER, " +
                SongContract.SmileEntry.COLUMN_IMAGE + " TEXT, " +
                SongContract.SmileEntry.COLUMN_AVAILABLE + " BOOLEAN, " +
                SongContract.SmileEntry.COLUMN_EASYDIFFICULTY + " INTEGER, " +
                SongContract.SmileEntry.COLUMN_EASYNOTES + " INTEGER, " +
                SongContract.SmileEntry.COLUMN_NORMALDIFFICULTY + " INTEGER, " +
                SongContract.SmileEntry.COLUMN_NORMALNOTES + " INTEGER, " +
                SongContract.SmileEntry.COLUMN_HARDDIFFICULTY + " INTEGER, " +
                SongContract.SmileEntry.COLUMN_HARDNOTES + " INTEGER, " +
                SongContract.SmileEntry.COLUMN_EXPERTDIFFICULTY + " INTEGER, " +
                SongContract.SmileEntry.COLUMN_EXPERTNOTES + " INTEGER, " +
                SongContract.SmileEntry.COLUMN_MASTERDIFFICULTY + " INTEGER, " +
                SongContract.SmileEntry.COLUMN_MASTERNOTES + " INTEGER " +
                " );";

        final String SQL_PURE_TABLE = "CREATE TABLE " + SongContract.PureEntry.TABLE_NAME + " (" +
                SongContract.PureEntry._ID + " INTEGER PRIMARY KEY, " +
                SongContract.PureEntry.COLUMN_SONGNAME + " TEXT NOT NULL, " +
                SongContract.PureEntry.COLUMN_ROMANIZEDNAME + " TEXT, " +
                SongContract.PureEntry.COLUMN_TRANSLATEDNAME + " TEXT, " +
                SongContract.PureEntry.COLUMN_ATTRIBUTE + " TEXT, " +
                SongContract.PureEntry.COLUMN_MAINUNIT + " TEXT, " +
                SongContract.PureEntry.COLUMN_LENGTH + " INTEGER, " +
                SongContract.PureEntry.COLUMN_BPM + " INTEGER, " +
                SongContract.PureEntry.COLUMN_IMAGE + " TEXT, " +
                SongContract.PureEntry.COLUMN_AVAILABLE + " BOOLEAN, " +
                SongContract.PureEntry.COLUMN_EASYDIFFICULTY + " INTEGER, " +
                SongContract.PureEntry.COLUMN_EASYNOTES + " INTEGER, " +
                SongContract.PureEntry.COLUMN_NORMALDIFFICULTY + " INTEGER, " +
                SongContract.PureEntry.COLUMN_NORMALNOTES + " INTEGER, " +
                SongContract.PureEntry.COLUMN_HARDDIFFICULTY + " INTEGER, " +
                SongContract.PureEntry.COLUMN_HARDNOTES + " INTEGER, " +
                SongContract.PureEntry.COLUMN_EXPERTDIFFICULTY + " INTEGER, " +
                SongContract.PureEntry.COLUMN_EXPERTNOTES + " INTEGER, " +
                SongContract.PureEntry.COLUMN_MASTERDIFFICULTY + " INTEGER, " +
                SongContract.PureEntry.COLUMN_MASTERNOTES + " INTEGER " +
                " );";

        final String SQL_COOL_TABLE = "CREATE TABLE " + SongContract.CoolEntry.TABLE_NAME + " (" +
                SongContract.CoolEntry._ID + " INTEGER PRIMARY KEY, " +
                SongContract.CoolEntry.COLUMN_SONGNAME + " TEXT NOT NULL, " +
                SongContract.CoolEntry.COLUMN_ROMANIZEDNAME + " TEXT, " +
                SongContract.CoolEntry.COLUMN_TRANSLATEDNAME + " TEXT, " +
                SongContract.CoolEntry.COLUMN_ATTRIBUTE + " TEXT, " +
                SongContract.CoolEntry.COLUMN_MAINUNIT + " TEXT, " +
                SongContract.CoolEntry.COLUMN_LENGTH + " INTEGER, " +
                SongContract.CoolEntry.COLUMN_BPM + " INTEGER, " +
                SongContract.CoolEntry.COLUMN_IMAGE + " TEXT, " +
                SongContract.CoolEntry.COLUMN_AVAILABLE + " BOOLEAN, " +
                SongContract.CoolEntry.COLUMN_EASYDIFFICULTY + " INTEGER, " +
                SongContract.CoolEntry.COLUMN_EASYNOTES + " INTEGER, " +
                SongContract.CoolEntry.COLUMN_NORMALDIFFICULTY + " INTEGER, " +
                SongContract.CoolEntry.COLUMN_NORMALNOTES + " INTEGER, " +
                SongContract.CoolEntry.COLUMN_HARDDIFFICULTY + " INTEGER, " +
                SongContract.CoolEntry.COLUMN_HARDNOTES + " INTEGER, " +
                SongContract.CoolEntry.COLUMN_EXPERTDIFFICULTY + " INTEGER, " +
                SongContract.CoolEntry.COLUMN_EXPERTNOTES + " INTEGER, " +
                SongContract.CoolEntry.COLUMN_MASTERDIFFICULTY + " INTEGER, " +
                SongContract.CoolEntry.COLUMN_MASTERNOTES + " INTEGER " +
                " );";

        //execute and create the SQL Tables with the Strings defined above
        sqLiteDatabase.execSQL(SQL_SMILE_TABLE);
        sqLiteDatabase.execSQL(SQL_PURE_TABLE);
        sqLiteDatabase.execSQL(SQL_COOL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //Seeing that I am not 100% familiar with this concept of upgrading databases
        //and that this application shouldn't require adding new columns unless the API
        //incorporates it, for now, this upgrade method will clear the existing database file
        //and create it anew
    }
}

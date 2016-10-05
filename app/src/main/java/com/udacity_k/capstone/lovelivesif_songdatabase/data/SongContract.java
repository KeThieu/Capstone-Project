package com.udacity_k.capstone.lovelivesif_songdatabase.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Kenneth on 8/23/2016.
 */
public class SongContract {
    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.udacity_k.capstone.lovelivesif_songdatabase";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_SMILE = "attribute_smile";
    public static final String PATH_PURE = "attribute_pure";
    public static final String PATH_COOL = "attribute_cool";


    public static class SmileEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SMILE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SMILE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SMILE;

        //Table Schema
        public static final String TABLE_NAME = "smileTable";
        public static final String COLUMN_SONGNAME = "name";
        public static final String COLUMN_ROMANIZEDNAME = "romanized_name";
        public static final String COLUMN_TRANSLATEDNAME = "translated_name";
        public static final String COLUMN_ATTRIBUTE = "attribute";
        public static final String COLUMN_MAINUNIT = "main_unit";
        public static final String COLUMN_LENGTH = "length";
        public static final String COLUMN_BPM = "bpm";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_AVAILABLE = "available";
        public static final String COLUMN_EASYDIFFICULTY = "easy_difficulty";
        public static final String COLUMN_EASYNOTES = "easy_notes";
        public static final String COLUMN_NORMALDIFFICULTY = "normal_difficulty";
        public static final String COLUMN_NORMALNOTES = "normal_notes";
        public static final String COLUMN_HARDDIFFICULTY = "hard_difficulty";
        public static final String COLUMN_HARDNOTES = "hard_notes";
        public static final String COLUMN_EXPERTDIFFICULTY = "expert_difficulty";
        public static final String COLUMN_EXPERTNOTES = "expert_notes";
        public static final String COLUMN_MASTERDIFFICULTY = "master_difficulty";
        public static final String COLUMN_MASTERNOTES = "master_notes";

        // Define a function to build a URI to find a specific smile song by it's identifier
        public static Uri buildSmileUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static class PureEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PURE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PURE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PURE;

        //Table Schema
        public static final String TABLE_NAME = "pureTable";
        public static final String COLUMN_SONGNAME = "name";
        public static final String COLUMN_ROMANIZEDNAME = "romanized_name";
        public static final String COLUMN_TRANSLATEDNAME = "translated_name";
        public static final String COLUMN_ATTRIBUTE = "attribute";
        public static final String COLUMN_MAINUNIT = "main_unit";
        public static final String COLUMN_LENGTH = "length";
        public static final String COLUMN_BPM = "bpm";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_AVAILABLE = "available";
        public static final String COLUMN_EASYDIFFICULTY = "easy_difficulty";
        public static final String COLUMN_EASYNOTES = "easy_notes";
        public static final String COLUMN_NORMALDIFFICULTY = "normal_difficulty";
        public static final String COLUMN_NORMALNOTES = "normal_notes";
        public static final String COLUMN_HARDDIFFICULTY = "hard_difficulty";
        public static final String COLUMN_HARDNOTES = "hard_notes";
        public static final String COLUMN_EXPERTDIFFICULTY = "expert_difficulty";
        public static final String COLUMN_EXPERTNOTES = "expert_notes";
        public static final String COLUMN_MASTERDIFFICULTY = "master_difficulty";
        public static final String COLUMN_MASTERNOTES = "master_notes";

        // Define a function to build a URI to find a specific smile song by it's identifier
        public static Uri buildPureUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static class CoolEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_COOL).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COOL;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COOL;

        //Table Schema
        public static final String TABLE_NAME = "coolTable";
        public static final String COLUMN_SONGNAME = "name";
        public static final String COLUMN_ROMANIZEDNAME = "romanized_name";
        public static final String COLUMN_TRANSLATEDNAME = "translated_name";
        public static final String COLUMN_ATTRIBUTE = "attribute";
        public static final String COLUMN_MAINUNIT = "main_unit";
        public static final String COLUMN_LENGTH = "length";
        public static final String COLUMN_BPM = "bpm";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_AVAILABLE = "available";
        public static final String COLUMN_EASYDIFFICULTY = "easy_difficulty";
        public static final String COLUMN_EASYNOTES = "easy_notes";
        public static final String COLUMN_NORMALDIFFICULTY = "normal_difficulty";
        public static final String COLUMN_NORMALNOTES = "normal_notes";
        public static final String COLUMN_HARDDIFFICULTY = "hard_difficulty";
        public static final String COLUMN_HARDNOTES = "hard_notes";
        public static final String COLUMN_EXPERTDIFFICULTY = "expert_difficulty";
        public static final String COLUMN_EXPERTNOTES = "expert_notes";
        public static final String COLUMN_MASTERDIFFICULTY = "master_difficulty";
        public static final String COLUMN_MASTERNOTES = "master_notes";

        // Define a function to build a URI to find a specific smile song by it's identifier
        public static Uri buildCoolUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}

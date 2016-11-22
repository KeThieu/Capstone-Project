package com.udacity_k.capstone.lovelivesif_songdatabase.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;



/**
 * Created by Kenneth on 8/31/2016.
 */
public class SongProvider extends ContentProvider {

    private SongDbHelper mOpenHelper;
    /*
     /   ALL is all rows, single is a single row by _ID
     */
    private static final int SMILE_ALL = 100;
    private static final int SMILE_SINGLE = 101;
    private static final int PURE_ALL = 200;
    private static final int PURE_SINGLE = 201;
    private static final int COOL_ALL = 300;
    private static final int COOL_SINGLE = 301;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = SongContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, SongContract.PATH_SMILE, SMILE_ALL);
        matcher.addURI(authority, SongContract.PATH_SMILE + "/#", SMILE_SINGLE);
        matcher.addURI(authority, SongContract.PATH_PURE, PURE_ALL);
        matcher.addURI(authority, SongContract.PATH_PURE + "/#", PURE_SINGLE);
        matcher.addURI(authority, SongContract.PATH_COOL, COOL_ALL);
        matcher.addURI(authority, SongContract.PATH_COOL + "/#", COOL_SINGLE);


        return matcher;
    }

    public boolean onCreate() {
        mOpenHelper = new SongDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {

         switch(sUriMatcher.match(uri)) {
             case SMILE_ALL :
                 return SongContract.SmileEntry.CONTENT_TYPE; //DIR
             case SMILE_SINGLE :
                 return SongContract.SmileEntry.CONTENT_ITEM_TYPE; //ITEM
             case PURE_ALL :
                 return SongContract.PureEntry.CONTENT_TYPE;
             case PURE_SINGLE :
                 return SongContract.PureEntry.CONTENT_ITEM_TYPE;
             case COOL_ALL :
                 return SongContract.CoolEntry.CONTENT_TYPE;
             case COOL_SINGLE :
                 return SongContract.CoolEntry.CONTENT_ITEM_TYPE;
             default :
                 throw new UnsupportedOperationException(uri.toString());
         }
    }

    @Override
    public Cursor query(Uri uri,String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor retCursor;

        //Strings for the selection and item specific queries
        final String smile_single_query =
                SongContract.SmileEntry.TABLE_NAME + "." + SongContract.SmileEntry._ID + " = ?";
        final String pure_single_query =
                SongContract.PureEntry.TABLE_NAME + "." + SongContract.PureEntry._ID + " = ?";
        final String cool_single_query =
                SongContract.CoolEntry.TABLE_NAME + "." + SongContract.CoolEntry._ID + " = ?";

        switch(sUriMatcher.match(uri)) {
            //"attribute_smile"
            case SMILE_ALL : {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SongContract.SmileEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            //"attribute_smile/#"
            case SMILE_SINGLE : {
                long _id = ContentUris.parseId(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SongContract.SmileEntry.TABLE_NAME,
                        projection,
                        smile_single_query,
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            //"attribute_pure"
            case PURE_ALL : {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SongContract.PureEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            //"attribute_pure/#"
            case PURE_SINGLE : {
                long _id = ContentUris.parseId(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SongContract.PureEntry.TABLE_NAME,
                        projection,
                        pure_single_query,
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            //"attribute_cool"
            case COOL_ALL : {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SongContract.CoolEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            //"attribute_cool/#"
            case COOL_SINGLE : {
                long _id = ContentUris.parseId(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SongContract.CoolEntry.TABLE_NAME,
                        projection,
                        cool_single_query,
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            default :
                throw new UnsupportedOperationException(uri.toString());
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;

        switch(sUriMatcher.match(uri)) {
            case SMILE_ALL : {
                long _id = db.insert(SongContract.SmileEntry.TABLE_NAME, null, contentValues);
                if(_id > 0) {
                    //successful insert
                    returnUri = SongContract.SmileEntry.buildSmileUri(_id);
                } else {
                    //failed insert
                    throw new android.database.SQLException(uri.toString());
                }
                break;
            }
            case PURE_ALL : {
                long _id = db.insert(SongContract.PureEntry.TABLE_NAME, null, contentValues);
                if(_id > 0) {
                    //successful insert
                    returnUri = SongContract.PureEntry.buildPureUri(_id);
                } else {
                    //failed insert
                    throw new android.database.SQLException(uri.toString());
                }
                break;
            }
            case COOL_ALL : {
                long _id = db.insert(SongContract.CoolEntry.TABLE_NAME, null, contentValues);
                if(_id > 0) {
                    //successful insert
                    returnUri = SongContract.CoolEntry.buildCoolUri(_id);
                } else {
                    //failed insert
                    throw new android.database.SQLException(uri.toString());
                }
                break;
            }
            default :
                throw new UnsupportedOperationException(uri.toString());
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsUpdated;

        switch(sUriMatcher.match(uri)) {
            case SMILE_ALL : {
                rowsUpdated = db.update(SongContract.SmileEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            }
            case PURE_ALL : {
                rowsUpdated = db.update(SongContract.PureEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            }
            case COOL_ALL : {
                rowsUpdated = db.update(SongContract.CoolEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            }
            default :
                throw new UnsupportedOperationException(uri.toString());
        }

        if(rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsDeleted;

        switch(sUriMatcher.match(uri)) {
            case SMILE_ALL : {
                rowsDeleted = db.delete(SongContract.SmileEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case PURE_ALL : {
                rowsDeleted = db.delete(SongContract.PureEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case COOL_ALL : {
                rowsDeleted = db.delete(SongContract.CoolEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default :
                throw new UnsupportedOperationException(uri.toString());
        }

        if(selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] contentValues) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        switch(sUriMatcher.match(uri)) {
            case SMILE_ALL : {
                db.beginTransaction();
                int returnCount = 0;

                try {
                    for (ContentValues value : contentValues) {
                        long _id = db.insert(SongContract.SmileEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            }
            case PURE_ALL : {
                db.beginTransaction();
                int returnCount = 0;

                try {
                    for (ContentValues value : contentValues) {
                        long _id = db.insert(SongContract.PureEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            }
            case COOL_ALL : {
                db.beginTransaction();
                int returnCount = 0;

                try {
                    for (ContentValues value : contentValues) {
                        long _id = db.insert(SongContract.CoolEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            }
            default :
                return super.bulkInsert(uri, contentValues);
        }
    }
}

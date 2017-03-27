package com.ubqsys.station.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by julian on 3/18/17.
 *
 * Note: a useful set of APIs is available in SQLLiteOpenHelper class.
 * When use this class to obtain references to database, the system performs the potential long-running operations
 * of creating and updating the database only when needed and not during app startup.
 * All you need to do is call getWritableDatabase() or getReadable Database().
 * Because they can be long-running, be sure that you call getWritableDatabase() and getReadableDatabase() in a
 * background thread, such as with AscyncTask or IntentService.
 *
 */
public class DBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DB_VERSION = 1;

    public static final String DB_NAME = "UBQ.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.UEvent.TABLE_NAME + " (" +
                    DBContract.UEvent._ID + " INTEGER PRIMARY KEY," +
                    DBContract.UEvent.COLUMN_NAME_TITLE + " TEXT," +
                    DBContract.UEvent.COLUMN_NAME_SUBTITLE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBContract.UEvent.TABLE_NAME;

    /**
     * Constructor
     * @param context
     */
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}

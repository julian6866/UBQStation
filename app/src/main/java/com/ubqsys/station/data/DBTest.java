package com.ubqsys.station.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julian on 3/18/17.
 *
 *
 *
 * Use AsyncTask for:
 * Simple network operations which do not require downloading a lot of data
 * Disk-bound tasks that might take more than a few milliseconds
 *
 * Use Java threads for:
 * Network operations which involve moderate to large amounts of data (either uploading or downloading)
 * High-CPU tasks which need to be run in the background
 * Any task where you want to control the CPU usage relative to the GUI thread
 *
 * This is a test class to demonstrate how to use SQLite Database in Android app.
 *
 *
 */
public class DBTest {

    public static void demo(Context context) {

        // to access database, instantiate subclass of SQLiteOpenHelper.
        Log.i("SQLite", "create a new DB instance ...");
        DBHelper dbHelper = new DBHelper(context);

        // ------------- Write to database -------------//
        // Get the data repository in write mode.
        Log.i("SQLite", "getting a writable databse to use ...");
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        Log.i("SQLite", "prepare row data (content values) ...");
        ContentValues values = new ContentValues();
        values.put(DBContract.UEvent.COLUMN_NAME_TITLE, "test_title");
        values.put(DBContract.UEvent.COLUMN_NAME_SUBTITLE, "test_subtitle");

        // Insert the new row, returning the primary key value of the new row
        Log.i("SQLite", "insert a new DB record ...");
        long newRowId = db.insert(DBContract.UEvent.TABLE_NAME, null, values);

        // ------------- Read from database -------------//
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        Log.i("SQLite", "prepare data attributes (project) to read from DB ...");
        String[] projection = {
                DBContract.UEvent._ID,
                DBContract.UEvent.COLUMN_NAME_TITLE,
                DBContract.UEvent.COLUMN_NAME_SUBTITLE
        };

        // Filter results WHERE "title" = 'My Title'
        Log.i("SQLite", "prepare where clause ...");
        String selection = DBContract.UEvent.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { "My Title" };

        // How you want the results sorted in the resulting Cursor
        Log.i("SQLite", "prepare sort order ...");
        String sortOrder = DBContract.UEvent.COLUMN_NAME_SUBTITLE + " DESC";

        Log.i("SQLite", "reading/querying from DB using cursor ...");
        Cursor cursor = db.query(
                DBContract.UEvent.TABLE_NAME,             // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        Log.i("SQLite", "loop through returned rows using cursor ...");
        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(DBContract.UEvent._ID));
            itemIds.add(itemId);
        }
        Log.i("SQLite", "close cursor ...");
        cursor.close();

        //--------- DELETE from database --------------//
        // Define 'where' part of query.
        String selection1 = DBContract.UEvent.COLUMN_NAME_TITLE + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs1 = { "MyTitle" };
        // Issue SQL statement.

        Log.i("SQLite", "deleting from DB ...");
        db.delete(DBContract.UEvent.TABLE_NAME, selection1, selectionArgs1);

        //--------- Update a database --------------//

        // New value for one column
        ContentValues values2 = new ContentValues();
        values.put(DBContract.UEvent.COLUMN_NAME_TITLE, "title_new");

        // Which row to update, based on the title
        String selection2 = DBContract.UEvent.COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectionArgs2 = { "MyTitle" };

        Log.i("SQLite", "updating DB using where/selection clause ...");
        int count = db.update(
                DBContract.UEvent.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        //--------- Persisting database connection --------------//
        //Since getWritableDatabase() and getReadableDatabase() are expensive to call
        // when the database is closed, you should leave your database connection open
        // for as long as you possibly need to access it.
        // Typically,it is optimal to close the database in the onDestroy() of the calling Activity.
        // @Override
        //protected void onDestroy() {
        //    dbHelper.close();
        //    super.onDestroy();
        //}

        dbHelper.close();



        // To delete a database
        Log.i("SQLite", "delete a database ...");
        //context.deleteDatabase(DBHelper.DB_NAME);
    }

}

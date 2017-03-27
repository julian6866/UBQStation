package com.ubqsys.station.data;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by julian on 3/18/17.
 *
 *
 */
public final class DBContract {


    private DBContract() {
    }

    /* Inner class that defines the table contents */
    public static class UEvent implements BaseColumns {

        public static final String TABLE_NAME = "event";

        public static final String COLUMN_NAME_TITLE = "title";

        public static final String COLUMN_NAME_SUBTITLE = "subtitle";

    }
}

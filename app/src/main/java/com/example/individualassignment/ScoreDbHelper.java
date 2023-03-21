package com.example.individualassignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class ScoreDbHelper extends SQLiteOpenHelper {

    // Database information
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "score.db";

    // Define the contract class as a static inner class
    public static class ScoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "scores";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_SCORE = "score";
    }

    // Database creation SQL statement
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ScoreEntry.TABLE_NAME + " (" +
                    ScoreEntry._ID + " INTEGER PRIMARY KEY," +
                    ScoreEntry.COLUMN_NAME_NAME + " TEXT," +
                    ScoreEntry.COLUMN_NAME_SCORE + " INTEGER)";

    // Database deletion SQL statement
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ScoreEntry.TABLE_NAME;

    // Constructor
    public ScoreDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    // Called when the database version is upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}

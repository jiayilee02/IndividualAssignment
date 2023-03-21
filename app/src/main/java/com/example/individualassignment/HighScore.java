package com.example.individualassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class HighScore extends AppCompatActivity {

    TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_high_score);

        tableLayout = findViewById(R.id.my_table);
        ScoreDbHelper dbHelper = new ScoreDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                ScoreDbHelper.ScoreEntry._ID,
                ScoreDbHelper.ScoreEntry.COLUMN_NAME_NAME,
                ScoreDbHelper.ScoreEntry.COLUMN_NAME_SCORE
        };

        String sortOrder =
                ScoreDbHelper.ScoreEntry.COLUMN_NAME_SCORE + " DESC";

        Cursor cursor = db.query(
                ScoreDbHelper.ScoreEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(ScoreDbHelper.ScoreEntry._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(ScoreDbHelper.ScoreEntry.COLUMN_NAME_NAME));
            int score = cursor.getInt(cursor.getColumnIndexOrThrow(ScoreDbHelper.ScoreEntry.COLUMN_NAME_SCORE));
            addTableRow(name, Integer.toString(score));
        }

        cursor.close();


    }

    private void addTableRow(String name, String score) {

        TableRow tableRow = new TableRow(this);

        TextView nameTextView = new TextView(this);
        nameTextView.setText(name);
        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
        nameTextView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1f));

        TextView scoreTextView = new TextView(this);
        scoreTextView.setText(score);
        scoreTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
        scoreTextView.setGravity(Gravity.END);
        scoreTextView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1f));

        tableRow.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT
        ));
        tableRow.addView(nameTextView);
        tableRow.addView(scoreTextView);

        tableLayout.addView(tableRow);
    }

}

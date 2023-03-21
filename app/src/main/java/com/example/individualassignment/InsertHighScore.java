package com.example.individualassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InsertHighScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_high_score);


        ScoreDbHelper dbHelper = new ScoreDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Intent intent = getIntent();
        int totalCount = intent.getIntExtra("TotalCount", 0);

        TextView totalScore = findViewById(R.id.total_Score);
        totalScore.setText("Total Score: "+ totalCount);

        EditText editText = findViewById(R.id.editTextName);

        Button btn = findViewById(R.id.btnSubmit);
        btn.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {

                String name = editText.getText().toString();
                ContentValues values = new ContentValues();
                values.put(ScoreDbHelper.ScoreEntry.COLUMN_NAME_NAME, name);
                values.put(ScoreDbHelper.ScoreEntry.COLUMN_NAME_SCORE, totalCount);

                long newRowId = db.insert(ScoreDbHelper.ScoreEntry.TABLE_NAME, null, values);

                finish();
            }
        });

    }
}
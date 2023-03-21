package com.example.individualassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGame = findViewById(R.id.startGame);
        Button btnScore = findViewById(R.id.highScore);

        btnGame.setOnClickListener(new OnClickListener(){

            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, gameActivity.class);
                intent.putExtra("Levels", "Level 1");
                intent.putExtra("TotalCount", 0);
                startActivity(intent);
            }
        });

        btnScore.setOnClickListener(new OnClickListener(){

            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, HighScore.class);
                startActivity(intent);
            }
        });

    }

}
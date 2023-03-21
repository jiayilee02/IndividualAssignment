package com.example.individualassignment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

public class gameActivity extends AppCompatActivity {

    private List<ImageView> imageViews = new ArrayList<>();
    private Random random;
    private ImageView currentImageView;
    private boolean isClicked;
    private int levelNum;
    private int previousIndex = -1;
    private CountDownTimer countDownTimer;
    private int totalCount = 0;
    private boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String level = intent.getStringExtra("Levels");
        levelNum = Character.getNumericValue(level.charAt(level.length() - 1));
        totalCount = intent.getIntExtra("TotalCount", 0);
        ActionBar actionBar = getSupportActionBar(); // for AppCompatActivity
        actionBar.setTitle(level);

        setContentView(DisplayView(levelNum));

        for (final ImageView imageView : imageViews) {
            if (imageView != currentImageView) {
                isClicked = false;
                imageView.setAlpha(0.2f);
            }
        }

        random = new Random();
        isClicked = true;

        startGame();

        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                if(!isExit){
                    if(levelNum != 5){
                        Intent intent = new Intent(gameActivity.this, gameActivity.class);
                        int nextLevelNum = levelNum + 1;
                        String nextLevel = "Level" + nextLevelNum;
                        intent.putExtra("Levels", nextLevel);
                        intent.putExtra("TotalCount", totalCount);
                        startActivity(intent);
                        finish();
                    }
                    else {

                            Intent intent = new Intent(gameActivity.this, InsertHighScore.class);
                            intent.putExtra("TotalCount", totalCount);
                            startActivity(intent);
                            finish();

                    }

                }

            }
        };
        countDownTimer.start();


    }

    public GridLayout DisplayView (int level){
        // Get the window manager and default display
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        // Get the width of the screen
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;

        ShapeDrawable arc = new ShapeDrawable(new ArcShape(0, 360));
        arc.setIntrinsicWidth((int) ((screenWidth-20) / (Math.sqrt(GameLevel(level)))));
        arc.setIntrinsicHeight((int) ((screenWidth-20) / (Math.sqrt(GameLevel(level)))));
        arc.getPaint().setColor(Color.parseColor("#6F308B"));

        // Create a new GridLayout
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setPadding(10,20,10,10);

        gridLayout.setColumnCount((int)Math.sqrt(GameLevel(level)));
        gridLayout.setRowCount((int)Math.sqrt(GameLevel(level)));
        for(int i = 0; i < GameLevel(level); i ++){
            imageViews.add(new ImageView(this));
        }
        int count = 0;
        for (int row = 0; row < (int)Math.sqrt(GameLevel(level)); row++){
            for(int col = 0; col < (int)Math.sqrt(GameLevel(level)); col++){

                //imageView[count].setImageResource(R.drawable.image1);
                imageViews.get(count).setImageDrawable(arc);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(row);
                params.columnSpec = GridLayout.spec(col);

                gridLayout.addView(imageViews.get(count), params);
                count++;
            }
        }

        return gridLayout;
    }

    public void startGame (){

        int randomIndex = random.nextInt(imageViews.size());

        while(randomIndex == previousIndex) {
            randomIndex = random.nextInt(imageViews.size());
        }
        previousIndex = randomIndex;
        currentImageView = imageViews.get(randomIndex);

        for(ImageView img : imageViews){
            if(img == currentImageView){
                img.setClickable(true);
            }
            else{
                img.setClickable(false);
            }
        }
        currentImageView.setAlpha(1f);

        currentImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //imageViews.remove(currentImageView);

                currentImageView.setAlpha(0.2f);
                totalCount++;
                // Highlight next ImageView
                startGame();
            }
        });

    }

    public int GameLevel (int level){
        if(level == 1)
            return 4;
        else if(level == 2)
            return 9;
        else if(level == 3)
            return 16;
        else if(level == 4)
            return 25;
        else if(level == 5)
            return 36;
        return 0;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main,menu);
        menu.add("End Game")
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {@Override
                public boolean onMenuItemClick(MenuItem item) {
                    countDownTimer.cancel();
                    isExit = true;
                    finish();
                    return false;
                }
                });
        return true;
    }

}
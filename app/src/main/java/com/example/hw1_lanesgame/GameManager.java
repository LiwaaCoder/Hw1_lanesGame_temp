package com.example.hw1_lanesgame;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.imageview.ShapeableImageView;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class GameManager extends AppCompatActivity
{

    public static final int LIVES = 3;
    public static final int VIBRATE_LENGTH = 200; //mil sec
    public static  int ROWS = 5;
    public static  int COLS = 3;
    private static  int LENGTH_LONG = 3;
    private static  int GAME_SPEED = 1000; //mil sec
    final ImageView game_left_bear;
    final ImageView game_middle_bear;
    final ImageView game_right_bear;
    final ShapeableImageView[] game_IMG_hearts;
    Context context;
    ShapeableImageView[][] game_grid_IMG_stones;
    private int score = 0;
    private int currentLives;
    private int life;
    private int[][] stonesArr;
    private int[] prevStonesArr;
    private int counter;
    private int car_curr = 1; // 0 - right, 1 - middle, 2 - left


    public GameManager(Context context)
    {

        this.context=context;
        this.life = life;
        this.car_curr = 1;
        this.currentLives = LIVES;
        this.stonesArr = new int[ROWS+1][COLS]; // +1 using to simulate "crash" with the bear
        this.prevStonesArr = new int[ROWS];
        this.counter = 0;

        game_left_bear = (ImageView) ((Activity)context).findViewById(R.id.game_IMG_car3);
        game_middle_bear = (ImageView) ((Activity)context).findViewById(R.id.game_IMG_car2);
        game_right_bear = (ImageView) ((Activity)context).findViewById(R.id.game_IMG_car1);

        game_IMG_hearts = new ShapeableImageView[]{
                (ShapeableImageView) ((Activity)context).findViewById(R.id.game_IMG_heart1),
                (ShapeableImageView) ((Activity)context).findViewById(R.id.game_IMG_heart2),
                (ShapeableImageView) ((Activity)context).findViewById(R.id.game_IMG_heart3),
        };

        game_grid_IMG_stones = new ShapeableImageView[][]{
                {(ShapeableImageView) ((Activity)context).findViewById(R.id.game_IMG_stoneC1),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_IMG_stoneC2),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_IMG_stoneC3)
                },
                {(ShapeableImageView) ((Activity)context).findViewById(R.id.game_IMG_stoneC4),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_IMG_stoneC5),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_IMG_stoneC6)
                },
                {(ShapeableImageView) ((Activity)context).findViewById(R.id.game_IMG_stoneC7),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_IMG_stoneC8),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_IMG_stoneC9)
                },
                {(ShapeableImageView) ((Activity)context).findViewById(R.id.game_IMG_stoneC10),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_IMG_stoneC11),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_IMG_stoneC12)
                },
                {(ShapeableImageView) ((Activity)context).findViewById(R.id.game_IMG_stoneC13),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_IMG_stoneC14),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_IMG_stoneC15)
                },
        };
    }

    public static void vibrateDevice(Context mContext){
        Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VIBRATE_LENGTH);
    }

    public void car_left()
    {
        if(car_curr == 2){
            return;
        }
        else if(car_curr == 1){
            game_left_bear.setVisibility(View.INVISIBLE);
            game_middle_bear.setVisibility(View.INVISIBLE);
            game_right_bear.setVisibility(View.VISIBLE);
            car_curr = 2;
        }
        else{
            game_middle_bear.setVisibility(View.VISIBLE);
            game_left_bear.setVisibility(View.INVISIBLE);
            game_right_bear.setVisibility(View.INVISIBLE);
            car_curr = 1;
        }
    }

    public void car_right()
    {
        if(car_curr == 0){
            return;
        }
        else if(car_curr == 1){
            game_right_bear.setVisibility(View.INVISIBLE);
            game_middle_bear.setVisibility(View.INVISIBLE);
            game_left_bear.setVisibility(View.VISIBLE);
            car_curr = 0;
        }
        else{
            game_middle_bear.setVisibility(View.VISIBLE);
            game_left_bear.setVisibility(View.INVISIBLE);
            game_right_bear.setVisibility(View.INVISIBLE);
            car_curr = 1;
        }
    }

    public void startGame()
    {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (currentLives > 0 && currentLives <= LIVES)
                {
                    update_stones();
                    showStoneOnScreen();
                    IsAccident();
                }
            }
        }, 0, GAME_SPEED);
    }

    private void IsAccident()
    {
        int location = car_curr;
        if(stonesArr[ROWS][car_curr] == 1){
            vibrateDevice(this.context);
            update_hearts();
        }
    }

    private void update_hearts()
    {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                game_IMG_hearts[currentLives-1].setVisibility(View.INVISIBLE);

                if(currentLives == 1){
                    Toast.makeText(context,"GAME OVER", Toast.LENGTH_SHORT).show();
                } else {
                    currentLives--;
                }
            }
        });

    }


    private void showStoneOnScreen() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLS; j++) {
                        if(stonesArr[i][j] == 1)
                        {
                            game_grid_IMG_stones[i][j].setVisibility(View.VISIBLE);
                        }else{
                            game_grid_IMG_stones[i][j].setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        });
    }




    private void update_stones()
    {
        Random ran = new Random();
        int randomStoneLocation = ran.nextInt(3);

        if(randomStoneLocation == 2)
        {
            stonesArr[0][0] = 0;
            stonesArr[0][1] = 0;
            stonesArr[0][2] = 1;
        }else if(randomStoneLocation == 1){
            stonesArr[0][0] = 0;
            stonesArr[0][1] = 1;
            stonesArr[0][2] = 0;
        }else if(randomStoneLocation == 0){
            stonesArr[0][0] = 1;
            stonesArr[0][1] = 0;
            stonesArr[0][2] = 0;
        }

        for (int i = (ROWS); i > 0; i--) {
            for (int j = 0; j < COLS; j++) {
                stonesArr[i][j] = stonesArr[i-1][j];
            }
        }
    }


}

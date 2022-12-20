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
    public static final int VIBRATE_LENGTH = 120; //mil sec
    private static final int GAME_SPEED = 1000; //mil sec
    final ImageView game_car_c1;
    final ImageView game_car_c2;
    final ImageView game_car_c3;
    final ImageView game_car_c4;
    final ImageView game_car_c5;
    private final   int ROWS = 8;
    private final   int COLS = 3;

    final ShapeableImageView[] game_IMG_hearts;
    Context context;
    ShapeableImageView[][] game_grid_IMG_stones;
    private final int score = 0;
    private int currentLives;
    private int life;
    private final int[][] stonesArr;
    private final int[] prevStonesArr;
    private final int counter;
    private int car_curr = 1; // 0 - left , 1 - left +1 , 2 - mid , 3 - right-1 , 4 right


    public GameManager(Context context)
    {

        this.context=context;
        this.life = life;
        this.car_curr = 2;
        this.currentLives = LIVES;
        this.stonesArr = new int[ROWS+1][COLS]; // +1 using to simulate "crash" with the bear
        this.prevStonesArr = new int[ROWS];
        this.counter = 0;

        game_car_c1 = ((Activity)context).findViewById(R.id.game_IMG_car1);
        game_car_c2 = ((Activity)context).findViewById(R.id.game_IMG_car2);
        game_car_c3 = ((Activity)context).findViewById(R.id.game_IMG_car3);
        game_car_c4 = ((Activity)context).findViewById(R.id.game_IMG_car4);
        game_car_c5=((Activity)context).findViewById(R.id.game_IMG_car5);

        game_IMG_hearts = new ShapeableImageView[]{
                ((Activity)context).findViewById(R.id.game_IMG_heart1),
                ((Activity)context).findViewById(R.id.game_IMG_heart2),
                ((Activity)context).findViewById(R.id.game_IMG_heart3),
        };

        game_grid_IMG_stones = new ShapeableImageView[][]
                {
                        {((Activity) context).findViewById(R.id.game_IMG_stoneC1),
                                ((Activity) context).findViewById(R.id.game_IMG_stoneC2),
                                ((Activity) context).findViewById(R.id.game_IMG_stoneC3)
                        },
                        {((Activity) context).findViewById(R.id.game_IMG_stoneC4),
                                ((Activity) context).findViewById(R.id.game_IMG_stoneC5),
                                ((Activity) context).findViewById(R.id.game_IMG_stoneC6)
                        },
                        {((Activity) context).findViewById(R.id.game_IMG_stoneC7),
                                ((Activity) context).findViewById(R.id.game_IMG_stoneC8),
                                ((Activity) context).findViewById(R.id.game_IMG_stoneC9)
                        },
                        {((Activity) context).findViewById(R.id.game_IMG_stoneC10),
                                ((Activity) context).findViewById(R.id.game_IMG_stoneC11),
                                ((Activity) context).findViewById(R.id.game_IMG_stoneC12)
                        },
                        {((Activity) context).findViewById(R.id.game_IMG_stoneC13),
                                ((Activity) context).findViewById(R.id.game_IMG_stoneC14),
                                ((Activity) context).findViewById(R.id.game_IMG_stoneC15)
                        },
                        {((Activity) context).findViewById(R.id.game_IMG_stoneC16),
                                ((Activity) context).findViewById(R.id.game_IMG_stoneC17),
                                ((Activity) context).findViewById(R.id.game_IMG_stoneC18)
                        },
                        {((Activity) context).findViewById(R.id.game_IMG_stoneC19),
                                ((Activity) context).findViewById(R.id.game_IMG_stoneC20),
                                ((Activity) context).findViewById(R.id.game_IMG_stoneC21)
                        },
                        {((Activity) context).findViewById(R.id.game_IMG_stoneC22),
                                ((Activity) context).findViewById(R.id.game_IMG_stoneC23),
                                ((Activity) context).findViewById(R.id.game_IMG_stoneC24)
                        },
                        {((Activity) context).findViewById(R.id.game_IMG_stoneC25),
                                }
                };

    }

    public static void vibrateDevice(Context mContext){
        Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VIBRATE_LENGTH);
    }

    public void car_left()
    {
        if(car_curr == 0)
        {
            return;
        }
        else if(car_curr == 1)
        {
            game_car_c1.setVisibility(View.VISIBLE);
            game_car_c2.setVisibility(View.INVISIBLE);
            game_car_c3.setVisibility(View.INVISIBLE);
            game_car_c4.setVisibility(View.INVISIBLE);
            game_car_c5.setVisibility(View.INVISIBLE);
            car_curr = 0;
        }
        else if (car_curr==2)
        {
            game_car_c2.setVisibility(View.VISIBLE);
            game_car_c1.setVisibility(View.INVISIBLE);
            game_car_c3.setVisibility(View.INVISIBLE);
            game_car_c4.setVisibility(View.INVISIBLE);
            game_car_c5.setVisibility(View.INVISIBLE);
            car_curr = 1;
        }
        else if (car_curr == 3)
        {
            game_car_c2.setVisibility(View.INVISIBLE);
            game_car_c1.setVisibility(View.INVISIBLE);
            game_car_c3.setVisibility(View.VISIBLE);
            game_car_c4.setVisibility(View.INVISIBLE);
            game_car_c5.setVisibility(View.INVISIBLE);
            car_curr = 2;
        }
        else
        {
            game_car_c2.setVisibility(View.INVISIBLE);
            game_car_c1.setVisibility(View.INVISIBLE);
            game_car_c3.setVisibility(View.INVISIBLE);
            game_car_c4.setVisibility(View.VISIBLE);
            game_car_c5.setVisibility(View.INVISIBLE);
            car_curr = 3;
        }
    }

    public void car_right()
    {
        if(car_curr == 0)
        {
            game_car_c1.setVisibility(View.INVISIBLE);
            game_car_c2.setVisibility(View.VISIBLE);
            game_car_c3.setVisibility(View.INVISIBLE);
            game_car_c4.setVisibility(View.INVISIBLE);
            game_car_c5.setVisibility(View.INVISIBLE);
            car_curr = 1;
        }
        else if(car_curr == 1)
        {
            game_car_c1.setVisibility(View.INVISIBLE);
            game_car_c2.setVisibility(View.INVISIBLE);
            game_car_c3.setVisibility(View.VISIBLE);
            game_car_c4.setVisibility(View.INVISIBLE);
            game_car_c5.setVisibility(View.INVISIBLE);
            car_curr = 2;
        }
        else if (car_curr == 2)
        {
            game_car_c1.setVisibility(View.INVISIBLE);
            game_car_c2.setVisibility(View.INVISIBLE);
            game_car_c3.setVisibility(View.INVISIBLE);
            game_car_c4.setVisibility(View.VISIBLE);
            game_car_c5.setVisibility(View.INVISIBLE);
            car_curr = 3;
        }
        else if (car_curr == 3)
        {
            game_car_c1.setVisibility(View.INVISIBLE);
            game_car_c2.setVisibility(View.INVISIBLE);
            game_car_c3.setVisibility(View.INVISIBLE);
            game_car_c4.setVisibility(View.INVISIBLE);
            game_car_c5.setVisibility(View.VISIBLE);
            car_curr = 4;
        }
        else
            return;
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
        if(stonesArr[ROWS][car_curr] == 1){
            vibrateDevice(this.context);
            update_hearts();
        }
    }

    private void update_hearts()
    {

        runOnUiThread(() -> {

            game_IMG_hearts[currentLives-1].setVisibility(View.INVISIBLE);

            if(currentLives == 1){
                Toast.makeText(context,"GAME OVER", Toast.LENGTH_SHORT).show();
            } else {
                currentLives--;
            }
        });

    }


    private void showStoneOnScreen() {
        runOnUiThread(() -> {
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
        });
    }




    private void update_stones()
    {
        Random ran = new Random();
        int randomStoneLocation = ran.nextInt(5);

        if(randomStoneLocation == 2)
        {
            stonesArr[0][0] = 0;
            stonesArr[0][1] = 0;
            stonesArr[0][2] = 1;
        }else if(randomStoneLocation == 1){
            stonesArr[0][0] = 0;
            stonesArr[0][1] = 1;
            stonesArr[0][2] = 0;
        }
        else if(randomStoneLocation == 0)
        {

            stonesArr[0][0] = 1;
            stonesArr[0][1] = 0;
            stonesArr[0][2] = 0;
        }
        else if(randomStoneLocation == 3)
        {

            stonesArr[0][0] = 0;
            stonesArr[0][1] = 0;
            stonesArr[0][2] = 1;
        }
        else if(randomStoneLocation == 4)
        {

            stonesArr[0][0] = 1;
            stonesArr[0][1] = 0;
            stonesArr[0][2] = 0;
        }

        for (int i = (ROWS); i > 0; i--)
        {
            for (int j = 0; j < COLS; j++) {
                stonesArr[i][j] = stonesArr[i-1][j];
            }
        }
    }


}

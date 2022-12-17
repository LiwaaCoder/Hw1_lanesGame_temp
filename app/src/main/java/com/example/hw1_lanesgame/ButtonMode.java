package com.example.hw1_lanesgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ButtonMode extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();

        final Button game_btn_right = findViewById(R.id.game_BTN_right);
        final Button game_btn_left = findViewById(R.id.game_BTN_left);

        GameManager gameManager = new GameManager(this);

        gameManager.startGame();

        game_btn_right.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                //main_TV_score.setText("123");
                gameManager.car_right();
            }
        });

        game_btn_left.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                gameManager.car_left();
            }
        });
    }





    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
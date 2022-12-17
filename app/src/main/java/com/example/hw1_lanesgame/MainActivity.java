package com.example.hw1_lanesgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btn_start_game = findViewById(R.id.btn_start_game);
        ButtonMode gameActivity = new ButtonMode();

        btn_start_game.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, ButtonMode.class);
                MainActivity.this.startActivity(myIntent);
                finish();

            }
        });
    }


}
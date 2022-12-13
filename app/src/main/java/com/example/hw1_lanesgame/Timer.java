package com.example.hw1_lanesgame;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textview.MaterialTextView;

public class Timer extends AppCompatActivity {

    final int DELAY = 1000;
    private MaterialTextView timer_LBL_info;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        timer_LBL_info = findViewById(R.id.game_LBL_score);
    }

    private void updateTimeUI() {
        timer_LBL_info.setText("" + System.currentTimeMillis());
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(99999999, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.d("pttt", "A: " + Thread.currentThread().getName() + " - " + System.currentTimeMillis());
                updateTimeUI();

            }

            public void onFinish() {
            }

        }.start();
    }

    private void stopTimer() {
        countDownTimer.cancel();
    }
}
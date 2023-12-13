package com.jnu.student.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.jnu.student.R;

public class GameView extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener {
    private SurfaceHolder surfaceHolder;
    private GameEngine gameEngine;
    private TextView scoreTextView;
    private int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_game_view);

        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        scoreTextView = findViewById(R.id.scoreTextView);
        score = 0;

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameEngine = new GameEngine(surfaceHolder, () -> {
            score++;
            runOnUiThread(() -> scoreTextView.setText("Score: " + score));
        });
        gameEngine.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        // 处理SurfaceView尺寸变化的逻辑
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        gameEngine.stop();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.resetButton) {
            score = 0;
            scoreTextView.setText("Score: 0");
        }
    }
}
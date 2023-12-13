package com.jnu.student.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private int screenWidth, screenHeight;
    private int bookCount;
    private boolean isGameRunning;
    private RectF bookRect;
    private Paint bookPaint, textPaint;

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        getHolder().addCallback(this);

        bookPaint = new Paint();
        bookPaint.setColor(Color.BLUE);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(50);

        bookRect = new RectF();

        isGameRunning = false;
        bookCount = 0;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screenWidth = getWidth();
        screenHeight = getHeight();
        startGame();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopGame();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float touchX = event.getX();
            float touchY = event.getY();

            if (bookRect.contains(touchX, touchY)) {
                bookCount++;
                generateRandomBookPosition();
            }
        }

        return true;
    }

    private void startGame() {
        isGameRunning = true;
        generateRandomBookPosition();
        gameThread = new GameThread();
        gameThread.start();
    }

    private void generateRandomBookPosition() {
        Random random = new Random();
        float bookSize = 200;
        float bookX = random.nextInt(screenWidth - (int) bookSize);
        float bookY = random.nextInt(screenHeight - (int) bookSize);

        bookRect.set(bookX, bookY, bookX + bookSize, bookY + bookSize);
    }
    private void stopGame() {
        isGameRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void drawGame(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawRect(bookRect, bookPaint);
        canvas.drawText("Books: " + bookCount, 50, 50, textPaint);
    }

    private class GameThread extends Thread {
        private static final int FPS = 30;
        private static final long FRAME_TIME = 1000 / FPS;
        @Override
        public void run() {
            while (isGameRunning) {
                long startTime = System.currentTimeMillis();

                Canvas canvas = null;
                try {
                    canvas = getHolder().lockCanvas();
                    synchronized (getHolder()) {
                        if (canvas != null) {
                            drawGame(canvas);
                        }
                    }
                } finally {
                    if (canvas != null) {
                        getHolder().unlockCanvasAndPost(canvas);
                    }
                }
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                if (elapsedTime < FRAME_TIME) {
                    try {
                        Thread.sleep(FRAME_TIME - elapsedTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}


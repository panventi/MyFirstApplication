package com.jnu.student.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.Random;

class GameEngine {
    private SurfaceHolder surfaceHolder;
    private Thread gameThread;
    private boolean isRunning;
    private GameEventListener gameEventListener;
    private Random random;
    private boolean isHit;
    private int targetX, targetY;
    private int targetRadius;
    public interface GameEventListener {
        void onHit();
    }

    public GameEngine(SurfaceHolder surfaceHolder, GameEventListener gameEventListener) {
        this.surfaceHolder = surfaceHolder;
        this.gameEventListener = gameEventListener;
        random = new Random();
        targetRadius = 50;
    }

    public void start() {
        isRunning = true;
        gameThread = new Thread(this::gameLoop);
        gameThread.start();
    }

    public void stop() {
        isRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void gameLoop() {
        while (isRunning) {
            update();
            draw();
        }
    }

    private void update() {
        if (isHit) {
            // 重新生成目标地鼠的位置
            targetX = random.nextInt(surfaceHolder.getSurfaceFrame().width() - targetRadius * 2) + targetRadius;
            targetY = random.nextInt(surfaceHolder.getSurfaceFrame().height() - targetRadius * 2) + targetRadius;
            isHit = false;
        }
    }

    private void draw() {
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas != null) {
            // 绘制游戏画面
            canvas.drawColor(Color.WHITE); // 清空画布

            // 绘制地鼠
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(targetX, targetY, targetRadius, paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float touchX = event.getX();
            float touchY = event.getY();

            // 判断点击是否命中地鼠
            if (Math.sqrt(Math.pow(touchX - targetX, 2) + Math.pow(touchY - targetY, 2)) <= targetRadius) {
                isHit = true;
                gameEventListener.onHit();
            }
        }
        return true;
    }
}

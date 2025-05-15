package com.example.appgames;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class SnakeGameActivity extends AppCompatActivity {

    private SurfaceView gameSurface;
    private SurfaceHolder holder;
    private Paint paint, foodPaint, scorePaint;
    private boolean isPlaying = true;
    private boolean isGameOver = false;
    private TextView textScore;


    private int snakeX = 200, snakeY = 200;
    private int snakeSpeed = 20;
    private int foodX, foodY;
    private int score = 0;
    private String direction = "RIGHT";
    private List<int[]> snakeBody = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_game);

        textScore = findViewById(R.id.textScore);

        gameSurface = findViewById(R.id.gameSurface);
        holder = gameSurface.getHolder();


        initializePaints();

        gameSurface.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                holder = surfaceHolder;
                resetGame();
                startGameLoop();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {}

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                isPlaying = false;
            }
        });

        gameSurface.setOnTouchListener((v, event) -> handleTouch(event));
    }

    private void initializePaints() {
        paint = new Paint();
        paint.setColor(Color.GREEN);

        foodPaint = new Paint();
        foodPaint.setColor(Color.RED);

        scorePaint = new Paint();
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(40);
    }

    private boolean handleTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float touchX = event.getX();
            float touchY = event.getY();

            if (Math.abs(touchX - snakeX) > Math.abs(touchY - snakeY)) {
                if (!direction.equals("LEFT") && !direction.equals("RIGHT"))
                    direction = (touchX < snakeX) ? "LEFT" : "RIGHT";
            } else {
                if (!direction.equals("UP") && !direction.equals("DOWN"))
                    direction = (touchY < snakeY) ? "UP" : "DOWN";
            }
        }
        return true;
    }

    private void resetGame() {
        snakeX = 200;
        snakeY = 200;
        snakeBody.clear();
        snakeBody.add(new int[]{snakeX, snakeY});

        if (!isGameOver) { // Só reseta a pontuação depois que Game Over foi exibido!
            score = 0;
            if (textScore != null) {
                textScore.post(() -> textScore.setText("Pontuação: " + score));
            }
        }

        generateFood();
        isPlaying = true;
    }

    private void generateFood() {
        do {
            foodX = (int) (Math.random() * gameSurface.getWidth() / 20) * 20;
            foodY = (int) (Math.random() * gameSurface.getHeight() / 20) * 20;
        } while (isOnSnake(foodX, foodY));
    }

    private boolean isOnSnake(int x, int y) {
        for (int[] part : snakeBody) {
            if (part[0] == x && part[1] == y) return true;
        }
        return false;
    }

    private void startGameLoop() {
        new Thread(() -> {
            while (true) {
                if (isPlaying && !isGameOver) { // Só continua se o jogo não estiver pausado
                    updateGame();
                    renderGame();
                }

                try { Thread.sleep(100); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
        }).start();
    }

    private void updateGame() {
        int newX = snakeX, newY = snakeY;

        if (direction.equals("UP")) newY -= snakeSpeed;
        else if (direction.equals("DOWN")) newY += snakeSpeed;
        else if (direction.equals("LEFT")) newX -= snakeSpeed;
        else if (direction.equals("RIGHT")) newX += snakeSpeed;

        snakeBody.add(0, new int[]{newX, newY});
        snakeX = newX;
        snakeY = newY;

        if (snakeX == foodX && snakeY == foodY) {
            score++;
            runOnUiThread(() -> textScore.setText("Pontuação: " + score)); // Atualiza a pontuação corretamente
            generateFood();

        } else {
            snakeBody.remove(snakeBody.size() - 1);
        }

        // Chama Game Over antes de resetar o jogo
        if (snakeX < 0 || snakeX > gameSurface.getWidth() || snakeY < 0 || snakeY > gameSurface.getHeight()) {
            showGameOver(); // Exibe a pontuação antes de resetar
            return; // Para evitar que o jogo continue após Game Over
        }

        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeBody.get(i)[0] == snakeX && snakeBody.get(i)[1] == snakeY) {
                showGameOver(); // Exibe a pontuação antes de resetar
                return; // Para evitar que o jogo continue após Game Over
            }
        }
    }

    private void renderGame() {
        if (holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.BLACK);

            canvas.drawCircle(foodX, foodY, 15, foodPaint);

            for (int[] part : snakeBody) {
                canvas.drawCircle(part[0], part[1], 15, paint);
            }



            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void showGameOver() {
        runOnUiThread(() -> {
            isGameOver = true; // Pausa o jogo

            if (textScore != null) { // Evita NullPointerException
                textScore.post(() -> textScore.setText("GAME OVER! Pontuação final: " + score));
            }

            Button restartButton = findViewById(R.id.buttonRestart);
            if (restartButton != null) { // Evita erro ao acessar botão
                restartButton.setVisibility(View.VISIBLE);
            }

            isPlaying = false; // Garante que o jogo realmente pare
        });
    }

    public void restartGame(View view) {
        isGameOver = false; // Retira o estado de Game Over
        isPlaying = true; // Permite que o jogo continue rodando

        score = 0;

        if (textScore != null) {
            textScore.post(() -> textScore.setText("Pontuação: " + score));
        }

        Button restartButton = findViewById(R.id.buttonRestart);
        if (restartButton != null) {
            restartButton.setVisibility(View.GONE);
        }

        resetGame(); // Reinicia o jogo e permite movimento novamente
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        isPlaying = false;
    }
}

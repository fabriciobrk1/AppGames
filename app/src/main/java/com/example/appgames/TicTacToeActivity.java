package com.example.appgames;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TicTacToeActivity extends AppCompatActivity {
    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoe);

        initializeButtons();
    }

    private void initializeButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);

                buttons[i][j].setOnClickListener(this::onButtonClick);
            }
        }
    }

    private void onButtonClick(View v) {
        Button button = (Button) v;
        if (!button.getText().toString().equals("")) return;

        button.setText(player1Turn ? "X" : "O");
        roundCount++;

        if (roundCount == 9 && !checkWinner()) { // Primeiro verifica o empate!
            showResult("Empate!");
        } else if (checkWinner()) { // Agora verifica a vitória
            showResult("Jogador " + (player1Turn ? "X" : "O") + " venceu!");
        } else {
            player1Turn = !player1Turn;
        }
    }

    private boolean checkWinner() {
        return checkRows() || checkColumns() || checkDiagonals();
    }

    private boolean checkRows() {
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                    buttons[i][0].getText().equals(buttons[i][2].getText()) &&
                    !buttons[i][0].getText().equals("")) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns() {
        for (int i = 0; i < 3; i++) {
            if (buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                    buttons[0][i].getText().equals(buttons[2][i].getText()) &&
                    !buttons[0][i].getText().equals("")) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals() {
        return (buttons[0][0].getText().equals(buttons[1][1].getText()) &&
                buttons[0][0].getText().equals(buttons[2][2].getText()) &&
                !buttons[0][0].getText().equals("")) ||
                (buttons[0][2].getText().equals(buttons[1][1].getText()) &&
                        buttons[0][2].getText().equals(buttons[2][0].getText()) &&
                        !buttons[0][2].getText().equals(""));
    }

    private void showResult(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        resetGame();
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
    }
}
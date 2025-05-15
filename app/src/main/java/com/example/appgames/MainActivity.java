package com.example.appgames;

import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    private ViewPager viewPagerGames;
    private GamePagerAdapter adapter;
    private List<Game> gameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* //Teste manual: abrir SnakeGameActivity diretamente
        startActivity(new Intent(MainActivity.this, SnakeGameActivity.class));
        */

        viewPagerGames = findViewById(R.id.viewPagerGames);
        gameList = new ArrayList<>();
        gameList.add(new Game("Jogo da Velha", R.drawable.tictactoe));
        gameList.add(new Game("Jogo da Cobrinha", R.drawable.snake));
        //gameList.add(new Game("Pedra, Papel, Tesoura", R.drawable.rps));

        adapter = new GamePagerAdapter(getSupportFragmentManager(), gameList, viewPagerGames);
        viewPagerGames.setAdapter(adapter);






    }
}
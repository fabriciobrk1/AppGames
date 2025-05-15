package com.example.appgames;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameFragment extends Fragment {
    private static final String ARG_GAME = "game";
    private Game game;
    private ViewPager viewPager; // Referência ao ViewPager

    public static GameFragment newInstance(Game game, ViewPager viewPager) {
        GameFragment fragment = new GameFragment();
        fragment.viewPager = viewPager; // Guardamos referência ao ViewPager
        Bundle args = new Bundle();
        args.putSerializable(ARG_GAME, game);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            game = (Game) getArguments().getSerializable(ARG_GAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        // Configurar elementos da interface
        ImageView gameImageView = view.findViewById(R.id.gameImageView);
        gameImageView.setImageResource(game.getImageResource());
        TextView gameTitle = view.findViewById(R.id.gameTitle);
        Button buttonPlay = view.findViewById(R.id.buttonPlay);
        Button buttonNext = view.findViewById(R.id.buttonNext);
        Button buttonBack = view.findViewById(R.id.buttonBack);

        // Definir os valores dos componentes
        gameImageView.setImageResource(game.getImageResource());
        gameTitle.setText(game.getName());

        // botão "Jogar"
        buttonPlay.setOnClickListener(v -> {
            if (game.getName().equals("Jogo da Velha")) {
                startActivity(new Intent(getActivity(), TicTacToeActivity.class));
            } else if (game.getName().equals("Jogo da Cobrinha")) {
                startActivity(new Intent(getActivity(), SnakeGameActivity.class));
            }
        });

        // botão Próximo
        buttonNext.setOnClickListener(v -> {
            if (viewPager != null) {
                int nextItem = viewPager.getCurrentItem() + 1;
                if (nextItem < viewPager.getAdapter().getCount()) {
                    viewPager.setCurrentItem(nextItem, true);
                }
            }
        });

        // botao voltar
        buttonBack.setOnClickListener(v -> {
            if (viewPager != null) {
                int previousItem = viewPager.getCurrentItem() - 1;
                if (previousItem >= 0) {
                    viewPager.setCurrentItem(previousItem, true);
                }
            }
        });

        return view;
    }
}
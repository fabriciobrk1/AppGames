package com.example.appgames;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import java.util.List;

public class GamePagerAdapter extends FragmentPagerAdapter {
    private List<Game> gameList;
    private ViewPager viewPager; // Adicionando a referência ao ViewPager

    public GamePagerAdapter(FragmentManager fm, List<Game> gameList, ViewPager viewPager) {
        super(fm);
        this.gameList = gameList;
        this.viewPager = viewPager;
    }

    @Override
    public Fragment getItem(int position) {
        return GameFragment.newInstance(gameList.get(position), viewPager); // Passando o ViewPager corretamente
    }

    @Override
    public int getCount() {
        return gameList.size();
    }
}
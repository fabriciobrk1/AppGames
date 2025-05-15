package com.example.appgames;

import java.io.Serializable;

public class Game implements Serializable {
    private String name;
    private int imageResource;

    public Game(String name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }
}


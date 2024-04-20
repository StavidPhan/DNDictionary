package com.example.dndictionary.game;

public abstract class Game {
    protected int score;
    protected int health;
    public Game() {
        this.score = 0;
        this.health = 3;
    }
    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void increaseHighscore() {
        this.score++;
    }
    public void decreaseHealth() {
        this.health--;
    }
}

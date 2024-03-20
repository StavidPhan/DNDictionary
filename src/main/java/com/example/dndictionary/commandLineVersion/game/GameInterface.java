package com.example.dndictionary.commandLineVersion.game;

public class GameInterface extends GameManagement {
    protected static final int MAX = 33482;
    protected String path;
    protected String userAnswers;

    protected void insertFromFile() {
    }

    protected boolean isEndGame() {
        return point == 10 || health == 0;
    }

    protected void start() {
    }

    protected void printEndGame() {
        printInfo();
        if (point == 10)
            System.out.println("--------------YOU WIN !----------");
        else
            System.out.println("--------------YOU LOSE !---------");
    }

    protected void printInfo() {
        System.out.println("----------Your point:  " + this.point + " --------");
        System.out.println("----------Your health: " + this.health + " --------" + "\n\n");
    }
}
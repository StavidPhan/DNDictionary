package com.example.dndictionary.game;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static com.example.dndictionary.Utilities.PICTURES_PATH;

public class ChooseItem extends Game {
    private ArrayList<Item> items;
    private int numberOfItems;

    public ChooseItem() {
        items = new ArrayList<>();
        numberOfItems = 0;
    }

    public void addItem(String name, String imagePath) throws FileNotFoundException {
        this.items.add(new Item(name, imagePath));
        numberOfItems++;
    }

    public static ChooseItem getChooseItem() throws FileNotFoundException {
        ChooseItem chooseItem = new ChooseItem();

        chooseItem.addItem("compass", PICTURES_PATH + "compass.png");
        chooseItem.addItem("wheat", PICTURES_PATH + "wheat.png");
        chooseItem.addItem("apple", PICTURES_PATH + "apple.png");
        chooseItem.addItem("potato", PICTURES_PATH + "potato.png");
        chooseItem.addItem("book", PICTURES_PATH + "book.png");
        chooseItem.addItem("dog", PICTURES_PATH + "dog.png");
        chooseItem.addItem("pickaxe", PICTURES_PATH + "pickaxe.png");
        chooseItem.addItem("cat", PICTURES_PATH + "cat.png");
        chooseItem.addItem("chair", PICTURES_PATH + "chair.png");
        chooseItem.addItem("computer", PICTURES_PATH + "computer.png");
        chooseItem.addItem("hotpot", PICTURES_PATH + "hotpot.png");
        chooseItem.addItem("car", PICTURES_PATH + "car.png");
        chooseItem.addItem("lake", PICTURES_PATH + "lake.png");
        chooseItem.addItem("mouse", PICTURES_PATH + "mouse.png");
        chooseItem.addItem("banana", PICTURES_PATH + "banana.png");
        chooseItem.addItem("pig", PICTURES_PATH + "pig.png");
        chooseItem.addItem("fan", PICTURES_PATH + "fan.png");
        chooseItem.addItem("board", PICTURES_PATH + "board.png");
        chooseItem.addItem("glasses", PICTURES_PATH + "glasses.png");
        chooseItem.addItem("bottle", PICTURES_PATH + "bottle.png");

        return chooseItem;
    }

    public String returnRandomQuestion() {
        int randomIndex = (int) (Math.random() * numberOfItems);
        return items.get(randomIndex).getQuestion();
    }

    public Item returnRandomItem() {
        int randomIndex = (int) (Math.random() * numberOfItems);
        return items.get(randomIndex);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }
}

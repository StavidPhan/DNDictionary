package com.example.dndictionary.commandLineVersion.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.example.dndictionary.commandLineVersion.game.question.Question;

public class MultipleChoice extends GameInterface {
    private List<Question> questionList = new ArrayList<>();

    public MultipleChoice() {
        point = 0;
        health = 3;
        path = "src/main/resources/com/example/dndictionary/data/MultipleChoiceData.txt";
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void increasePoint() {
        point++;
    }

    public void decreaseHealth() {
        health--;
    }
    public void insertFromFile() {
        try {
            FileReader fr = new FileReader(path);
            BufferedReader bf = new BufferedReader(fr);
            String line;

            while ((line = bf.readLine()) != null) {
                String[] s = line.split("\\s+");

                String answers = s[0];

                String question = "";
                for (int i = 1; i < s.length; i++) {
                    String k = s[i].substring(0, s[i].length() - 1);
                    if (answers.equalsIgnoreCase(s[i]) || answers.equalsIgnoreCase(k)) {
                        question += "___" + " ";
                    } else {
                        question += s[i] + " ";
                    }
                }
                questionList.add(new Question(question, answers));
            }

            bf.close();
            fr.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("Cannot read file!");
        } catch (Exception e) {
            System.out.println("Error!");
        }
    }

    public List<String> randomAnswers(String answer) {
        List<String> list = new ArrayList<>();
        Random rand = new Random();
        list.add(answer);
        while (list.size() < 5) {
            int index = rand.nextInt(MAX);
            list.add(questionList.get(index).getCorrectAnswers());
        }

        //đảo đáp án.
        Collections.shuffle(list); 
        return list;
    }

    public void start() {
        insertFromFile();
        Scanner input = new Scanner(System.in);
        int stt = 1;
        System.out.println("-------------NEW GAME------------");

        while (true) {
            Question x = this.randomQuestion();
            x.setListAnswers(randomAnswers(x.getCorrectAnswers()));
            x.printQuestion(stt);
            stt++;

            System.out.print("Your Answers: ");
            char userAnswers = input.next().charAt(0);
            userAnswers = Character.toUpperCase(userAnswers);
            if (x.checkAnswers(userAnswers) == true) {
                System.out.println("\n\nCORRECT !");
                point += 1;
            } else {
                System.out.println("\n\nINCORRECT ! \t\t CORRECT ANSWERS: " + x.getCorrectAnswers());
                health -= 1;
            }
            printInfo();
            if (isEndGame() == true) {
                printEndGame();
                break;
            }
        }
    }

    public Question randomQuestion() {
        Random rand = new Random();
        int index = rand.nextInt(MAX);
        questionList.get(index).toString();

        return questionList.get(index);
    }

}
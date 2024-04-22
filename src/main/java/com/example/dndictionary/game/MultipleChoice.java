package com.example.dndictionary.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MultipleChoice extends Game {
    private ArrayList<MultipleChoiceQuestion> questions;
    private int numberOfQuestions;

    public MultipleChoice() {
        questions = new ArrayList<>();
    }

    public MultipleChoiceQuestion returnRandomQuestion() {
        int min = 0;
        int max = this.numberOfQuestions - 1;
        int randomInt = 0;

        while (true) {
            randomInt = (int) Math.floor(Math.random() * (max - min + 1) + min);
            if (!this.questions.get(randomInt).isChosen()) {
                this.questions.get(randomInt).setChosen(true);
                return this.questions.get(randomInt);
            }
        }
    }

    public ArrayList<MultipleChoiceQuestion> getQuestions() {
        return this.questions;
    }

    public int getNumberOfQuestions() {
        return this.numberOfQuestions;
    }

    public void addQuestion(MultipleChoiceQuestion q) {
        this.questions.add(q);
        numberOfQuestions = this.questions.size();
    }

    private static final String QUESTION_FILE = "src/main/resources/com/example/dndictionary/data/multiChoiceQuestion.txt";
    private static List<String> lists = new ArrayList<>();

    public static void loadQuestions() {
        try {
            FileReader reader = new FileReader(QUESTION_FILE);
            BufferedReader bf = new BufferedReader(reader);

            String line;
            while ((line = bf.readLine()) != null) {
                lists.add(line);
            }

            bf.close();
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("không tìm thấy file");
        } catch (IOException e) {
            System.out.println("Lỗi đọc file");
        } catch (Exception e) {
            System.out.println("Lỗi khác với file");
        }
    }

    public static MultipleChoiceQuestion getMultipleChoiceQuestion() {
        if (lists.isEmpty()) {
            loadQuestions();
        }

        String questionData = lists.remove(0);
        String[] parts = questionData.split("\\|");
        String question = parts[0];
        String[] options = new String[4];
        options[0] = parts[1];
        options[1] = parts[2];
        options[2] = parts[3];
        options[3] = parts[4];
        String answer = parts[5];

        return new MultipleChoiceQuestion(question, options[0], options[1], options[2], options[3], answer);
    }

    public static MultipleChoice getMultipleChoice() {
        if (lists.isEmpty()) {
            loadQuestions();
        }

        MultipleChoice multipleChoice = new MultipleChoice();

        for (int i = 0; i < lists.size(); i++) {
            String questionData = lists.remove(0);
            String[] parts = questionData.split("\\|");
            String question = parts[0];
            String[] options = new String[4];
            options[0] = parts[1];
            options[1] = parts[2];
            options[2] = parts[3];
            options[3] = parts[4];
            String answer = parts[5];
            multipleChoice.addQuestion(new MultipleChoiceQuestion(question, options[0], options[1], options[2], options[3], answer));
        }

        return multipleChoice;
    }


//    public static MultipleChoice getMultipleChoice() {
//        MultipleChoice multipleChoice = new MultipleChoice();
//
//        multipleChoice.addQuestion(new MultipleChoiceQuestion("Hello! My name ___ DNDictionary.", "is", "are", "was", "were", "A"));
//        multipleChoice.addQuestion(new MultipleChoiceQuestion("Here ___ the train.", "came", "come", "comes", "coming", "C"));
//        multipleChoice.addQuestion(new MultipleChoiceQuestion("We're ___ dinner. Do you wanna join?", "have", "having", "has", "had", "B"));
//        multipleChoice.addQuestion(new MultipleChoiceQuestion("At 8PM last night, I ___ the television.", "watched", "has watched", "had watched", "was watching", "D"));
//        multipleChoice.addQuestion(new MultipleChoiceQuestion("I ___ have a big meeting tomorrow.", "will", "is going to", "is", "shall", "A"));
//        multipleChoice.addQuestion(new MultipleChoiceQuestion("Do you like the app?", "Yes", "No", "I hate it", "DNDictionary sucks", "A"));
//        multipleChoice.addQuestion(new MultipleChoiceQuestion("What ___ you doing?", "is", "are", "am", "was", "B"));
//        multipleChoice.addQuestion(new MultipleChoiceQuestion("I ___ to the cinema last night.", "go", "goes","went", "going", "C"));
//        multipleChoice.addQuestion(new MultipleChoiceQuestion("How much do you rate this app?", "7.5", "2.5", "5", "10", "D"));
//
//        return multipleChoice;
//    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (MultipleChoiceQuestion q : questions) {
            result.append(q.toString()).append(q.isChosen()).append("\n");
        }
        return result.toString();
    }
}

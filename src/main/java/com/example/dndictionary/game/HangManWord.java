package com.example.dndictionary.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HangManWord {
    public String word;
    public boolean[] list;

    private static final int MAX_CHAR = 20;
    public List<Character> listChar = new ArrayList<>();
    public boolean[] list1 = new boolean[MAX_CHAR];
    private int charCorrect = 0;

    public HangManWord(String word) {
        this.word = word;
        list = new boolean[word.length()];
        for (int i = 0; i < word.length(); i++) {
            listChar.add(word.charAt(i));
        }
        randChar();
    }

    public void randChar() {
        int k = MAX_CHAR - word.length();
        Random rand = new Random();
        for (int i = 0; i < k; i++) {
            int n = rand.nextInt('z' - 'a');
            char t = (char) ('a' + n);
            listChar.add(t);
        }
        Collections.shuffle(listChar);
    }

    public String randGuessWord() {
        StringBuilder guessWord = new StringBuilder();
        int len = word.length();

        for (int i = 0; i < len; i++) {
            if (list[i]) {
                guessWord.append(word.charAt(i));
            } else {
                guessWord.append("_ ");
            }
        }

        return guessWord.toString();
    }
    public String printInfoGraphic() {
        StringBuilder info = new StringBuilder();
        info.append("                           Hint letters\n");

        for (int i = 0; i < listChar.size(); i++) {
            if (list1[i]) {
                info.append("* ");
            } else {
                info.append(listChar.get(i)).append(" ");
            }
        }

        return info.toString();
    }

    public boolean checkAnswers(char answers) {
        answers = Character.toLowerCase(answers);
        for (int i = 0; i < MAX_CHAR; i++) {
            if (answers == listChar.get(i) && !list1[i]) {
                list1[i] = true;
                boolean check = false;
                for (int j = 0; j < word.length(); j++) {
                    if (word.charAt(j) == answers && list[j] == false) {
                        list[j] = true;
                        charCorrect++;
                        check = true;
                        break;
                    }
                }
                if (check == true) {
                    return true;
                } else
                    return false;
            }
        }
        System.out.println("-------Chữ này không nằm trong gợi ý------");
        return false;
    }

    public boolean completedWord() {
        return charCorrect == word.length();
    }
}

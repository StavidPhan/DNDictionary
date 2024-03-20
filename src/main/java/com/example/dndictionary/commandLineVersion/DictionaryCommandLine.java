package com.example.dndictionary.commandLineVersion;

import java.util.Scanner;

import com.example.dndictionary.commandLineVersion.game.GameManagement;

public class DictionaryCommandLine {
    private DictionaryManagement dm = new DictionaryManagement();

    public DictionaryCommandLine() {
        System.out.println("Welcome to My Application");
    }

    public void menu() {
        System.out.println("[0]     Exit");
        System.out.println("[1]     Add");
        System.out.println("[2]     Remove");
        System.out.println("[3]     Update");
        System.out.println("[4]     Display");
        System.out.println("[5]     Lookup");
        System.out.println("[6]     Search");
        System.out.println("[7]     Game");
        System.out.println("[8]     Import from file");
        System.out.println("[9]     Export to file");
    }

    public void yourAction() {
        try (Scanner input = new Scanner(System.in)) {
            String yourChoice;
            do {
                menu();
                System.out.println("Your Action:");
                yourChoice = input.next();
                input.nextLine();
                switch (yourChoice) {
                    case "0":
                        break;
                    case "1": {
                        System.out.println("Nhap tu tieng Anh muon them: ");
                        String wordTarget = input.nextLine();
                        System.out.println("Nhap nghia tieng Viet: ");
                        String wordExplain = input.nextLine();
                        Word word = new Word(wordTarget, wordExplain);
                        dm.dictionaryAdd(word);
                        break;
                    }
                    case "2": {
                        System.out.println("Nhap tu tieng Anh can xoa: ");
                        String word_target = input.nextLine();
                        dm.dictionaryDelete(word_target);
                        break;
                    }
                    case "3": {
                        System.out.println("Nhap tu tieng Anh can chinh sua: ");
                        String wordTarget = input.nextLine();
                        System.out.println("Nhap nghia can chinh sua: ");
                        String wordExplain = input.nextLine();
                        Word word = new Word(wordTarget, wordExplain);
                        dm.dictionaryUpdate(word);
                        break;
                    }
                    case "4": {
                        this.showAllWords();
                        break;
                    }
                    case "5": {
                        System.out.println("Nhap tu tieng Anh can tim kiem");
                        String word_explain = input.nextLine();
                        dm.dictionaryLookUp(word_explain);
                        break;
                    }
                    case "6": {
                        System.out.println("Nhap tu tieng Viet can tim kiem");
                        String word_target = input.nextLine();
                        dm.dictionarySearch(word_target);
                        break;
                    }
                    case "7": {
                        GameManagement game = new GameManagement();
                        game.printMenu();
                        break;
                    }
                    case "8": {
                        dm.insertFromFile1("/com/example/demo/data/WordList.txt");
                        break;
                    }
                    case "9": {
                        dm.dictionaryExportToFile("/com/example/demo/data/file_export.txt");
                        break;
                    }
                    default:
                        System.out.println("Action not supported!");
                }

            } while (yourChoice.compareTo("0") != 0);
        }
    }
    public void showAllWords() {
        System.out.printf("%-10s | %-32s | %-32s\n", "STT", "English", "Vietnamese");
        int i = 1;
        for (Word word:dm.getDictionary().values()) {
            System.out.printf("%-10s | %-32s | %-32s\n",i, word.getWordTarget(), word.getWordExplain());
            i++;
        }
    }
}


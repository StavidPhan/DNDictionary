package com.example.dndictionary;

public class Word {
    private String word;
    private String pronunciation;
    private String description;
    private boolean isBookmarked;

    public Word(String word, String pronunciation, String description, boolean isBookmarked) {
        this.word = word;
        this.pronunciation = pronunciation;
        this.description = description;
        this.isBookmarked = isBookmarked;
    }

    public Word(String word, String pronunciation, String description, int isBookmarked) {
        this.word = word;
        this.pronunciation = pronunciation;
        this.description = description;
        if (isBookmarked == 1) {
            this.isBookmarked = true;
        } else {
            this.isBookmarked = false;
        }
    }

    public String getWord() {
        return word;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public String getDescription() {
        return description;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    @Override
    public String toString() {
        return this.getWord();
    }
}
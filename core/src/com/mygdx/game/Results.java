package com.mygdx.game;

public class Results {
    int notesHit;
    int totalNotes;
    int maxCombo;
    int score;
    boolean win;

    Results(int notesHit, int totalNotes, int maxCombo, int score, boolean win) {
        this.notesHit = notesHit;
        this.totalNotes = totalNotes;
        this.maxCombo = maxCombo;
        this.score = score;
        this.win = win;
    }
}
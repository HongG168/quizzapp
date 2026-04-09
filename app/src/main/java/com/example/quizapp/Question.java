package com.example.quizapp;

public class Question {
    private String question;
    private String[] options;
    private int correctIndex; // 0-based index

    public Question(String question, String[] options, int correctIndex) {
        this.question = question;
        this.options = options;
        this.correctIndex = correctIndex;
    }

    public String getQuestion() { return question; }
    public String[] getOptions() { return options; }
    public int getCorrectIndex() { return correctIndex; }
}

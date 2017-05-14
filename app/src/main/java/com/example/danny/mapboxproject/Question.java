package com.example.danny.mapboxproject;


import java.util.ArrayList;
import java.util.List;

public class Question {

    Integer id;
    Integer placeID;
    Integer isLocked;
    Integer isAnswered;
    String question;
    String answerA;
    String answerB;
    String answerC;
    String answerD;
    Integer correct;

    public Question(){
        super();
    }

    public Question(Integer id, Integer placeID, Integer isLocked, Integer isAnswered,
                    String question, String answerA, String answerB, String answerC,
                    String answerD, Integer correct) {
        this.id = id;
        this.placeID = placeID;
        this.isLocked = isLocked;
        this.isAnswered = isAnswered;
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.correct = correct;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlaceID() {
        return placeID;
    }

    public void setPlaceID(Integer placeID) {
        this.placeID = placeID;
    }

    public Integer getLocked() {
        return isLocked;
    }

    public void setLocked(Integer locked) {
        isLocked = locked;
    }

    public Integer getAnswered() {
        return isAnswered;
    }

    public void setAnswered(Integer answered) {
        isAnswered = answered;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    public List<Question> getQuestionList(){
        List<Question> list = new ArrayList<>();

        list.add(new Question(1, 1, 0, 1, "What is the name of..", "yes", "maybe", "no", "nah", 1));
        list.add(new Question(2, 3, 0, 0, "I have 3 red parts..", "yes", "maybe", "no", "nah", 2));
        list.add(new Question(3, 6, 0, 0, "If i have a mark on..", "yes", "maybe", "no", "nah", 2));
        list.add(new Question(4, 2, 1, 0, "Does a building with a..", "yes", "maybe", "no", "nah", 4));
        list.add(new Question(5, 5, 1, 0, "I do have 1 spot, but do i..", "yes", "maybe", "no", "nah", 3));

        return list;
    }
}
package com.example.danny.mapboxproject;


import java.util.ArrayList;
import java.util.List;

public class Question {

    String id;
    Boolean isLocked;
    Boolean isAnswered;
    String question;
    String answer;

    public Question(){
        super();
    }

    public Question(String id, Boolean isLocked, Boolean isAnswered, String question, String answer) {
        this.id = id;
        this.isLocked = isLocked;
        this.isAnswered = isAnswered;
        this.question = question;
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public Boolean getAnswered() {
        return isAnswered;
    }

    public void setAnswered(Boolean answered) {
        isAnswered = answered;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<Question> getQuestionList(){
        List<Question> list = new ArrayList<>();

        list.add(new Question("aa02", false, true, "Question 1", "yes"));
        list.add(new Question("ab03", false, false, "Question 2", "maybe"));
        list.add(new Question("ac01", false, false, "Question 3", "m8"));
        list.add(new Question("ad06", true, false, "Question 4", "no"));
        list.add(new Question("ae04", true, false, "Question 5", "no"));

        return list;
    }

}
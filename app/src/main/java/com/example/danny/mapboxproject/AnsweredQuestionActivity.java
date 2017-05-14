package com.example.danny.mapboxproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AnsweredQuestionActivity extends AppCompatActivity {
    Integer id, correct;
    private DatabaseManager dbm;
    Question quest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answered_question);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");

        dbm = new DatabaseManager(getBaseContext());
        dbm.getWritableDatabase();

        quest = dbm.getQuestion(id);

        correct = quest.getCorrect();

        final TextView questionTextView = (TextView) findViewById(R.id.questionTextView);
        final TextView answerTextView = (TextView) findViewById(R.id.answerTextView);

        questionTextView.setText(quest.getQuestion());

        if(correct==1){
            answerTextView.setText(quest.getAnswerA());
        }
        else if(correct==2){
            answerTextView.setText(quest.getAnswerB());
        }
        else if(correct==3){
            answerTextView.setText(quest.getAnswerC());
        }
        else if(correct==4){
            answerTextView.setText(quest.getAnswerD());
        }
    }
}
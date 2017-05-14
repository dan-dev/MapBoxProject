package com.example.danny.mapboxproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    Context context = this;
    Integer id, correct;
    private DatabaseManager dbm;
    Question quest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");

        dbm = new DatabaseManager(getBaseContext());
        dbm.getWritableDatabase();

        quest = dbm.getQuestion(id);

        correct = quest.getCorrect();

        final TextView questionTextView = (TextView) findViewById(R.id.questionTextView);
        final Button answerA = (Button) findViewById(R.id.AnswerA);
        final Button answerB = (Button) findViewById(R.id.AnswerB);
        final Button answerC = (Button) findViewById(R.id.AnswerC);
        final Button answerD = (Button) findViewById(R.id.AnswerD);

        /*
        for( Question q : questionList ){
            if(q.getId().equals(id)){
                questionTextView.setText(q.getQuestion());
                answer = q.getAnswerA();
            }
        }*/

        questionTextView.setText(quest.getQuestion());

        answerA.setText(quest.answerA);
        answerB.setText(quest.answerB);
        answerC.setText(quest.answerC);
        answerD.setText(quest.answerD);

        answerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(1);
            }
        });

        answerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(2);
            }
        });

        answerC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(3);
            }
        });

        answerD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(4);
            }
        });

        /*
        answerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer.toLowerCase().equals(answerEditText.getText().toString().toLowerCase())){
                    Toast.makeText(getApplicationContext(), "Correct! :)", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Wrong. :(", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }

    private void checkAnswer(int ans){
        if (ans == correct){
            Toast.makeText(getApplicationContext(), "Certo! :)", Toast.LENGTH_SHORT).show();
            dbm.setQuestionAnswered(quest.getId());
        }
        else {
            Toast.makeText(getApplicationContext(), "Errado. Tenta outra vez!  " + correct, Toast.LENGTH_SHORT).show();
        }
    }
}
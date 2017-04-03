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
    String id, answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");

        final Question question = new Question();

        final List<Question> questionList = question.getQuestionList();

        final Button mapBTN = (Button) findViewById(R.id.mapBTN);

        final TextView questionTextView = (TextView) findViewById(R.id.questionTextView);
        final EditText answerEditText = (EditText) findViewById(R.id.answerEdit);
        final Button answerBTN = (Button) findViewById(R.id.submitBTN);

        for( Question q : questionList ){
            if(q.getId().equals(id)){
                questionTextView.setText(q.getQuestion());
                answer = q.getAnswer();
            }
        }

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
        });

        mapBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
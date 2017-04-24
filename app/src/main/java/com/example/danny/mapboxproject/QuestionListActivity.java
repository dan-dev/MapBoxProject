package com.example.danny.mapboxproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuestionListActivity extends AppCompatActivity {

    ListView questionListView;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        final Button mapBTN = (Button) findViewById(R.id.mapBTN);

        final FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });


        mapBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

        mapBTN.setVisibility(View.INVISIBLE);

        questionListView = (ListView) findViewById(R.id.questionListView);

        final Question question = new Question();

        final List<Question> questionList = question.getQuestionList();
        final ArrayList<Question> list = new ArrayList<>();

        for (Question q : questionList){
            list.add(q);
        }

        ItemAdapter adapter = new ItemAdapter(context, list);

        /*for (Question q : questionList){
            String s = "";
            if(q.getAnswered()==true) {
                s += "(Answered) ";
            }
            else if(q.getLocked() == false) {
                s += "(Open) ";
            }
            else {
                s += "(Locked) ";
            }
            s += q.getQuestion();
            list.add(s);
        }*/

        //final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        //questionListView.setAdapter(arrayAdapter);

        questionListView.setAdapter(adapter);
        questionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(questionList.get(position).getAnswered()==false && questionList.get(position).getLocked() == false){
                    Intent intent = new Intent(getBaseContext(), QuestionActivity.class);
                    intent.putExtra("id", questionList.get(position).getId());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Select a question that is open and unanswered.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
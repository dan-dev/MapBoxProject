package com.example.danny.mapboxproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuestionListActivity extends Fragment {

    public QuestionListActivity(){}

    ListView questionListView;
    //Context context = this;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_question_list);
        view = inflater.inflate(R.layout.activity_question_list, container, false);

        final Button mapBTN = (Button) view.findViewById(R.id.mapBTN);

        final FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(context, MainActivity.class);
                //startActivity(intent);
                //QuestionListActivity.super.onBackPressed();
            }
        });

        floatingActionButton.setVisibility(View.GONE);


        mapBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        mapBTN.setVisibility(View.INVISIBLE);

        questionListView = (ListView) view.findViewById(R.id.questionListView);

        final Question question = new Question();

        final List<Question> questionList = question.getQuestionList();
        final ArrayList<Question> list = new ArrayList<>();

        for (Question q : questionList){
            list.add(q);
        }

        ItemAdapter adapter = new ItemAdapter(getContext(), list);

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
                if(questionList.get(position).getAnswered()==0 && questionList.get(position).getLocked() == 0){
                    Intent intent = new Intent(getActivity().getBaseContext(), QuestionActivity.class);
                    intent.putExtra("id", questionList.get(position).getId());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "Select a question that is open and unanswered.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
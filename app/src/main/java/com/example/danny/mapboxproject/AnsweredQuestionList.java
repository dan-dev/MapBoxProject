package com.example.danny.mapboxproject;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AnsweredQuestionList extends Fragment {

    public AnsweredQuestionList(){}

    ListView questionListView;

    private DatabaseManager dbm;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_answered_question_list, container, false);

        dbm = new DatabaseManager(getActivity());
        dbm.getWritableDatabase();

        questionListView = (ListView) view.findViewById(R.id.questionListView);

        final Question question = new Question();

        //final List<Question> questionList = question.getQuestionList();
        final List<Question> questionList = dbm.getAnsweredQuestions();
        final ArrayList<Question> list = new ArrayList<>();

        for (Question q : questionList){
            list.add(q);
        }

        ItemAdapter adapter = new ItemAdapter(getContext(), list);

        questionListView.setAdapter(adapter);
        questionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getBaseContext(), AnsweredQuestionActivity.class);
                intent.putExtra("id", questionList.get(position).getId());
                startActivity(intent);
            }
        });

        return view;

    }
}
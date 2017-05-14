package com.example.danny.mapboxproject;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PlaceList extends Fragment {
    public PlaceList(){}

    ListView questionListView;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_place_list, container, false);

        questionListView = (ListView) view.findViewById(R.id.placelistView);

        final Question question = new Question();

        final List<Question> questionList = question.getQuestionList();
        final ArrayList<String> list = new ArrayList<>();

        for (Question q : questionList){
            String s = "";
            if(q.getAnswered()==1) {
                s += "(Answered) ";
            }
            else if(q.getLocked() == 0) {
                s += "(Open) ";
            }
            else {
                s += "(Locked) ";
            }
            s += q.getQuestion();
            list.add(s);
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
        questionListView.setAdapter(arrayAdapter);

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

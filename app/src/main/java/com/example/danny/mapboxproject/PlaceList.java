package com.example.danny.mapboxproject;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PlaceList extends Fragment {
    public PlaceList(){}

    ListView questionListView;
    View view;
    JSONArray arrayPlaces;
    JSONArray arrayTypes;

    DatabaseManager dbm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_place_list, container, false);

        questionListView = (ListView) view.findViewById(R.id.placelistView);

        //final Question question = new Question();

        dbm = new DatabaseManager(getActivity());
        dbm.getWritableDatabase();

        //final List<Question> questionList = question.getQuestionList();
        final List<Question> questionList = dbm.getAnsweredQuestions();
        final ArrayList<String> list = new ArrayList<>();
        final ArrayList<String> places = new ArrayList<>();

        String json = "";

        try {
            InputStream inputStream = getActivity().getAssets().open("placeJson.json");
            int size = inputStream.available();

            byte[] buffer = new byte[size];

            inputStream.read(buffer);

            inputStream.close();

            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(json);
            arrayTypes = jsonObject.getJSONArray("type");
            arrayPlaces = jsonObject.getJSONArray("places");

            for(Question q : questionList) {
                for(int i = 0; i < arrayPlaces.length(); i++) {
                    if(q.getPlaceID() == arrayPlaces.getJSONObject(i).getInt("id")) {
                        places.add(arrayPlaces.getJSONObject(i).toString());
                        list.add(arrayPlaces.getJSONObject(i).getString("name"));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
        questionListView.setAdapter(arrayAdapter);

        questionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getBaseContext(), AnsweredPlaceDetails.class);
                intent.putExtra("json", places.get(position));
                startActivity(intent);
            }
        });

        return view;
    }
}

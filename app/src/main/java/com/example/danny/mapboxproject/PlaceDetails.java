package com.example.danny.mapboxproject;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PlaceDetails extends AppCompatActivity {
    int id;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");

        TextView textView = (TextView) findViewById(R.id.name);
        TextView textView2 = (TextView) findViewById(R.id.details);
        ImageView imageView = (ImageView) findViewById(R.id.image);

        final Place place = new Place();
        final List<Place> listMarks = place.getPlacesList();

        textView.setText(listMarks.get(id).getName());
        textView2.setText(listMarks.get(id).getDescrip());
        Resources resources = context.getResources();
        final int res = resources.getIdentifier(listMarks.get(id).getImage(), "drawable", context.getPackageName());
        imageView.setImageResource(res);
    }
}

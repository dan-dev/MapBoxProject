package com.example.danny.mapboxproject;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.HashMap;
import java.util.List;

public class PlaceDetails extends AppCompatActivity {
    int id;
    Context context = this;
    private SliderLayout sliderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");

        TextView textView = (TextView) findViewById(R.id.name);
        TextView textView2 = (TextView) findViewById(R.id.details);
        sliderLayout = (SliderLayout) findViewById(R.id.image_slider);

        HashMap<String, Integer> image_list = new HashMap<String, Integer>();
        image_list.put("Image 1", R.drawable.igrejamisericordia);
        image_list.put("Image 2", R.drawable.duqueribeira);
        image_list.put("Image 3", R.drawable.sedoporto);

        for(String name : image_list.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView.description(name).image(image_list.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterInside);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);

            sliderLayout.addSlider(textSliderView);
        }

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Fade);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(6000);

        final Place place = new Place();
        final List<Place> listMarks = place.getPlacesList();

        textView.setText(listMarks.get(id).getName());
        textView2.setText(listMarks.get(id).getDescrip());
        Resources resources = context.getResources();
        //final int res = resources.getIdentifier(listMarks.get(id).getImage(), "drawable", context.getPackageName());
        //imageView.setImageResource(res);
    }
}

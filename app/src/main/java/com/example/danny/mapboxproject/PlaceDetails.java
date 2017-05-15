package com.example.danny.mapboxproject;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlaceDetails extends AppCompatActivity {
    int id, close;
    String json;
    Context context = this;
    private SliderLayout sliderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        Bundle bundle = getIntent().getExtras();

        close = bundle.getInt("close");
        id = bundle.getInt("id");
        json = bundle.getString("json");

        TextView name = (TextView) findViewById(R.id.name);
        TextView details = (TextView) findViewById(R.id.details);
        TextView contact = (TextView) findViewById(R.id.contactText);
        TextView schedule = (TextView) findViewById(R.id.schedule);
        TextView seeMore = (TextView) findViewById(R.id.seeMore);
        sliderLayout = (SliderLayout) findViewById(R.id.image_slider);

        try {
            JSONObject jsonObject = new JSONObject(json);
            name.setText(jsonObject.getString("name"));
            if (close == 1){
                details.setText(jsonObject.getString("description"));
                seeMore.setVisibility(View.INVISIBLE);
            }
            else{
                details.setText(jsonObject.getString("resume"));
            }
            if(jsonObject.getString("contact").isEmpty()){ }
            else{
                contact.setText("Contacto: " + jsonObject.getString("contact"));
            }
            if(jsonObject.getString("schedule").isEmpty()){ }
            else{
                schedule.setText("Horário: " + jsonObject.getString("schedule"));
            }

            JSONArray arrayImages = jsonObject.getJSONArray("images");

            for (int i = 0; i < arrayImages.length(); i++){
                TextSliderView textSliderView = new TextSliderView(this);
                Resources resources = context.getResources();
                final int resourceId = resources.getIdentifier(arrayImages.getJSONObject(i).getString("ref"), "drawable",
                        context.getPackageName());
                textSliderView.image(resourceId).setScaleType(BaseSliderView.ScaleType.CenterInside);
                sliderLayout.addSlider(textSliderView);
                if(close != 1){
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Fade);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(6000);
    }
}
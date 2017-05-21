package com.example.danny.mapboxproject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnsweredPlaceDetails extends AppCompatActivity {

    int id;
    String json;
    Context context = this;
    private SliderLayout sliderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answered_place_details);
        Bundle bundle = getIntent().getExtras();

        id = bundle.getInt("id");
        json = bundle.getString("json");

        TextView textView = (TextView) findViewById(R.id.name);
        TextView textView2 = (TextView) findViewById(R.id.details);
        TextView contact = (TextView) findViewById(R.id.contactText);
        TextView schedule = (TextView) findViewById(R.id.schedule);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.shareFAB);

        sliderLayout = (SliderLayout) findViewById(R.id.image_slider);

        try {
            JSONObject jsonObject = new JSONObject(json);
            textView.setText(jsonObject.getString("name"));
            textView2.setText(jsonObject.getString("description"));
            if(jsonObject.getString("contact").isEmpty()){
                //contact.setVisibility(View.GONE);
            }
            else{
                contact.setText("Contacto: " + jsonObject.getString("contact"));
            }
            if(jsonObject.getString("schedule").isEmpty()){
                //contact.setVisibility(View.GONE);
            }
            else{
                schedule.setText("Horário: " + jsonObject.getString("schedule"));
            }

            JSONArray arrayImages = jsonObject.getJSONArray("images");

            final String name = jsonObject.getString("name");
            final String image = arrayImages.getJSONObject(0).getString("ref");

            for (int i = 0; i < arrayImages.length(); i++){
                TextSliderView textSliderView = new TextSliderView(this);
                Resources resources = context.getResources();
                final int resourceId = resources.getIdentifier(arrayImages.getJSONObject(i).getString("ref"), "drawable",
                        context.getPackageName());
                textSliderView.image(resourceId).setScaleType(BaseSliderView.ScaleType.CenterInside);
                sliderLayout.addSlider(textSliderView);
            }

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    share(name, image);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Fade);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(6000);
    }

    private void share(String name, String image){
        if(checkPermissions()){
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            Resources resources = context.getResources();
            final int resourceId = resources.getIdentifier(image, "drawable",
                    context.getPackageName());
            Bitmap icon = BitmapFactory.decodeResource(context.getResources(), resourceId);

            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/Pictures");

            String fname = "MyImage.jpg";
            File file = new File (myDir, fname);
            if (file.exists ()) file.delete ();
            if (!file.exists()){
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                file.mkdirs();
                FileOutputStream out = new FileOutputStream(file);
                icon.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            Uri uri = Uri.fromFile(file);

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Desbloqueei o ponto de interesse - " + name + "!");
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);

            shareIntent.setType("image/jpg");
            startActivity(shareIntent);
            //file.delete();
        }
        else {
            Toast.makeText(getApplicationContext(), "Necessita de permissoes para partilhar.", Toast.LENGTH_SHORT).show();
        }
    }

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    private boolean checkPermissions(){
        int storage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if(storage != PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            //Log.e("----permission----", "not granted");
        }

        if (!listPermissionsNeeded.isEmpty())
        {
            ActivityCompat.requestPermissions(this,listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }
}

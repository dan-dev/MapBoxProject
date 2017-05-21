package com.example.danny.mapboxproject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.constants.MyLocationTracking;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.services.android.telemetry.permissions.PermissionsListener;
import com.mapbox.services.android.telemetry.permissions.PermissionsManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class MapFragment extends Fragment implements PermissionsListener {

    private PermissionsManager permissionsManager;
    private MapView mapView;
    private MapboxMap map;
    Context context = getContext();
    JSONArray arrayPlaces;
    JSONArray arrayTypes;
    View view;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_map, container, false);



        Mapbox.getInstance(getActivity(), "pk.eyJ1IjoiZGFuLWRldiIsImEiOiJjajBwdHM1NTUwMGZ6Mndtd3BxaXpyb3FtIn0.z_ap6R88cPHTzu5RvMlvlQ");



        //setContentView(R.layout.activity_main);

        IconFactory iconFactory = IconFactory.getInstance(context);

        final Icon statue = iconFactory.fromResource(R.drawable.ic_estatua);
        final Icon museum = iconFactory.fromResource(R.drawable.ic_museu);
        final Icon church = iconFactory.fromResource(R.drawable.ic_igreja);

        FloatingActionButton centerFAB = (FloatingActionButton) view.findViewById(R.id.centerFAB);
        centerFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkGPS()){
                    enableGPS();
                }
                enableLocationTracking();
            }
        });

        String json = "";

        try {
            InputStream inputStream = context.getAssets().open("placeJson.json");
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
            Log.e("-------------array: ", ""+arrayPlaces.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                map = mapboxMap;

                permissionsManager = new PermissionsManager((PermissionsListener) getContext().getApplicationContext());
                if (!PermissionsManager.areLocationPermissionsGranted(getContext().getApplicationContext())) {
                    Log.e("-------------", "no permission");
                    permissionsManager.requestLocationPermissions((Activity) getContext().getApplicationContext());
                } else {
                    Log.e("-------------","permission");
                    //enableLocationTracking();
                }

                if(!checkGPS()){
                    enableGPS();
                }

                enableLocationTracking();

                for(int i = 0; i < arrayPlaces.length(); i++){
                    try {
                        Icon icon = null;
                        JSONObject jsonObject = arrayPlaces.getJSONObject(i);
                        int type = jsonObject.getInt("type");
                        if (type == 0){
                            icon = statue;
                        } else if (type == 1){
                            icon = museum;
                        } else if (type == 2){
                            icon = church;
                        }
                        LatLng latLng = new LatLng(jsonObject.getDouble("lat"), jsonObject.getDouble("lng"));
                        mapboxMap.addMarker(new MarkerOptions().position(new LatLng(latLng)).title(jsonObject.getString("name")).icon(icon)).setId(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                mapboxMap.setInfoWindowAdapter(new MapboxMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(@NonNull Marker marker) {
                        View v = getActivity().getLayoutInflater().inflate(R.layout.layout_info_window, null);
                        TextView textView = (TextView) v.findViewById(R.id.text);
                        TextView textView2 = (TextView) v.findViewById(R.id.textView);
                        ImageView imageView = (ImageView) v.findViewById(R.id.image);
                        Button button = (Button) v.findViewById(R.id.button_go);

                        String placeJson = "";

                        try {

                            final int id = (int) marker.getId();
                            textView.setText(arrayPlaces.getJSONObject(id).getString("name"));

                            placeJson = arrayPlaces.getJSONObject(id).toString();

                            Double distance = -1.0;
                            if(map.getMyLocation() == null){ }
                            else{
                                distance = marker.getPosition().distanceTo(new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude()));
                                if(distance > 1000){
                                    distance = distance/1000;
                                    textView2.setText("Distance: " + String.format("%.2f", distance) + "km");
                                }
                                else{
                                    textView2.setText("Distance: " + String.format("%.2f", distance) + "m");
                                }
                            }

                            Resources resources = context.getResources();
                            final int resourceId = resources.getIdentifier(arrayPlaces.getJSONObject(id).getJSONArray("images").getJSONObject(0).getString("ref"), "drawable",
                                    context.getPackageName());

                            imageView.setImageResource(resourceId);

                            final Double finalDistance = distance;
                            final String finalPlaceJson = placeJson;
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(context, PlaceDetails.class);
                                    if(finalDistance > 5 || finalDistance<0){
                                        intent.putExtra("close", 0);
                                    }
                                    else {
                                        intent.putExtra("close", 1);
                                    }
                                    intent.putExtra("id", id);
                                    intent.putExtra("json", finalPlaceJson);
                                    startActivity(intent);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return v;
                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private boolean checkGPS(){
        LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        boolean gps = false;

        try{
            gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e){}

        return gps;
    }

    private void enableGPS(){
        new AlertDialog.Builder(context).setTitle("Ligue GPS").setMessage("É necessario ligar o GPS")
                .setPositiveButton("Ligar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private void enableLocationTracking() {
        map.setMyLocationEnabled(true);

        // Disable tracking dismiss on map gesture
        map.getTrackingSettings().setDismissAllTrackingOnGesture(true);

        // Enable location and bearing tracking
        map.getTrackingSettings().setMyLocationTrackingMode(MyLocationTracking.TRACKING_FOLLOW);
        //map.getTrackingSettings().setMyLocationTrackingMode(MyLocationTracking.TRACKING_NONE);
        map.setMinZoomPreference(10);
        map.setMaxZoomPreference(17);
        //map.getTrackingSettings().setMyBearingTrackingMode(MyBearingTracking.COMPASS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(context, "This app needs location permissions in order to show its functionality.",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationTracking();
        } else {
            Toast.makeText(context, "You didn't grant location permissions.",
                    Toast.LENGTH_LONG).show();
            //finish();
        }
    }

}
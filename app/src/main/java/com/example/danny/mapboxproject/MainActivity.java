package com.example.danny.mapboxproject;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.constants.MyBearingTracking;
import com.mapbox.mapboxsdk.constants.MyLocationTracking;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.services.android.telemetry.permissions.PermissionsListener;
import com.mapbox.services.android.telemetry.permissions.PermissionsManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PermissionsListener {

    private PermissionsManager permissionsManager;
    private MapView mapView;
    private MapboxMap map;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoiZGFuLWRldiIsImEiOiJjajBwdHM1NTUwMGZ6Mndtd3BxaXpyb3FtIn0.z_ap6R88cPHTzu5RvMlvlQ");

        setContentView(R.layout.activity_main);

        final Button questionsBTN = (Button) findViewById(R.id.questionBtn);

        questionsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuestionListActivity.class);
                startActivity(intent);
            }
        });

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                map = mapboxMap;

                permissionsManager = new PermissionsManager(MainActivity.this);
                if (!PermissionsManager.areLocationPermissionsGranted(MainActivity.this)) {
                    permissionsManager.requestLocationPermissions(MainActivity.this);
                } else {
                    enableLocationTracking();
                }

                List<LatLng> coordsList = new ArrayList<LatLng>();
                final Place place = new Place();
                final List<Place> placeList = place.getPlacesList();

                int i = 0;
                for (Place p : placeList) {
                    LatLng latLng = p.getCoord();
                    mapboxMap.addMarker(new MarkerOptions().position(new LatLng(latLng)).title(p.getName())).setId(i);
                    i++;
                }

                mapboxMap.setInfoWindowAdapter(new MapboxMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(@NonNull Marker marker) {
                        //Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();
                        View v = getLayoutInflater().inflate(R.layout.layout_info_window, null);
                        TextView textView = (TextView) v.findViewById(R.id.text);
                        ImageView imageView = (ImageView) v.findViewById(R.id.image);
                        Button button = (Button) v.findViewById(R.id.button_go);
                        final int id = (int) marker.getId();
                        textView.setText(placeList.get(id).getName());
                        Resources resources = context.getResources();
                        final int res = resources.getIdentifier(placeList.get(id).getImage(), "drawable", context.getPackageName());
                        imageView.setImageResource(res);

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Toast.makeText(getApplicationContext(), "showing", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getBaseContext(), PlaceDetails.class);
                                intent.putExtra("id", id);
                                startActivity(intent);
                            }
                        });
                        return v;
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
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
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private void enableLocationTracking() {

        map.setMyLocationEnabled(true);

        // Disable tracking dismiss on map gesture
        map.getTrackingSettings().setDismissAllTrackingOnGesture(false);

        // Enable location and bearing tracking
        map.getTrackingSettings().setMyLocationTrackingMode(MyLocationTracking.TRACKING_FOLLOW);
        map.getTrackingSettings().setMyBearingTrackingMode(MyBearingTracking.COMPASS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, "This app needs location permissions in order to show its functionality.",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationTracking();
        } else {
            Toast.makeText(this, "You didn't grant location permissions.",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }
}

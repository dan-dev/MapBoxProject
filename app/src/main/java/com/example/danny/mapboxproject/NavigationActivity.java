package com.example.danny.mapboxproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseManager dbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        dbm = new DatabaseManager(getBaseContext());
        dbm.getWritableDatabase();

        if(dbm.isdbEmpty()){
            fillDatabase();
        }

        fab.setVisibility(View.GONE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MainPageActivity activity = new MainPageActivity();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout, activity).addToBackStack(null).commit();
    }

    private void fillDatabase(){
        String json = "";

        try {
            InputStream inputStream = getAssets().open("questionsJson.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];

            inputStream.read(buffer);
            inputStream.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0; i < jsonArray.length(); i++){
                Question question = new Question();
                question.setId(jsonArray.getJSONObject(i).getInt("id"));
                question.setPlaceID(jsonArray.getJSONObject(i).getInt("place"));
                question.setCorrect(jsonArray.getJSONObject(i).getInt("correct"));
                question.setQuestion(jsonArray.getJSONObject(i).getString("question"));
                question.setAnswerA(jsonArray.getJSONObject(i).getJSONArray("answers").getJSONObject(0).getString("answer"));
                question.setAnswerB(jsonArray.getJSONObject(i).getJSONArray("answers").getJSONObject(1).getString("answer"));
                question.setAnswerC(jsonArray.getJSONObject(i).getJSONArray("answers").getJSONObject(2).getString("answer"));
                question.setAnswerD(jsonArray.getJSONObject(i).getJSONArray("answers").getJSONObject(3).getString("answer"));
                question.setAnswered(0);
                question.setLocked(1);
                dbm.addNewQuestion(question);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dbm.unlockRandomQuestion();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.navigation, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            new Intent(NavigationActivity.this, MainActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(NavigationActivity.this, MainActivity.class));
        } else if (id == R.id.nav_gallery) {
            QuestionListActivity activity = new QuestionListActivity();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_layout, activity).addToBackStack(null).commit();

        } else if (id == R.id.nav_slideshow) {
            AnsweredQuestionList activity = new AnsweredQuestionList();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_layout, activity).addToBackStack(null).commit();
        } else if (id == R.id.nav_manage) {
            PlaceList activity = new PlaceList();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_layout, activity).addToBackStack(null).commit();

        } else if (id == R.id.nav_share) {
            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
            dbm.dropTble(dbm.getWritableDatabase());
            fillDatabase();
        } else if (id == R.id.nav_send) {
            AboutActivity activity = new AboutActivity();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_layout, activity).addToBackStack(null).commit();
        } else if(id == R.id.nav_out) {
            new AlertDialog.Builder(this).setTitle("Log out").setMessage("Deseja sair da conta?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
package apk.tamere.projet.pokemother.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import apk.tamere.projet.pokemother.R;
import apk.tamere.projet.pokemother.adaptateur.AlarmAdpatateur;
import apk.tamere.projet.pokemother.metier.Alarm;

public class AlarmActivity extends AppCompatActivity {

    private RecyclerView rAlarms;
    private AlarmAdpatateur adapter;
    private List<Alarm> alarms;

    private static final String SH_PREF = "PokeMom", SAVE_ALARMS = "shPr_alarms";

    private void init() {
        SharedPreferences prefs = getSharedPreferences(SH_PREF, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(SAVE_ALARMS, "");
        alarms = gson.fromJson(json, new TypeToken<List<Alarm>>(){}.getType());
        if(alarms == null) {
            alarms = new ArrayList<>();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        init();

        rAlarms = findViewById(R.id.rAlarms);
        adapter = new AlarmAdpatateur(alarms);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rAlarms.setLayoutManager(layoutManager);
        rAlarms.setAdapter(adapter);

        FloatingActionButton flButton = findViewById(R.id.floating_add_alarm);
        flButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdd = new Intent(getApplicationContext(), AlarmAddActivity.class);
                startActivityForResult(intentAdd, 0);
            }
        });
    }

    @Override
    protected void onDestroy() {
        SharedPreferences prefs = getSharedPreferences(SH_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        try {
            editor.putString(SAVE_ALARMS, gson.toJson(alarms));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        editor.apply();

        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Alarm alarm = (Alarm) data.getSerializableExtra(AlarmAddActivity.GET_NEW);

            if(alarm != null) {
                adapter.add(alarm);
            }
        }
    }
}

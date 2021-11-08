package com.example.finalandroidproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;

public class StationActivity extends AppCompatActivity {

    private TextView stationTextField;
    private ImageView icon;
    private TextView descriptionTextView;
    private ListView listView;

    private CustomAdapter2 customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        // a value is transferred through the Intent
        Intent intent = getIntent();
        // Read values from Intent
        Double[][] stationData = (Double[][]) intent.getSerializableExtra("STATION_DATA");
        Double[] activityLimits = (Double[]) intent.getSerializableExtra("ACTIVITY_LIMITS");
        Double current_limit = (Double) intent.getDoubleExtra("CURRENT_STATION_LIMIT", 0d);

        String station_name = (String) intent.getStringExtra("STATION_NAME");
        String station_id = (String) intent.getStringExtra("STATION_ID");
        Double current_value = (Double) intent.getDoubleExtra("CURRENT_VALUE", 0d);

        boolean highActivity = (current_value >= current_limit);

        // Set data into UI

        stationTextField = (TextView) findViewById(R.id.stationListViewTextView);
        stationTextField.setText(station_name+" ("+station_id+")");

        icon = (ImageView) findViewById(R.id.iconImageViewStation);
        icon.setImageResource(highActivity ? R.drawable.active_icon : R.drawable._01n);

        descriptionTextView = (TextView) findViewById(R.id.stationListViewDescriptionTextView);
        descriptionTextView.setText(highActivity ? getString(R.string.high_activity, current_value) : getString(R.string.small_activity, current_value));

        Collections.reverse(Arrays.asList(stationData));
        listView = (ListView) findViewById(R.id.listViewStation);
        customAdapter = new CustomAdapter2(getApplicationContext(), activityLimits, stationData, current_limit, station_name, station_id, current_value);
        listView.setAdapter(customAdapter);


    }

    public void backToMain(View view) {
        // Close this Activity and go back to Main
        finish();
    }

}

package com.example.finalandroidproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ListView stationListView;
    private CustomAdapter customAdapter;

    // Default values
    private Boolean loading = false;
    private Boolean loaded = false;

    String[] stations;// = {"OUJ", "MEK", "RAN", "PEL", "MUO", "KIL", "KEV", "IVA", "SOD", "HAN", "NUR"};//, "TAR"};
    int[] icons;// = {R.drawable._01d, R.drawable._01n, R.drawable._02d, R.drawable._02n, R.drawable._03d, R.drawable._03n, R.drawable._04d, R.drawable._04n, R.drawable._09d, R.drawable._09n, R.drawable._10d, R.drawable._10n};
    String[] stationNames;// = {"Oulujärvi", "Mekrijärvi", "Ranua", "Pello", "Muonio", "Kilpisjärvi", "Kevo", "Ivalo", "Sodankylä", "Hankasalmi", "Nurmijärvi"};//, "Tartto" };
    Double[] activityLimits;// = { 0.41d, 0.36d, 0.42, 0.5d, 0.51d, 0.58d, 0.58d, 0.52d, 0.5d, 0.35d, 0.3 };
    Double[] activityValues;// = { 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d };
    Double[][][] stationData;//  = new Double[11][1][2];

    Map<String, LastData> lastData = new Map<String, LastData>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsValue(@Nullable Object o) {
            return false;
        }

        @Nullable
        @Override
        public LastData get(@Nullable Object o) {
            return null;
        }

        @Nullable
        @Override
        public LastData put(String s, LastData lastData) {
            return null;
        }

        @Nullable
        @Override
        public LastData remove(@Nullable Object o) {
            return null;
        }

        @Override
        public void putAll(@NonNull Map<? extends String, ? extends LastData> map) {

        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public Set<String> keySet() {
            return null;
        }

        @NonNull
        @Override
        public Collection<LastData> values() {
            return null;
        }

        @NonNull
        @Override
        public Set<Entry<String, LastData>> entrySet() {
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stations = new String[] {"OUJ", "MEK", "RAN", "PEL", "MUO", "KIL", "KEV", "IVA", "SOD", "HAN", "NUR"};//, "TAR"};
        icons = new int[] {R.drawable._01d, R.drawable._01n, R.drawable._02d, R.drawable._02n, R.drawable._03d, R.drawable._03n, R.drawable._04d, R.drawable._04n, R.drawable._09d, R.drawable._09n, R.drawable._10d, R.drawable._10n};
        stationNames = new String[] {"Oulujärvi", "Mekrijärvi", "Ranua", "Pello", "Muonio", "Kilpisjärvi", "Kevo", "Ivalo", "Sodankylä", "Hankasalmi", "Nurmijärvi"};//, "Tartto" };
        activityLimits = new Double[] { 0.41d, 0.36d, 0.42, 0.5d, 0.51d, 0.58d, 0.58d, 0.52d, 0.5d, 0.35d, 0.3 };

        activityValues = new Double[] { 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d };
        stationData  = new Double[11][1][2];

        progressBar = (ProgressBar) findViewById(R.id.progressBarMain);
        setProgressBarState(loading);

        for (String stationId: stations) {
            lastData.put(stationId, new LastData());
        }

        stationListView = (ListView) findViewById(R.id.listViewMain);
        customAdapter = new CustomAdapter(getApplicationContext(), stations, icons, stationNames, activityLimits, activityValues, lastData);
        stationListView.setAdapter(customAdapter);
        stationListView.setOnItemClickListener((adapterView, view, i, l) -> {

            Intent intent = new Intent(MainActivity.this, StationActivity.class);
            intent.putExtra("STATION_DATA", stationData[i]);
            intent.putExtra("ACTIVITY_LIMITS", activityLimits);
            intent.putExtra("CURRENT_STATION_LIMIT", activityLimits[i]);
            intent.putExtra("STATION_NAME", stationNames[i]);
            intent.putExtra("STATION_ID", stations[i]);
            intent.putExtra("CURRENT_VALUE", activityValues[i]);

            //based on item add info to intent
            startActivity(intent);
        });

        // If the Bundle is not empty
        if (savedInstanceState != null) {
            // ...
            loading = savedInstanceState.getBoolean("LOADING");
            loaded = savedInstanceState.getBoolean("LOADED");


        } else {
            // ...
        }

        if (!loaded) {
            getData();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save ...
        String str = "";//view.getText().toString();
        outState.putString("KEY", str);

        outState.putBoolean("LOADING", loading);
        outState.putBoolean("LOADED", loaded);


        //Double[] activityValues;
        //Double[][][] stationData;
        //Map<String, LastData> lastData;
    }

    private void setProgressBarState(Boolean isLoading) {
        loading = isLoading;
        // Set the visibility of image loading progress bar
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    /*public void updateData(View view) {
        // Start fetching the data
        getData();
    }*/

    public void getData() {
        // Set progress bar active
        setProgressBarState(true);
        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        // Get the api key from keys.xml in resources
        //String API_KEY = getString(R.string.api_key);
        // The api url in String
        String url ="https://ssl-saario.xyz/repo/lastData.json";// + API_KEY;
        // Print the url in debug log
        Log.d("URL", url);
        // a StringRequest to get the data
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, this::parseJSONAndUpdateUI, error -> { errorGettingData(); });
        // Add the request to the RequestQueue
        queue.add(stringRequest);
    }

    private void errorGettingData() {
        // If there is an error getting the data

        // Get localized "Error" string
        String errorStr = getString(R.string.error);
        // Replace _ with "Error"

        // ...

        // Hide progress bar
        setProgressBarState(false);
    }

    private void parseJSONAndUpdateUI(String response) {
        try {
            // Print into debug log
            Log.d("Try tag", "try");

            Log.d("Before lastData assign", "parseJSONAndUpdateUI try");
            lastData = Converter.fromJsonString(response);
            Log.d("After lastData assign", "parseJSONAndUpdateUI try");

            int i = 0;
            for (String id: stations) {
                Log.d("ID", id);
                if (lastData.get(id) != null) {
                    Log.d("Before lastData assign", "CustomAdapter getView()");
                    LastData activityData = null;
                    String activityDataStr = String.valueOf(lastData.get(id)).replace("=", ":");
                    activityDataStr = "{\"dataSeries\"" + activityDataStr.substring(11);
                    Log.d("activityDataStr", activityDataStr);
                    try {
                        activityData = LastDataConverter.fromJsonString(activityDataStr);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("After lastData assign", "CustomAdapter getView()");
                    System.out.println(activityData);

                    Double[][] dataSeries = (activityData.getDataSeries() != null) ? activityData.getDataSeries() : new Double[][] {{0d, 0d}};
                    Log.d("dataSeries", String.valueOf(dataSeries));

                    int index = dataSeries.length-1;
                    while (dataSeries[index][1] == null) {
                        if (index == 0) { break; }
                        index--;
                    }
                    activityValues[i] = (dataSeries[index][1] != null) ? dataSeries[index][1] : 0d;
                    stationData[i] = dataSeries;
                } i++;
            }

            customAdapter = new CustomAdapter(getApplicationContext(), stations, icons, stationNames, activityLimits, activityValues, lastData);
            stationListView.setAdapter(customAdapter);

            // Hide progress bar
            setProgressBarState(false);

        } catch (IOException e){
            // If an error happens while parsing JSON

            // Print into debug log
            Log.d("Catch tag", "catch");
            e.printStackTrace();
            // Hide progress bar
            setProgressBarState(false);
        }
    }

    // Old
    /*public void openStation(View view) {
        // The Station view
        Intent openStationIntent = new Intent(this, StationActivity.class);
        // ...
        double value = 1d;
        // Put a value into the Intent
        openStationIntent.putExtra("VALUE", value);
        // Open the Station view
        startActivity(openStationIntent);
    }*/

}

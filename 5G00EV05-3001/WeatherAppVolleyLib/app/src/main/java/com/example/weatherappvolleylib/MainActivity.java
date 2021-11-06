package com.example.weatherappvolleylib;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;

// Items from Volley library
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

// Items from JSON library
import org.json.JSONObject;
import org.json.JSONException;


public class MainActivity extends AppCompatActivity {

    // Text views
    private TextView cityTextView;
    private TextView descriptionTextView;
    private TextView temperatureTextView;
    private TextView windSpeedTextView;
    // Image view
    private ImageView weatherImageView;
    // Progress bar
    private ProgressBar imageProgressBar;

    // Default values
    private String cityStr = "Kangasala";
    private String iconStr = "";
    private Boolean loading = false;

    // Round float to decimals
    private float roundFloat(float f, int decimals) {
        float deciF = 1f;
        while (decimals-- > 0) { deciF *= 10f; }
        return Math.round(f * deciF) / deciF;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find text views and assign them to the variables
        cityTextView = findViewById(R.id.cityTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        temperatureTextView = findViewById(R.id.temperatureTextView);
        windSpeedTextView = findViewById(R.id.windSpeedTextView);

        // Find image and progress bar and assign them to the variables
        weatherImageView = (ImageView) findViewById(R.id.weatherImageView);
        imageProgressBar = (ProgressBar) findViewById(R.id.imageProgressBar);

        // If the Bundle is not empty
        if (savedInstanceState != null) {
            // Get city string value from the State
            cityStr = savedInstanceState.getString("CITY", "-");
            cityTextView.setText(cityStr);
            // Get description string value from the State
            String descriptionString = savedInstanceState.getString("DESCRIPTION", "-");
            descriptionTextView.setText(descriptionString);
            // Get temperature string value from the State
            String temperatureString = savedInstanceState.getString("TEMPERATURE", "-");
            temperatureTextView.setText(temperatureString);
            // Get wind speed string value from the State
            String windSpeedString = savedInstanceState.getString("WIND_SPEED", "-");
            windSpeedTextView.setText(windSpeedString);
            // Get icon name string value from the State
            String icon = savedInstanceState.getString("ICON", "-");
            setWeatherIcon(icon);
            // Get image loading boolean value from the State
            Boolean imgLoading = savedInstanceState.getBoolean("IMAGE_LOADING", false);
            setProgressBarState(imgLoading);
        } else {
            // Set default values on the first time of launching the app
            setProgressBarState(loading);
            cityTextView.setText(cityStr);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save city string state into the Bundle
        String cityString = cityTextView.getText().toString();
        outState.putString("CITY", cityString);
        // Save description string state into the Bundle
        String descriptionString = descriptionTextView.getText().toString();
        outState.putString("DESCRIPTION", descriptionString);
        // Save temperature string state into the Bundle
        String temperatureString = temperatureTextView.getText().toString();
        outState.putString("TEMPERATURE", temperatureString);
        // Save wind speed string state into the Bundle
        String windSpeedString = windSpeedTextView.getText().toString();
        outState.putString("WIND_SPEED", windSpeedString);
        // Save image name string state into the Bundle
        outState.putString("ICON", iconStr);
        // Save image loading boolean state into the Bundle
        outState.putBoolean("IMAGE_LOADING", loading);
    }

    private void setProgressBarState(Boolean isLoading) {
        loading = isLoading;
        // Set the visibility of image loading progress bar
        if (isLoading) {
            imageProgressBar.setVisibility(View.VISIBLE);
        } else {
            imageProgressBar.setVisibility(View.GONE);
        }
    }

    public void updateWeatherData(View view) {
        // Start fetching the weather data
        getWeatherData();
    }

    public void getWeatherData() {
        // Set progress bar active
        setProgressBarState(true);
        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        // Get the api key from keys.xml in resources
        String API_KEY = getString(R.string.open_weather_map_api_key);
        // The api url in String
        String url ="https://api.openweathermap.org/data/2.5/weather?q=" + cityStr + ",fi&units=metric&appid=" + API_KEY;
        // Print the url in debug log
        //Log.d("URL", url);
        // a StringRequest to get the weather data
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, this::parseJSONAndUpdateUI, error -> { errorGettingData(); });
        // Add the request to the RequestQueue
        queue.add(stringRequest);
    }

    private void errorGettingData() {
        // If there is an error getting the data

        //cityTextView = findViewById(R.id.cityTextView);
        // Get localized "Error" string
        String errorStr = getString(R.string.error);
        // Replace city with "Error"
        cityTextView.setText(errorStr);
        // Hide progress bar
        setProgressBarState(false);
    }

    private void parseJSONAndUpdateUI(String response) {
        try {
            // Print into debug log
            //Log.d("Try tag", "try");
            // Response into JSON object
            JSONObject rootObject = new JSONObject(response);
            // Get temperature from JSON
            float temperature = (float) rootObject.getJSONObject("main").getDouble("temp");
            // Get wind speed from JSON
            float windSpeed = (float) rootObject.getJSONObject("wind").getDouble("speed");
            // Get description from JSON
            String description = rootObject.getJSONArray("weather").getJSONObject(0).getString("main");
            // Get icon name from JSON
            String icon = rootObject.getJSONArray("weather").getJSONObject(0).getString("icon");

            //descriptionTextView = findViewById(R.id.descriptionTextView);
            // Set description into UI
            descriptionTextView.setText(description);

            //temperatureTextView = findViewById(R.id.temperatureTextView);
            // Get localized temperature string (replaces float placeholder with the temperature value)
            String tempStr = getString(R.string.temperature_text, temperature);
            // Set temperature into UI
            temperatureTextView.setText(tempStr);

            //windSpeedTextView = findViewById(R.id.windSpeedTextView);
            // Get localized wind speed string (replaces float placeholder with the wind speed value)
            String windSpeedStr = getString(R.string.wind_speed_text, windSpeed);
            // Set wind speed into UI
            windSpeedTextView.setText(windSpeedStr);

            // Set weather icon into UI
            setWeatherIcon(icon);
            // Hide progress bar
            setProgressBarState(false);

        } catch (JSONException e){
            // If an error happens while parsing JSON

            // Print into debug log
            Log.d("Catch tag", "catch");
            e.printStackTrace();
            // Hide progress bar
            setProgressBarState(false);
        }
    }

    private void setWeatherIcon(String icon) {
        iconStr = icon;
        //weatherImageView = (ImageView) findViewById(R.id.weatherImageView);
        // Set the right weather icon into UI
        switch (icon) {
            case "01d": weatherImageView.setImageResource(R.drawable._01d);
            case "01n": weatherImageView.setImageResource(R.drawable._01n);
            case "02d": weatherImageView.setImageResource(R.drawable._02d);
            case "02n": weatherImageView.setImageResource(R.drawable._02n);
            case "03d": weatherImageView.setImageResource(R.drawable._03d);
            case "03n": weatherImageView.setImageResource(R.drawable._03n);
            case "04d": weatherImageView.setImageResource(R.drawable._04d);
            case "04n": weatherImageView.setImageResource(R.drawable._04n);
            case "09d": weatherImageView.setImageResource(R.drawable._09d);
            case "09n": weatherImageView.setImageResource(R.drawable._09n);
            case "10d": weatherImageView.setImageResource(R.drawable._10d);
            case "10n": weatherImageView.setImageResource(R.drawable._10n);
            case "11d": weatherImageView.setImageResource(R.drawable._11d);
            case "11n": weatherImageView.setImageResource(R.drawable._11n);
            case "13d": weatherImageView.setImageResource(R.drawable._13d);
            case "13n": weatherImageView.setImageResource(R.drawable._13n);
            case "50d": weatherImageView.setImageResource(R.drawable._50d);
            case "50n": weatherImageView.setImageResource(R.drawable._50n);
        }
    }

    public void settings(View view) {
        // a Builder that asks a city from the user
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the localized title string
        String titleStr = getString(R.string.city);
        // Set the title
        builder.setTitle(titleStr);

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
        // Add the input into the Builder
        builder.setView(input);

        // Get the localized OK button title
        String okStr = getString(R.string.ok);
        // Set the title and onClick handler into OK button and into the Builder
        builder.setPositiveButton(okStr, (dialog, which) -> {
            //cityTextView = findViewById(R.id.cityTextView);
            // Update the users input into the cityStr
            cityStr = input.getText().toString();
            // Update city into UI
            cityTextView.setText(cityStr);
            // Get the new weather data from server
            getWeatherData();
        });

        // Get the localized cancel button title
        String cancelStr = getString(R.string.cancel);
        // Set the title and onClick handler into cancel button and into the Builder
        builder.setNegativeButton(cancelStr, (dialog, which) -> dialog.cancel());

        // Show the Builder in UI
        builder.show();
    }

}

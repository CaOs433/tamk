package com.example.bmicalculatornavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class BMIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiactivity);

        // The BMI value is transferred through the Intent
        Intent intent = getIntent();
        // Read the BMI from Intent
        double BMI = intent.getDoubleExtra("bmi_value",0);

        // Search the result text view by id
        TextView textView = findViewById(R.id.resultTextViewBMI);
        // Set the BMI value into result text view
        textView.setText("BMI: "+BMI);

    }

    public void backToMain(View view) {
        // Close this Activity and go back to Main
        finish();
    }

}
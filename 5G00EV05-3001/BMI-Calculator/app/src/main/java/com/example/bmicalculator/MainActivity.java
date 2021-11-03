package com.example.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonClicked(View view) {
        // Text views:
        EditText lengthTextNumber = findViewById(R.id.LengthTextNumber);
        EditText weightTextNumber = findViewById(R.id.WeightTextNumber);

        // String values from text views:
        String lengthString = lengthTextNumber.getText().toString();
        String weightString = weightTextNumber.getText().toString();

        // Double values from strings:
        double length = Double.parseDouble(lengthString);
        double weight = Double.parseDouble(weightString);
        double BMI = weight / (length / 100 * length / 100);

        // The result text view
        TextView resultTextview = findViewById(R.id.resultTextView);
        // Showing the result in the result text view
        resultTextview.setText("BMI " +Math.round(BMI*100.0) / 100.0);

    }
}

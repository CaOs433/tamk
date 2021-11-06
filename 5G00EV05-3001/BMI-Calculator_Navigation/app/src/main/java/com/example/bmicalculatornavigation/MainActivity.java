package com.example.bmicalculatornavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public double getBMI() {
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

        // Return the BMI value in double
        return BMI;
    }

    public void buttonClicked(View view) {
        // Get BMI
        double BMI = getBMI();
        // The result text view
        TextView resultTextview = findViewById(R.id.resultTextView);
        // Showing the result in the result text view
        resultTextview.setText("BMI " +Math.round(BMI*100.0) / 100.0);

    }

    public void openBMI(View view) {
        // The BMI Table view
        Intent openBMIIntent = new Intent(this, BMIActivity.class);
        // Get BMI
        double BMI = getBMI();
        // Put the BMI value into the Intent
        openBMIIntent.putExtra("bmi_value", Math.round(BMI*100.0) / 100.0);
        // Open the BMI Table view
        startActivity(openBMIIntent);
    }

}
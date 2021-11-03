package com.example.colourfulbuttons;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Change default background color for button 1
        Button btn1 = findViewById(R.id.button);
        btn1.setBackgroundColor(getResources().getColor(R.color.default_bg));
        // Change default background color for button 2
        Button btn2 = findViewById(R.id.button2);
        btn2.setBackgroundColor(getResources().getColor(R.color.default_bg));
        // Change default background color for button 3
        Button btn3 = findViewById(R.id.button3);
        btn3.setBackgroundColor(getResources().getColor(R.color.default_bg));
        // Change default background color for button 4
        Button btn4 = findViewById(R.id.button4);
        btn4.setBackgroundColor(getResources().getColor(R.color.default_bg));
    }

    public void onClicked(View view) {
        // Find the button by its id
        Button button = findViewById(view.getId());

        if (button.getBackground() instanceof ColorDrawable) {
            // Get the background color and cast it to ColorDrawable
            ColorDrawable buttonColor = (ColorDrawable) button.getBackground();

            // Get the color id from ColorDrawable
            int colorId = buttonColor.getColor();

            // Change the button color
            if (colorId == R.color.green) {
                button.setBackgroundColor(getResources().getColor(R.color.yellow));
            } else if (colorId == R.color.yellow) {
                button.setBackgroundColor(getResources().getColor(R.color.red));
            } else {
                button.setBackgroundColor(getResources().getColor(R.color.green));
            }
        } else {
            button.setBackgroundColor(getResources().getColor(R.color.green));
        }

    }

}
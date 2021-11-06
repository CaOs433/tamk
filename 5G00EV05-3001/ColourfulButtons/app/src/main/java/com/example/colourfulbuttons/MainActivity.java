package com.example.colourfulbuttons;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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
        //btn1.setTag(1);
        // Change default background color for button 2
        Button btn2 = findViewById(R.id.button2);
        btn2.setBackgroundColor(getResources().getColor(R.color.default_bg));
        //btn2.setTag(1);
        // Change default background color for button 3
        Button btn3 = findViewById(R.id.button3);
        btn3.setBackgroundColor(getResources().getColor(R.color.default_bg));
        //btn3.setTag(1);
        // Change default background color for button 4
        Button btn4 = findViewById(R.id.button4);
        btn4.setBackgroundColor(getResources().getColor(R.color.default_bg));
        //btn4.setTag(1);
    }

    public void onClicked(View view) {
        // Find the button by its id
        Button button = findViewById(view.getId());

        // Change the button color
        button.setBackgroundColor(getResources().getColor(R.color.yellow));

        // Not working:
        /*String btnTag = (String) button.getTag();
        Log.d("Button tag", btnTag);

        if (btnTag == "1") {
            button.setBackgroundColor(getResources().getColor(R.color.green));
            //button.setTag(0, "2");
        } else if (btnTag == "2") {
            button.setBackgroundColor(getResources().getColor(R.color.yellow));
            //button.setTag(0, "3");
        } else {
            button.setBackgroundColor(getResources().getColor(R.color.red));
            //button.setTag(0, "1");
        }*/

        /*if (button.getBackground() instanceof ColorDrawable) {
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
        }*/

    }

}
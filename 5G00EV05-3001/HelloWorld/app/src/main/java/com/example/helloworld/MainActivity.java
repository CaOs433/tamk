package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View View) {
        TextView helloTxtView = findViewById(R.id.hello_text);

        if (helloTxtView.getText().toString().equals(getString(R.string.hello_txt))) {
            helloTxtView.setText(R.string.hello_world_txt);
        } else {
            helloTxtView.setText(R.string.hello_txt);
        }
    }
}

package com.example.activitystateintobundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // The text view to show the state change
    private TextView contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the content text view by id
        contentTextView = findViewById(R.id.contentTextView);
        // If the Bundle is not empty
        if (savedInstanceState != null) {
            // Get the log data of state changes
            String logString = savedInstanceState.getString("LOG_DATA", "No data");
            // Show the log data in content text view
            contentTextView.append(logString);
        }
        // Add this newest onCreate() call to content text view
        contentTextView.append("On Create\n\n");
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Add this newest onStart() call to content text view
        contentTextView.append("On Start\n\n");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Add this newest onResume() call to content text view
        contentTextView.append("On Resume\n\n");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Add this newest onPause() call to content text view
        contentTextView.append("On Pause\n\n");
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Add this newest onStop() call to content text view
        contentTextView.append("On Stop\n\n");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // Add this newest onRestart() call to content text view
        contentTextView.append("On Restart\n\n");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Add this newest onDestroy() call to content text view
        contentTextView.append("On Destroy\n\n");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Add this newest onSaveInstanceState() call to content text view
        contentTextView.append("On Save Instance State\n\n");
        // Get the log data of state changes from the content text view
        String logString = contentTextView.getText().toString();
        // Save the log data into the Bundle
        outState.putString("LOG_DATA", logString);
    }

    public void addText(View view) {
        // Add "More text" into content text view
        contentTextView.append("More text\n\n");
    }

    public void clearText(View view) {
        // Clear the content text view
        contentTextView.setText("");
    }

}

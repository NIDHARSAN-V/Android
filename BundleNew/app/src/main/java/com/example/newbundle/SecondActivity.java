package com.example.newbundle;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView textView = findViewById(R.id.textView);

        // Retrieving the Bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String message = bundle.getString("message");
            int number = bundle.getInt("number");

            // Displaying the received data
            textView.setText("Message: " + message + "\nNumber: " + number);
        }
    }
}

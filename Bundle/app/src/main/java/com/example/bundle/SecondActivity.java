package com.example.bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView txtString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        txtString = findViewById(R.id.txtString);

        // getting the bundle from the intent
        Bundle bundle = getIntent().getExtras();

        // setting the text in the textview
        txtString.setText(bundle.getString("key1", "No value from the MainActivity"));
    }
}

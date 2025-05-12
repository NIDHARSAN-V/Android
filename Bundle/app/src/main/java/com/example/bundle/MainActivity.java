package com.example.bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bundle.R;
import com.example.bundle.SecondActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnPassBundles, btnNoPassBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPassBundles = findViewById(R.id.btnPassBundles);
        btnNoPassBundle = findViewById(R.id.btnNoPassBundle);

        // one button will pass the bundle and other button
        // will not pass the bundle
        btnPassBundles.setOnClickListener(this);
        btnNoPassBundle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnPassBundles) {
            Bundle bundle = new Bundle();
            bundle.putString("key1", "Passing Bundle From Main Activity to 2nd Activity");
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.btnNoPassBundle) {
            Bundle bundle = new Bundle();
            bundle.putString("key1", "Not passing Bundle From Main Activity");
            bundle.clear();
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

}

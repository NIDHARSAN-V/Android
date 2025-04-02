package com.example.newbundle;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.example.newbundle.SecondActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creating an Intent to start SecondActivity
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                // Creating a Bundle
                Bundle bundle = new Bundle();
                bundle.putString("message", "Hello from MainActivity!");
                bundle.putInt("number", 123);

                // Attaching the Bundle to the Intent
                intent.putExtras(bundle);

                // Starting SecondActivity
                startActivity(intent);
            }
        });
    }
}

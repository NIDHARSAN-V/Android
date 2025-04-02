package com.example.calling;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    private EditText phone_number;
    private Button call_button;

    private static final int CALL_PHONE_PERMISSION_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        call_from_phone();

    }


    private void call_from_phone()
    {
         phone_number = findViewById(R.id.edit_phone);
         call_button = findViewById(R.id.call_button);

        call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phonenumber = phone_number.getText().toString();

                if(!phonenumber.isEmpty())
                {
                    if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED)
                    {
                        Intent phoneintent = new Intent(Intent.ACTION_CALL);

                        phoneintent.setData(Uri.parse("tel:" + phonenumber));
                        startActivity(phoneintent);

                    }
                    else {
                        requestCallPermission();
                    }

                }
            }
        });


    }



    private void requestCallPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CALL_PHONE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}





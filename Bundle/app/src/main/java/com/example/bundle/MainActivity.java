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
        switch (view.getId()) {
            case R.id.btnPassBundles:
                // creating a bundle instance
                Bundle bundle = new Bundle();
                // passing the data into the bundle
                bundle.putString(
                        "key1",
                        "Passing Bundle From Main Activity to 2nd Activity");
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                // passing the bundle to the intent
                intent.putExtras(bundle);
                // starting the activity by passing the intent
                // to it.
                startActivity(intent);
                break;

            case R.id.btnNoPassBundle:
                bundle = new Bundle();
                bundle.putString(
                        "key1",
                        "Not passing Bundle From Main Activity");
                // clearing the data stored into the bundle
                bundle.clear();
                // passing the intent to the second activity
                intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }
}

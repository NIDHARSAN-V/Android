//package com.example.urlopen;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//public class MainActivity extends AppCompatActivity {
//
//    EditText editText;
//    Button button;
//    TextView textView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        urlsearch();
//
//
//    }
//
//
//    private void urlsearch()
//    {
//         editText  = findViewById(R.id.urltxt);
//         button = findViewById(R.id.gotoURL);
//         textView = findViewById(R.id.textview);
//
//         setTitle("N_Browser");
//
//         button.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 String url = editText.getText().toString();
//
//                 Intent urlintent = new Intent(Intent.ACTION_VIEW , Uri.parse(textView.getText().toString() + url));
//                 startActivity(urlintent);
//             }
//         });
//
//
//    }
//}



package com.example.urlopen;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    TextView textView;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.urltxt);
        button = findViewById(R.id.gotoURL);
        textView = findViewById(R.id.textview);
        webView = findViewById(R.id.webview);

        // Enable JavaScript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());  // Open links inside WebView

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editText.getText().toString();

                // Ensure a valid URL
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://" + url;  // Add HTTPS if missing
                }

                webView.setVisibility(View.VISIBLE);
                webView.loadUrl(url);  // Load URL inside WebView
            }
        });
    }
}

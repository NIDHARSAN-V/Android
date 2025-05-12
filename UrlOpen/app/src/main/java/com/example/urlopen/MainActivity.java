


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

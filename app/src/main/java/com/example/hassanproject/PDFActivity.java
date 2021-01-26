package com.example.hassanproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import static android.view.View.VISIBLE;

public class PDFActivity extends AppCompatActivity {

    WebView webView;
    String url;
    ProgressBar progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        String url1 = "https://drive.google.com/open?id=1OOzREITv1ESSW5pBrGVU0gQnCkOjNnOr";
        String url2 = "https://drive.google.com/open?id=1jAZlVgrptAkQEMlulCtwa-6-jltRIbzR";

        String intentKey = getIntent().getStringExtra("intent");
        if (intentKey != null && intentKey.equals("help")) {
            getSupportActionBar().setTitle("Help");
            url = url1;
        } else if (intentKey != null && intentKey.equals("sensor")) {
            getSupportActionBar().setTitle("Sensors Information");
            url = url2;
        }

        webView = findViewById(R.id.wvloadPdf);
        progressBar = findViewById(R.id.web_view_progressBar);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setVisibility(VISIBLE);
        webView.loadUrl(url);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setVisibility(VISIBLE);
                if (newProgress == 100) {
                    //cShowProgress.hideProgress();
                    progressBar.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

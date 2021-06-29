package com.example.fiotebarber01betatest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

public class OtherInformations extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_other_informations);

        WebView webView = (WebView)findViewById(R.id.viewmap);

        String xURL = "https://www.google.com/maps/place/R.+Nova+Veneza,+522+-+Jardim+Maragojipe,+" +
                "Itaquaquecetuba+-+SP,+08580-340/@-23.4523509,-46.34332,17z/data=!3m1!4b1!4m5!3m4!" +
                "1s0x94ce7ce6e753b3a9:0xc27f8d46b3adb053!8m2!3d-23.4523509!4d-46.3411313";

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(xURL);
    }
}
package com.example.androidsocialnetwork;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

public class SplashVideo extends Activity {
    private VideoView startVideoApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        startVideoApp = (VideoView) findViewById(R.id.v5prc);

        startVideoApp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intent = new Intent(SplashVideo.this,MainActivity.class);
                startActivity(intent);
            }
        });

        String vPath = "android.resource://com.example.androidsocialnetwork/" + R.raw.android_splashscreen;
        Uri uriPath = Uri.parse(vPath);
        startVideoApp.setVideoURI(uriPath);
        startVideoApp.requestFocus();
        startVideoApp.start();

    }

}
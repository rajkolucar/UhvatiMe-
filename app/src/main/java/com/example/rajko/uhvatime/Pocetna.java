package com.example.rajko.uhvatime;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import java.util.Timer;
import java.util.TimerTask;

public class Pocetna extends AppCompatActivity {

    private static final String TWITTER_KEY = "VJXIr7B0ppyM8hqTnJkt1MVoQ";
    private static final String TWITTER_SECRET = "hFDXjnM5RlQsTs93SCHaJG1fnCg6HmUQ5br17qcrMfgO7iybzy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_pocetna);
    }

    public void pocniIgru(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void podesavanja(View view) {
        startActivity(new Intent(getApplicationContext(), Podesavanja.class));
    }
}

package com.example.rajko.uhvatime;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

/**
 * Created by Rajko on 11-Feb-17.
 */

public class Podesavanja extends AppCompatActivity {

    ToggleButton tg;
    public static boolean play = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podesavanja);
        tg = (ToggleButton) findViewById(R.id.zvuk);
        if (play) {
            tg.setChecked(true);
        } else {
            tg.setChecked(false);
        }
    }

    public void kontrola(View view) {
        play = ((ToggleButton) view).isChecked();
    }
}

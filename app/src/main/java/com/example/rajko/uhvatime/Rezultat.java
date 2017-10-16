package com.example.rajko.uhvatime;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.util.ArrayList;

public class Rezultat extends AppCompatActivity {

    Button rezultati;
    int rezultat;
    ListView lv;
    Button tweet;
    ArrayAdapter<String> adapter;
    String ime = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultat);
        final DBAdapter db = new DBAdapter(this);
        lv = (ListView) findViewById(R.id.listView);
        rezultati = (Button) findViewById(R.id.rezultati);
        rezultat = getIntent().getIntExtra("Rezultat", 0);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rezultat: " + rezultat);
        final EditText imeET = new EditText(this);
        builder.setView(imeET);
        builder.setPositiveButton("U redu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ime = imeET.getText().toString();
                db.openDB();
                long rez = db.add(ime, rezultat);
            }
        });

        builder.setNegativeButton("Izadji", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        rezultat = getIntent().getIntExtra("Rezultat", 0);
        rezultati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Rezultat.this, Rezultati.class);
                startActivity(intent);

            }
        });

        tweet = (Button) findViewById(R.id.tweet);
        tweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvituj();
            }
        });
    }

    public void tvituj() {
        TweetComposer.Builder builder = new TweetComposer.Builder(this).text("Moj novi rezultat u igri UhvatiMe! je: " + rezultat + "!");
        builder.show();
    }

    public void ponovo(View view) {
        startActivity(new Intent(getApplicationContext(), Pocetna.class));
    }

    public void rezultati(View view) {
        startActivity(new Intent(getApplicationContext(), Rezultati.class));
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}

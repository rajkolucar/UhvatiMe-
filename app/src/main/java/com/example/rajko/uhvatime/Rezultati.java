package com.example.rajko.uhvatime;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by Rajko on 06-Feb-17.
 */

public class Rezultati extends AppCompatActivity {

    DBAdapter db;
    ListView listView;
    ArrayList<String> lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rezultati);
        listView = (ListView) findViewById(R.id.listView);
        db = new DBAdapter(this);
        lista = new ArrayList<>();
        Cursor podaci = db.prikaziRezultate();
        if (podaci.getCount() == 0) {
            Toast.makeText(Rezultati.this, "Nema podataka u bazi!!", Toast.LENGTH_SHORT).show();
        } else {
            while (podaci.moveToNext()) {
                lista.add(podaci.getString(1));
                lista.add(podaci.getString(2));
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
                listView.setAdapter(listAdapter);
            }
        }
    }
}

package com.example.rajko.uhvatime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView rezultatLabela;
    private ImageView maliZeleni, crna, crvena, zuta, plava, zelena;

    //Velicine
    private int sirinaOkvira;
    private int maliZeleniVelicina;
    private int sirinaEkrana;
    private int visinaEkrana;

    //Pozicije
    private int maliZeleniX;
    private int crvenaX, crvenaY;
    private int crnaX, crnaY;
    private int plavaX, plavaY;
    private int zutaX, zutaY;
    private int zelenaX, zelenaY;

    //rezultat
    private int rezultat = 0;

    //Inicijalizujem klase za pomeranje loptica
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    //zvuk
    public Zvuk zvuk;

    //Provera statusa
    private boolean akcija = false;
    private boolean start = false;

    //pomeranje malog zelenog
    Button levo, desno;

    boolean pustiZvuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pustiZvuk = Podesavanja.play;
        zvuk = new Zvuk(this);
        rezultatLabela = (TextView) findViewById(R.id.rezultatLabela);

        maliZeleni = (ImageView) findViewById(R.id.malizeleni);
        crna = (ImageView) findViewById(R.id.crna);
        crvena = (ImageView) findViewById(R.id.crvena);
        plava = (ImageView) findViewById(R.id.plava);
        zuta = (ImageView) findViewById(R.id.zuta);
        zelena = (ImageView) findViewById(R.id.zelena);

        //Velicina ekrana
        Display display = getWindowManager().getDefaultDisplay();
        Point velicina = new Point();
        display.getSize(velicina);

        sirinaEkrana = velicina.x;
        visinaEkrana = velicina.y;

        levo = (Button) findViewById(R.id.levo);
        desno = (Button) findViewById(R.id.desno);
        levo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pomeriLevo();
            }
        });
        desno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pomeriDesno();
            }
        });
    }

    public void pomeriLevo() {
        akcija = true;
        if (!start) {
            start = true;
            FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
            sirinaOkvira = frame.getWidth();
            maliZeleniX = (int) maliZeleni.getX();
            maliZeleniVelicina = maliZeleni.getWidth();
            crna.setVisibility(View.VISIBLE);
            crvena.setVisibility(View.VISIBLE);
            plava.setVisibility(View.VISIBLE);
            zuta.setVisibility(View.VISIBLE);
            zelena.setVisibility(View.VISIBLE);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pomeri();
                        }
                    });
                }
            }, 0, 30);
        }
    }

    public void pomeriDesno() {
        akcija = false;
        if (!start) {
            start = true;
            FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
            sirinaOkvira = frame.getWidth();
            maliZeleniX = (int) maliZeleni.getX();
            maliZeleniVelicina = maliZeleni.getWidth();
            crna.setVisibility(View.VISIBLE);
            crvena.setVisibility(View.VISIBLE);
            plava.setVisibility(View.VISIBLE);
            zuta.setVisibility(View.VISIBLE);
            zelena.setVisibility(View.VISIBLE);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pomeri();
                        }
                    });
                }
            }, 0, 30);
        }
    }

    public void proveriPogodak() {

        //crvena
        int crvenaCentarX = crvenaX + crvena.getWidth() / 2;
        int crvenaCentarY = crvenaY + crvena.getHeight() / 2;
        if (maliZeleni.getY() < crvenaCentarY && crvenaCentarY < (maliZeleni.getY() + maliZeleni.getHeight()) &&
                maliZeleni.getX() < crvenaCentarX && crvenaCentarX < (maliZeleni.getX() + maliZeleni.getWidth())) {
            rezultat += 10;
            crvenaY = -20;
            crvenaX = (int) Math.floor(Math.random() * sirinaOkvira - crvena.getWidth());
            if(pustiZvuk) {
                zvuk.pogodio();
            }
        }

        //zuta
        int zutaCentarX = zutaX + zuta.getWidth() / 2;
        int zutaCentarY = zutaY + zuta.getHeight() / 2;
        if (maliZeleni.getY() < zutaCentarY && zutaCentarY < (maliZeleni.getY() + maliZeleni.getHeight()) &&
                maliZeleni.getX() < zutaCentarX && zutaCentarX < (maliZeleni.getX() + maliZeleni.getWidth())) {
            rezultat += 10;
            zutaY = -15;
            zutaX = (int) (Math.floor(Math.random() * sirinaOkvira -  zuta.getWidth()));
            if(pustiZvuk) {
                zvuk.pogodio();
            }
        }

        // /plava
        int plavaCentarX = plavaX + plava.getWidth() / 2;
        int plavaCentarY = plavaY + plava.getHeight() / 2;
        if (maliZeleni.getY() < plavaCentarY && plavaCentarY < (maliZeleni.getY() + maliZeleni.getHeight()) &&
                maliZeleni.getX() < plavaCentarX && plavaCentarX < (maliZeleni.getX() + maliZeleni.getWidth())) {
            rezultat += 10;
            plavaY = -1000;
            plavaX = (int) (Math.floor(Math.random() * sirinaOkvira - plava.getWidth()));
            if(pustiZvuk) {
                zvuk.pogodio();
            }
        }

        //zelena
        int zelenaCentarX = zelenaX + zelena.getWidth() / 2;
        int zelenaCentarY = zelenaY + zelena.getHeight() / 2;
        if (maliZeleni.getY() < zelenaCentarY && zelenaCentarY < (maliZeleni.getY() + maliZeleni.getHeight()) &&
                maliZeleni.getX() < zelenaCentarX && zelenaCentarX < (maliZeleni.getX() + maliZeleni.getWidth())) {
            rezultat += 30;
            zelenaY = -250;
            zelenaX = (int) (Math.floor(Math.random() * sirinaOkvira - zelena.getWidth()));
            if(pustiZvuk) {
                zvuk.pogodio();
            }
        }

        //crna
        int crnaCentarX = crnaX + crna.getWidth() / 2;
        int crnaCentarY = crnaY + crna.getHeight() / 2;
        if (maliZeleni.getY() < crnaCentarY && crnaCentarY < (maliZeleni.getY() + maliZeleni.getHeight()) &&
                maliZeleni.getX() < crnaCentarX && crnaCentarX < (maliZeleni.getX() + maliZeleni.getWidth())) {
            timer.cancel();
            timer = null;
            if(pustiZvuk) {
                zvuk.kraj();
            }
            //Prikazi rezultat
            Intent intent = new Intent(getApplicationContext(), Rezultat.class);
            intent.putExtra("Rezultat", rezultat);
            startActivity(intent);
        }
    }

    public void pomeri() {

        proveriPogodak();

        //Crvena
        crvenaY += (int) sirinaOkvira / 100;
        if(crvenaY > visinaEkrana) {
            crvenaY = -20;
            crvenaX = (int) Math.floor(Math.random() * sirinaOkvira - crvena.getWidth());
        }
        crvena.setX(crvenaX);
        crvena.setY(crvenaY);

        //Zuta
        zutaY += 9;
        if(zutaY > visinaEkrana) {
            zutaY = -15;
            zutaX = (int) (Math.floor(Math.random() * sirinaOkvira -  zuta.getWidth()));
        }
        zuta.setY(zutaY);
        zuta.setX(zutaX);

        //Plava
        plavaY += 13;
        if(plavaY > visinaEkrana) {
            plavaY = -1000;
            plavaX = (int) (Math.floor(Math.random() * sirinaOkvira - plava.getWidth()));
        }
        plava.setY(plavaY);
        plava.setX(plavaX);

        //Zelena
        zelenaY += 16;
        if(zelenaY > visinaEkrana) {
            zelenaY = -250;
            zelenaX = (int) (Math.floor(Math.random() * sirinaOkvira - zelena.getWidth()));
        }
        zelena.setY(zelenaY);
        zelena.setX(zelenaX);

        //Crna
        crnaY += 5;
        if(crnaY > visinaEkrana) {
            crnaY = -30;
            crnaX = (int) (Math.floor(Math.random() * sirinaOkvira - crna.getWidth()));
        }
        crna.setY(crnaY);
        crna.setX(crnaX);

        if(akcija) {
            maliZeleniX -=20;
        } else {
            maliZeleniX += 20;
        }
        //Provera pozicije malog zelenog
        if(maliZeleniX < 0) maliZeleniX = 0;

        if (maliZeleniX > sirinaOkvira - maliZeleniVelicina) maliZeleniX = sirinaOkvira - maliZeleniVelicina;
        maliZeleni.setX(maliZeleniX);

        rezultatLabela.setText("Rezultat: " + rezultat);
    }
}

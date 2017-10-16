package com.example.rajko.uhvatime;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/**
 * Created by Rajko on 1/28/2017.
 */

public class Zvuk {

    private AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX = 2;

    private static SoundPool soundPool;
    private static int pogodio;
    private static int kraj;

    public Zvuk(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();


            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(SOUND_POOL_MAX)
                    .build();
        } else {
            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);

        }


        pogodio = soundPool.load(context, R.raw.hit, 1);
        kraj = soundPool.load(context, R.raw.over, 1);
    }

    public void pogodio() {
        soundPool.play(pogodio, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void  kraj() {
        soundPool.play(kraj, 1.0f, 1.0f, 1, 0, 1.0f);
    }
}

package edu.scu.apadha.arttherapy;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;


public class BackgroundMusic extends IntentService {

    public BackgroundMusic() {
        super("BackgroundMusic");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        MediaPlayer player = MediaPlayer.create(BackgroundMusic.this, R.raw.eraser);
        player.start();
        while(player.isPlaying()) {
            Log.d("MUSIC", "playing ");
        }
        Log.d("MUSIC", "Finished playing!");
    }
}

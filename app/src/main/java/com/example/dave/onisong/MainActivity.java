package com.example.dave.onisong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.dave.onisong.song.SongParser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        new SongLoader().loadSongs(this);
    }

    public void error(final String errorMsg) {
        runOnUiThread(new Runnable() {
            public void run() {
            Toast.makeText(MainActivity.this,errorMsg,Toast.LENGTH_LONG).show();
            new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        MainActivity.this.finish();
                        System.exit(0);
                    }
                },
                2000);
            }
        });

    }

    public void success() {
        SongParser.parseSongFile(this);
        showSongList();
    }

    private void showSongList(){
        runOnUiThread(new Runnable() {
            public void run() {
                Intent i = new Intent(MainActivity.this,SongListActivity.class);
                startActivity(i);
                overridePendingTransition(0, 0);
            }
        });
    }

    public void newSongList(){
        SongApp songApp = (SongApp)this.getApplicationContext();
        songApp.newSongLoaded();
    }


}

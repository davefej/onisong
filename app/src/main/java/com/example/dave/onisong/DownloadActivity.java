package com.example.dave.onisong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.dave.onisong.download.Download;

public class DownloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        new Download().downloadSongs(this);
    }

    public void error() {
        runOnUiThread(new Runnable() {
            public void run() {
                //Toast.makeText(DownloadActivity.this,"Sikertelen letöltés",Toast.LENGTH_LONG).show();
                changeView();
            }
        });

    }

    public void success() {
        runOnUiThread(new Runnable() {
            public void run() {
                //Toast.makeText(DownloadActivity.this,"Sikeres letöltés",Toast.LENGTH_LONG).show();
                changeView();
            }
        });

    }

    private void changeView(){
        Intent i = new Intent(this,SongListActivity.class);
        startActivity(i);
    }


}

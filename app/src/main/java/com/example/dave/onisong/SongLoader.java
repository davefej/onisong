package com.example.dave.onisong;

import android.content.Context;
import android.util.Log;

import com.example.dave.onisong.song.SongParser;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by dave on 2018. 01. 16..
 */

public class SongLoader {

    MainActivity mainActivity;
    static final String DOWNLOADURL = "http://davidanddavid.hu/oni/enekek.txt";
    public void loadSongs(final MainActivity mainActivity){
        this.mainActivity = mainActivity;
        try{
            if(!SongParser.hasSongFile(mainActivity)){
                Log.i("SONGLOADER","NO PREVIOUS SONG");
                SongParser.moveDefaultSongToStorage(mainActivity);
            }else{
                Log.i("SONGLOADER","HAS PREVIOUS SONG");
            }
            mainActivity.success();
        }catch(Exception e){
            e.printStackTrace();
            Log.e("SINGLOADER INIT ERROR",e.toString());
            mainActivity.error("Valami balul sült el :(");
        }

        new Thread() {
            public void run() {
                try {
                    String oldSong = SongParser.getSongFile(mainActivity);
                    downloadFile(DOWNLOADURL);
                    String newSong = SongParser.getSongFile(mainActivity);
                    if(!oldSong.equals(newSong)) {
                        Log.i("SONGLOADER","NEW SONG LOADED");
                        //NEW SONGLIST IS DIFFERENT SO UPDATE
                        mainActivity.newSongList();
                    }else{
                        Log.i("SONGLOADER","SONG LOADED BUT SAME AS OLD");
                    }
                }catch (Exception e) {
                    Log.e("SONGLOADER","DOWNLOAD FAILED: "+e.toString());
                }
            }
        }.start();
    }

    private void downloadFile(final String path) throws Exception {
        URL url = new URL(path);
        URLConnection ucon = url.openConnection();
        ucon.setConnectTimeout(10000);
        InputStream is = ucon.getInputStream();
        BufferedInputStream inStream = new BufferedInputStream(is, 1024 * 5);
        FileOutputStream outStream = mainActivity.openFileOutput("enekek.txt",Context.MODE_PRIVATE);
        byte[] buff = new byte[5 * 1024];
        int len;
        while ((len = inStream.read(buff)) != -1)
        {
            outStream.write(buff, 0, len);
        }
        outStream.flush();
        outStream.close();
        inStream.close();
    }
}

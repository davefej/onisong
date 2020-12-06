package com.example.dave.onisong;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;

public class SongApp extends Application {
    boolean isNewSongLoaded = false;
    SongListActivity songListActivity;
    public void onCreate() {
        super.onCreate();
    }

    public void newSongLoaded() {
        if(songListActivity != null && songListActivity.isActive){
            songListActivity.newSongList();
        }else{
            isNewSongLoaded = true;
        }
    }

    public void newSongLoadHandled(){
        isNewSongLoaded = false;
    }

    public boolean isNewSongLoaded(){
        return this.isNewSongLoaded;
    }

    public void setSongListActivity(SongListActivity songListActivity) {
        this.songListActivity = songListActivity;
    }
}

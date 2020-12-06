package com.example.dave.onisong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dave.onisong.song.SongHeader;
import com.example.dave.onisong.song.TableOfContents;


public class SongActivity extends AppCompatActivity {
    SongHeader songh = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Bundle bundle = getIntent().getExtras();
        int num = bundle.getInt("number");
        String filter = bundle.getString("filter");
        if(filter.isEmpty()){
            songh = TableOfContents.getInstance().get(num);
        }else{
            songh = TableOfContents.getInstance().filtered(filter).get(num);
        }
        showSong();


        ScrollView view = (ScrollView) findViewById(R.id.scrollview);
        view.setOnTouchListener(new OnSwipeTouchListener(SongActivity.this) {
            @Override
            public void onSwipeLeft() {
                if(TableOfContents.getInstance().size() > songh.getNumber()){
                    songh = TableOfContents.getInstance().get(songh.getNumber());
                    showSong();
                }else{
                    Toast.makeText(SongActivity.this,"Ez az utolsó ének",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSwipeRight() {
                if(1 < songh.getNumber()){
                    songh = TableOfContents.getInstance().get(songh.getNumber()-2);
                    showSong();
                }else{
                    Toast.makeText(SongActivity.this,"Ez az első ének",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void showSong(){
        TextView textView2 = (TextView) findViewById(R.id.songtxt);
        getSupportActionBar().setTitle(songh.toString());
        textView2.setText(songh.getSong().toString());
    }

}

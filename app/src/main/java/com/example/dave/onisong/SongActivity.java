package com.example.dave.onisong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dave.onisong.song.TableOfContents;


public class SongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        Bundle bundle = getIntent().getExtras();
        int num = bundle.getInt("number");
        String filter = bundle.getString("filter");
        TextView textView2 = (TextView) findViewById(R.id.songtxt);

        if(filter.isEmpty()){
            getSupportActionBar().setTitle(TableOfContents.getInstance().get(num).toString());
            textView2.setText(TableOfContents.getInstance().get(num).getSong().toString());
        }else{
            getSupportActionBar().setTitle(TableOfContents.getInstance().filtered(filter).get(num).toString());
            textView2.setText(TableOfContents.getInstance().filtered(filter).get(num).getSong().toString());
        }

    }

}

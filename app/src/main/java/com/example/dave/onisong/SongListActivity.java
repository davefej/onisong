package com.example.dave.onisong;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dave.onisong.song.Song;
import com.example.dave.onisong.song.SongParser;
import com.example.dave.onisong.song.TableOfContents;


import java.io.IOException;

public class SongListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SongAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public boolean isActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SongApp app = (SongApp)this.getApplicationContext();
        app.setSongListActivity(this);
        if(TableOfContents.getInstance().size() == 0){
            SongParser.parseSongFile(this);
        }

        setContentView(R.layout.activity_song_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new SongAdapter(this,mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        final TextView search = (TextView) findViewById(R.id.searchsongtext);
        search.setVisibility(View.INVISIBLE);
        search.animate().translationX(500);

        final FloatingActionButton searchButton = (FloatingActionButton) findViewById(R.id.searchsongbutton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(search.getVisibility() == View.INVISIBLE){
                    search.setVisibility(View.VISIBLE);
                    search.animate().translationX(0);
                    search.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(search, InputMethodManager.SHOW_IMPLICIT);
                }else{
                    search.setText("");
                    search.animate().translationX(500);
                    search.setVisibility(View.INVISIBLE);
                    mAdapter.setSearchResult("");
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
                }
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                mAdapter.setSearchResult(search.getText().toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    int easterEggNum = 10;
    Toast lastToast;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {

            case R.id.action_settings:
                easterEggNum--;
                if(easterEggNum == 0){
                    if(lastToast != null && lastToast.getView().getWindowVisibility() == View.VISIBLE){
                        lastToast.cancel();
                    }
                    Toast.makeText(SongListActivity.this, "\"Mert ahol a kincsed van, ott lesz a szíved is\" Mt 6,21", Toast.LENGTH_LONG).show();
                    easterEggNum = 10;
                    return false;
                }
                if(easterEggNum < 5){

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(lastToast != null && lastToast.getView().getWindowVisibility() == View.VISIBLE){
                                lastToast.cancel();
                            }
                            lastToast = Toast.makeText(SongListActivity.this, "Még "+easterEggNum+" kattintás", Toast.LENGTH_SHORT);
                            lastToast.show();
                        }
                    });
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        isActive = true;
        SongApp app = (SongApp)this.getApplicationContext();
        if(app.isNewSongLoaded()){
            this.newSongList();
            app.newSongLoadHandled();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isActive = false;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void newSongList(){
        runOnUiThread(new Runnable() {
            public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(SongListActivity.this);
            builder.setMessage("Az énekeskönyv frissült, kívánja betölteni az új énekeket?")
                    .setCancelable(false)
                    .setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            TableOfContents.getInstance().reset();
                            SongParser.parseSongFile(SongListActivity.this);
                            mAdapter.notifyDataSetChanged();

                            Toast.makeText(SongListActivity.this,"Sikeres frissítés",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Nem", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            }
        });

    }
}

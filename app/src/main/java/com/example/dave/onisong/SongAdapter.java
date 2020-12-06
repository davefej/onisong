package com.example.dave.onisong;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dave.onisong.song.SongHeader;
import com.example.dave.onisong.song.TableOfContents;

import java.util.List;

/**
 * Created by dave on 2016. 11. 24..
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private SongListActivity sla;
    private RecyclerView rw;
    private String filter = "";
        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView songtitle,songnumber;
            public ViewHolder(View v) {
                super(v);
                songtitle = (TextView) v.findViewById(R.id.songtitle);
                songnumber = (TextView) v.findViewById(R.id.songnumber);
            }
        }

    public SongAdapter(SongListActivity sla,RecyclerView rw)
    {
        this.sla = sla;
        this.rw = rw;
    }


    // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent,
        int viewType) {


            View v =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_view, parent, false);
            // set the view's size, margins, paddings and layout parameters

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = rw.getChildLayoutPosition(v);
                    Bundle bundle = new Bundle();
                    bundle.putInt("number", itemPosition);
                    bundle.putString("filter", filter);
                    Intent myIntent = new Intent(sla, SongActivity.class);
                    myIntent.putExtras(bundle);
                    sla.startActivity(myIntent);
                }
            });

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            if(filter.isEmpty()) {
                SongHeader sh = TableOfContents.getInstance().get(position);
                holder.songnumber.setText(sh.getNumber() + "");
                holder.songtitle.setText(sh.getTitle());
            }else{
                SongHeader sh = TableOfContents.getInstance().filtered(filter).get(position);
                holder.songnumber.setText(sh.getNumber() + "");
                holder.songtitle.setText(sh.getTitle());
            }

            if (position == TableOfContents.getInstance().size() -1){
                ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                
            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            if(filter.isEmpty()){
                return TableOfContents.getInstance().size();
            }else{
                return TableOfContents.getInstance().filtered(filter).size();
            }

        }

        public void setSearchResult(String filter) {
            this.filter = filter.trim();
            notifyDataSetChanged();
        }
    }

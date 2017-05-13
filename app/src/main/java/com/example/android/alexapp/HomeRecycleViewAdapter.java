package com.example.android.alexapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by pc on 26/03/2017.
 */

public class HomeRecycleViewAdapter extends RecyclerView.Adapter<HomeRecycleViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<Place> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;
        public RelativeLayout vRoot;

        public MyViewHolder(View view) {
            super(view);
            vRoot = (RelativeLayout) view.findViewById(R.id.rl_root);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }

    public HomeRecycleViewAdapter(Context mContext, List<Place> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlayout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Place album = albumList.get(position);
        holder.title.setText(album.getName());
        Glide.with(mContext).load(album.getImage()).into(holder.thumbnail);
        holder.vRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("Item", albumList.get(position));
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}

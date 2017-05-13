package com.example.android.alexapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by amany on 3/22/2017.
 */

public class PlanRecycleViewAdapter extends RecyclerView.Adapter<PlanRecycleViewAdapter.MyViewHolder> {

   ArrayList<Place> myList;
    Context planContext;


    public PlanRecycleViewAdapter(Context context,ArrayList<Place> list){
        super();
       // myList = new ArrayList<Place>();
       // this.fragmentActivity=fragmentActivity;
        this.myList = list;
        this.planContext = context;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_plan_layout_fragment,null);
        MyViewHolder customViewHolder =new MyViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
       Place place = myList.get(position);

        Picasso.with(planContext).load(place.getImage()).into(holder.placeImg);

       holder.placeDes.setText(place.getDescribtion().substring(0,20) +"...");

        holder.placeVisitDuration.setText(place.getVisitDuration()+"");

        //details button
        holder.detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(planContext,DetailsActivity.class);
                detailIntent.putExtra("Item",myList.get(position));
                planContext.startActivity(detailIntent);
            }
        });

        //map button
        holder.mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  mapIntent = new Intent(planContext,MapsLocationActivity.class);
                mapIntent.putExtra("location",myList.get(position));
                planContext.startActivity(mapIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView placeImg;
        TextView placeVisitDuration,placeDes;
        ImageButton detailsBtn,mapBtn;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.placeImg = (ImageView) itemView.findViewById(R.id.profile_image1);
            this.placeVisitDuration = (TextView) itemView.findViewById(R.id.visitDuration);
            this.placeDes = (TextView) itemView.findViewById(R.id.placeDescriptionPlan);
           this.detailsBtn=(ImageButton) itemView.findViewById(R.id.details_button);
           this.mapBtn = (ImageButton) itemView.findViewById(R.id.map_routing_button);
        }
    }

 }

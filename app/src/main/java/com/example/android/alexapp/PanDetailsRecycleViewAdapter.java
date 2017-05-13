package com.example.android.alexapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by amany on 4/8/2017.
 */

public class PanDetailsRecycleViewAdapter extends RecyclerView.Adapter<PanDetailsRecycleViewAdapter.MyViewHolder> {

    ArrayList<Place> myList;
    Context context;
    DatabaseReference mDatabase1;

    //get data from firebase
    FirebaseDatabase mfirebasedatabase = FirebaseDatabase.getInstance();

    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


    public PanDetailsRecycleViewAdapter(Context context, ArrayList<Place> list){
        this.context = context;
        this.myList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_item_plan_recycleview_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PanDetailsRecycleViewAdapter.MyViewHolder holder, int position) {

        final Place myplace = myList.get(position);
        holder.placeDetailsName.setText(myplace.getName());
        holder.placeDetailsDuration.setText(String.valueOf(myplace.getVisitDuration()));
        Picasso.with(context).load(myplace.getImage()).into(holder.myPlanDetailsImg);
        holder.isVisited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.isVisited.isChecked()){
                 //   mDatabase1 = mfirebasedatabase.getReference("root").child("Users").child(userId).child("plan").child("Places").child("isVisited").setValue(true);



                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        CircleImageView myPlanDetailsImg;
        TextView placeDetailsName,placeDetailsDuration;
        CheckBox isVisited;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.myPlanDetailsImg = (CircleImageView)itemView.findViewById(R.id.myCircle_img);
            this.placeDetailsName = (TextView) itemView.findViewById(R.id.placeNamePlan);
            this.placeDetailsDuration = (TextView) itemView.findViewById(R.id.visit_place_planDuration);
            this.isVisited = (CheckBox) itemView.findViewById(R.id.plan_details_isvisited_checkbox);
        }
    }
}

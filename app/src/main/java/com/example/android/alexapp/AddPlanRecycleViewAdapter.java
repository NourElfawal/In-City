package com.example.android.alexapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by amany on 4/5/2017.
 */

public class AddPlanRecycleViewAdapter extends RecyclerView.Adapter<AddPlanRecycleViewAdapter.MyViewHolder> {

    ArrayList<Place> myList;
    Context planContext;
    int myDuration;
    HashMap<Integer, Place> checkedPlaces;
    AddNewPlanActivity planActivity;
    int allUserDuration;


    public AddPlanRecycleViewAdapter(Context context, ArrayList<Place> list, int allDuration) {
        super();
        this.myList = list;
        this.planContext = context;
        this.allUserDuration = allDuration;
        checkedPlaces = new HashMap<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_plan_item_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Place place = myList.get(position);

        Picasso.with(planContext).load(place.getImage()).into(holder.placeImg);

        holder.placeVisitDuration.setText(String.valueOf(place.getVisitDuration()));
        holder.placeName.setText(place.getName());

        if (checkedPlaces.containsKey(place.getId())) {
            holder.planCheckPlace.setChecked(true);
        } else {
            holder.planCheckPlace.setChecked(false);
        }

        holder.planCheckPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int placeVisitDuration = place.getVisitDuration();
                if (holder.planCheckPlace.isChecked()) {
                    if (allUserDuration >= placeVisitDuration) {
                        allUserDuration -= placeVisitDuration;
                        Toast.makeText(planContext, "remain duraion = " + allUserDuration, Toast.LENGTH_SHORT).show();
                        checkedPlaces.put(place.getId(), place);
                        Toast.makeText(planContext, "places you choose " + checkedPlaces.size(), Toast.LENGTH_SHORT).show();
                    } else {
                        holder.planCheckPlace.setChecked(false);
//                        checkedPlaces.remove(place.getId());
                        Toast.makeText(planContext, "places you choose " + checkedPlaces.size(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(planContext, "Sorry,there isn't enough time", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    allUserDuration += placeVisitDuration;
                    checkedPlaces.remove(place.getId());
                    Toast.makeText(planContext, "remain duraion = " + allUserDuration, Toast.LENGTH_SHORT).show();
                    Toast.makeText(planContext, "places now" + checkedPlaces.size(), Toast.LENGTH_SHORT).show();
                }
            }
        });

//        holder.planCheckPlace.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//            }
//        });
    }

    public ArrayList<Place> getCheckedPlaces() {
        return new ArrayList<>(checkedPlaces.values());
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView placeImg;
        TextView placeVisitDuration, placeName;
        CheckBox planCheckPlace;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.placeImg = (CircleImageView) itemView.findViewById(R.id.plan_img_recycleView);
            this.placeVisitDuration = (TextView) itemView.findViewById(R.id.visit_Duration_add_plan);
            this.placeName = (TextView) itemView.findViewById(R.id.add_plan_place_name);
            this.planCheckPlace = (CheckBox) itemView.findViewById(R.id.check_box_plan);

        }
    }

}


package com.example.android.alexapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by amany on 3/22/2017.
 */

public class PlanFragment extends Fragment {
    DatabaseReference mDatabase1;
    RecyclerView myRecyclerView;
    PlanRecycleViewAdapter recAdapter;
    ArrayList<Place> historicList = new ArrayList<>();
    private int x = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.container_recyclerview_plan, container, false);

        x = getArguments().getInt("Type");

        myRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_plan);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //get data from firebase
        FirebaseDatabase mfirebasedatabase = FirebaseDatabase.getInstance();
        mDatabase1 = mfirebasedatabase.getReference("root").child("Places");
        fillData();
        return rootView;

    }

    private void fillData() {
        switch (x) {
            case 1:
                mDatabase1.addValueEventListener(new ValueEventListener() {
                    public void onDataChange(DataSnapshot snapshot) {
                        historicList.clear();
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Place p = postSnapshot.getValue(Place.class);
                            if (p.getId()== 1 || p.getId()== 13 || p.getId()== 2 || p.getId()== 8 ||p.getId()== 10) {
                                historicList.add(p);
                            }

                        }

                        recAdapter = new PlanRecycleViewAdapter(getActivity(),historicList);
                        myRecyclerView.setAdapter(recAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
                break;
            case 2:
                mDatabase1.addValueEventListener(new ValueEventListener() {
                    public void onDataChange(DataSnapshot snapshot) {
                        historicList.clear();
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Place p = postSnapshot.getValue(Place.class);
                            if (p.getId()==3 || p.getId()== 4 || p.getId()== 5 || p.getId() == 13 || p.getId()==1) {
                                historicList.add(p);
                            }
                        }

                        recAdapter = new PlanRecycleViewAdapter(getActivity(),historicList);
                        myRecyclerView.setAdapter(recAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
                break;
            case 3:
                mDatabase1.addValueEventListener(new ValueEventListener() {
                    public void onDataChange(DataSnapshot snapshot) {
                        historicList.clear();
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Place p = postSnapshot.getValue(Place.class);
                            if (p.getId() == 6 || p.getId() == 7 || p.getId() == 9 || p.getId() == 12 || p.getId() == 8)

                                historicList.add(p);

                        }

                        recAdapter = new PlanRecycleViewAdapter(getActivity(),historicList);
                        myRecyclerView.setAdapter(recAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
                break;


        }


    }
}

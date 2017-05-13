package com.example.android.alexapp;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amany on 3/31/2017.
 */

public class Home_recycleView_fragment extends Fragment {
    List<Place> historicList = new ArrayList<>();
    DatabaseReference mDatabase1;
    HomeRecycleViewAdapter recyclerAdapter;
    RecyclerView recyclerView;
    Spinner homeSpinner;
    private ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_recycleview_fragment, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.home_recycleviw);

//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);

        homeSpinner = (Spinner) rootView.findViewById(R.id.home_recycleview_spinner);
        progressDialog = new ProgressDialog(getActivity());
        //if the image is loaded
        //displaying a progress dialog
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.filter,R.layout.spinner_item_layout);
        homeSpinner.setPopupBackgroundResource(R.color.spinnerColor);
        homeSpinner.setAdapter(adapter);
        //spinner for filtering
        homeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                //determine theposition of filterspinner click
                switch (i) {
                    case 0:
                        mDatabase1.addValueEventListener(new ValueEventListener() {
                            public void onDataChange(DataSnapshot snapshot) {
                                historicList.clear();
                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    Place p = postSnapshot.getValue(Place.class);
                                    if (p.isShow()) {
                                        historicList.add(p);
                                    }
                                }
                                recyclerAdapter = new HomeRecycleViewAdapter(getActivity(), historicList);
                                recyclerView.setAdapter(recyclerAdapter);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                        break;
                    case 1:
                        mDatabase1.addValueEventListener(new ValueEventListener() {
                            public void onDataChange(DataSnapshot snapshot) {
                                historicList.clear();
                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    Place p = postSnapshot.getValue(Place.class);
                                    if (p.getType().equals("Historical") && p.isShow()) {
                                        historicList.add(p);
                                    }
                                }
                                recyclerAdapter = new HomeRecycleViewAdapter(getActivity(), historicList);
                                recyclerView.setAdapter(recyclerAdapter);
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
                                    if (p.getType().equals("Entertainments and Shopping") && p.isShow()) {
                                        historicList.add(p);
                                    }
                                }
                                recyclerAdapter = new HomeRecycleViewAdapter(getActivity(), historicList);
                                recyclerView.setAdapter(recyclerAdapter);
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
                                    if (p.getType().equals("Hotels and Restaurant")&& p.isShow()) {
                                        historicList.add(p);
                                    }
                                }
                                recyclerAdapter = new HomeRecycleViewAdapter(getActivity(), historicList);
                                recyclerView.setAdapter(recyclerAdapter);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //get data from firebase
        FirebaseDatabase mfirebasedatabase = FirebaseDatabase.getInstance();
        mDatabase1 = mfirebasedatabase.getReference("root").child("Places");


        mDatabase1.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                historicList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Place p = postSnapshot.getValue(Place.class);
                    historicList.add(p);
                }
                recyclerAdapter = new HomeRecycleViewAdapter(getActivity(),historicList);
//                /////////////
//                recyclerView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
//                //////////////
                recyclerView.setAdapter(recyclerAdapter);
                progressDialog.dismiss();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) rootView.findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rootView;
    }
}

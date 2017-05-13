package com.example.android.alexapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by amany on 4/8/2017.
 */

public class ListOfMyPlansActivity extends AppCompatActivity {
    ArrayList<Plan> planList;
    ArrayList<String> planNameList;
    ListView myList;
    DatabaseReference mDatabase1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_dynamic_plan);
        planNameList = new ArrayList<String>();
        planList = new ArrayList<Plan>();
        myList = (ListView) findViewById(R.id.dynamicplans_list);
        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListOfMyPlansActivity.this, AddNewPlanActivity.class);
                startActivity(intent);
            }
        });

        //============================= toolbar Editing===============//
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetialsActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Plans");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //=============================================================//


        //get data from firebase
        FirebaseDatabase mfirebasedatabase = FirebaseDatabase.getInstance();

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase1 = mfirebasedatabase.getReference("root").child("Users").child(userId).child("plan");

        mDatabase1.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                planList.clear();
                planNameList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    if (!postSnapshot.getKey().equals("planCount")) {
                        Plan p = postSnapshot.getValue(Plan.class);
                        planList.add(p);
                        planNameList.add(p.getname());
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListOfMyPlansActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, planNameList);
                myList.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //click listener on listview
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Plan myplan = planList.get(position);
                Intent intent = new Intent(ListOfMyPlansActivity.this, DetailsOfUserPlanActivity.class);
                intent.putExtra("plan", myplan);
                startActivity(intent);

            }
        });
    }
}

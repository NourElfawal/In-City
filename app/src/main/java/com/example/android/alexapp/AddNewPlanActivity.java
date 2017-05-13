package com.example.android.alexapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by amany on 4/5/2017.
 */

public class AddNewPlanActivity extends AppCompatActivity {
    EditText  planName;
    RecyclerView addPlanRecyclView;
    ArrayList<Place> historicList = new ArrayList<>();
    DatabaseReference mDatabase1;
    DatabaseReference mDatabasePlan;
    AddPlanRecycleViewAdapter recyclerAdapter;
    ArrayList<Place> userPlacesChecked;
    FloatingActionButton save_btn;
    int allDuration;
    FirebaseDatabase mfirebasedatabase;
    User user;
    String user_id;
    Plan myPlan;
    int planContNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_plan_layout);
        planName = (EditText)findViewById(R.id.new_plan_name);

        addPlanRecyclView = (RecyclerView)findViewById(R.id.add_plan_recycleView);
        addPlanRecyclView.setLayoutManager(new LinearLayoutManager(AddNewPlanActivity.this));
        save_btn = (FloatingActionButton) findViewById(R.id.floatingAction_save);

        //get data from firebase
        mfirebasedatabase = FirebaseDatabase.getInstance();
        userPlacesChecked = new ArrayList<>();
        user = new User();
        myPlan = new Plan();

        //Intent intent = getIntent();
        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabasePlan =  mfirebasedatabase.getReference("root").child("Users").child(user_id);

        //open dialog
        openDialog();


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(planName.getText().toString().matches("")){
                    Toast.makeText(AddNewPlanActivity.this, "Sorry,plan data not complete", Toast.LENGTH_SHORT).show();
                }else {
                    mDatabasePlan.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            mDatabasePlan.child("plan").child("planCount").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue(Integer.class) != null)
                                        planContNum = dataSnapshot.getValue(Integer.class);
                                    else
                                        planContNum = 0;
                                    userPlacesChecked = recyclerAdapter.getCheckedPlaces();
                                    myPlan.setname(planName.getText().toString());
                                    myPlan.setduration(allDuration);
                                    myPlan.setplaces(userPlacesChecked);
                                    user.setid(user_id);
                                    user.setplan(myPlan);
                                    myPlan.setPlanId(++planContNum);
                                    mDatabasePlan.child("plan").child("planCount").setValue(planContNum);
                                    mDatabasePlan.child("plan").child(String.valueOf(planContNum)).setValue(myPlan);

                                  finish();

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        // userPlacesChecked = recyclerAdapter.getCheckedPlaces();

    }
    public int getAllDuration(){
        return allDuration;
    }
    public void openDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("How many days will You stay?");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(input.getText().toString().matches("")){
                    Toast.makeText(AddNewPlanActivity.this, "enter number", Toast.LENGTH_SHORT).show();
                }else {
                    allDuration = (Integer.valueOf(input.getText().toString())) * 9;
                    mDatabase1 = mfirebasedatabase.getReference("root").child("Places");
                    mDatabase1.addListenerForSingleValueEvent(new ValueEventListener() {
                        public void onDataChange(DataSnapshot snapshot) {
                            historicList.clear();
                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                Place p = postSnapshot.getValue(Place.class);
                                historicList.add(p);
                            }
                            recyclerAdapter = new AddPlanRecycleViewAdapter(AddNewPlanActivity.this, historicList, getAllDuration());
                            addPlanRecyclView.setAdapter(recyclerAdapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    dialog.dismiss();
                }

                }

        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
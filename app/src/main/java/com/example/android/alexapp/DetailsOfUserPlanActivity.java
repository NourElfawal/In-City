package com.example.android.alexapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by amany on 4/5/2017.
 */

public class DetailsOfUserPlanActivity extends AppCompatActivity {
    RecyclerView recyclerViewOfPlanDetails;
    TextView planName,planDuration;
    PlanRecycleViewAdapter myAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_of_dynamic_plan);

        //============================= toolbar Editing===============//
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetialsActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Plan Details");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //=============================================================//

        recyclerViewOfPlanDetails = (RecyclerView) findViewById(R.id.plan_details_recyclerview);
        planName = (TextView) findViewById(R.id.plan_item_details_name);
        planDuration = (TextView) findViewById(R.id.plan_item_details_duration);

        Intent intent = getIntent();
       Plan myplan = (Plan) intent.getSerializableExtra("plan");
        planName.setText(myplan.getname());
        planDuration.setText(String.valueOf((myplan.getduration())/9));
        myAdapter = new PlanRecycleViewAdapter(DetailsOfUserPlanActivity.this,myplan.getplaces());
        recyclerViewOfPlanDetails.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOfPlanDetails.setAdapter(myAdapter);
    }
}

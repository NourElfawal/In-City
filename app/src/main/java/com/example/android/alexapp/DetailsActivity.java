package com.example.android.alexapp;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;

public class DetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double longt;
    private double lati;
    LatLng place;
    DatabaseReference mDatabase1;
        Context mContext;
        ImageView imge;
        TextView placeTitle,placeDescription,placeWorkHour;
    Place p1,detailsPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        //============================= toolbar Editing===============//
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetialsActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Place Details");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //=============================================================//


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //================================= enable location manager================//
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        } else {
        }

        //========================================= map functionality end=================//

        imge = (ImageView) findViewById(R.id.placeImagDetails);
        placeTitle = (TextView) findViewById(R.id.placeNameDetails);
        placeDescription=(TextView) findViewById(R.id.descriptionDetails);
        placeWorkHour = (TextView) findViewById(R.id.operatingHourDetails);

       p1 = (Place) getIntent().getSerializableExtra("Item");

        lati = LocationParsing.getLatit(p1.getLocation());
        longt = LocationParsing.getLongit(p1.getLocation());

        placeTitle.setText(p1.getName());
        Glide.with(DetailsActivity.this).load(p1.getImage()).into(imge);
        placeDescription.setText(p1.getDescribtion());
        placeWorkHour.setText(p1.getHourOfWork());

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in specific place and move the camera
        place = new LatLng(lati,longt);
        mMap.addMarker(new MarkerOptions().position(place).title(p1.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place, 16));

    }
}

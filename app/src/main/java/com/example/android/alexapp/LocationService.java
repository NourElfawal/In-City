package com.example.android.alexapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by amany on 3/30/2017.
 */

public class LocationService extends Service {
    LocationListener locationListener;
    LocationManager locationManager;
    DatabaseReference mDatabase1;
    Place nearest_place;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //get data from firebase
        FirebaseDatabase mfirebasedatabase = FirebaseDatabase.getInstance();
        mDatabase1 = mfirebasedatabase.getReference("root").child("Places");
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final android.location.Location location) {
                mDatabase1.addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(DataSnapshot snapshot) {
                        float smallest_distance = -1;
                         nearest_place = null;
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Place p = postSnapshot.getValue(Place.class);
                            String place_location = p.getLocation();
                            double longit = LocationParsing.getLongit(place_location);
                            double latit = LocationParsing.getLatit(place_location);
                            float[] r = new float[10];
                            Location.distanceBetween(latit,longit,location.getLatitude(),location.getLongitude(),r);
                            float calculatedDistance = r[0];
                            if(calculatedDistance < 500) {
                                if (smallest_distance < 0) {
                                    smallest_distance = calculatedDistance;
                                    nearest_place = p;
                                }
                                else if (calculatedDistance < smallest_distance)
                                {
                                    smallest_distance = calculatedDistance;
                                    nearest_place = p;
                                }
                            }
                        }

                        if(nearest_place != null){
                            //notification method
                            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                            intent.putExtra("Item",nearest_place);
                           // startActivity(intent);
                            PendingIntent contentIntent = PendingIntent.getActivity(LocationService.this,0,intent,0);

                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(LocationService.this)
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setContentTitle(nearest_place.getName())
                                    .setContentText("You are near to this place");
                            mBuilder.setContentIntent(contentIntent);
                            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
                            mBuilder.setAutoCancel(true);
                            NotificationManager mNotificationManager =
                                    (NotificationManager) LocationService.this.getSystemService(Context.NOTIFICATION_SERVICE);
                            mNotificationManager.notify(1, mBuilder.build());

                        }else {
                            // do nothing
                        }

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


//                Intent i = new Intent("Updated_location");
//                i.putExtra("location_coordinates", location.getLongitude() + " " + location.getLatitude());
//                sendBroadcast(i);
            }


            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        //noinspection MissingPermission
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,500, locationListener);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            //noinspection MissingPermission
            locationManager.removeUpdates(locationListener);
        }
    }
}

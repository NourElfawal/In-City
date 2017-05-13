package com.example.android.alexapp;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//    GridLayoutManager lLayout;
    RecyclerView myRecyclerView;
    DatabaseReference mDatabase1;
    int day_num;
    MenuItem tracking;
    NavigationView navigationView;
    private ProgressDialog progressDialog;
    ProfilePictureView profilePictureFace;
    private ImageView  imgProfileGoogle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_for_days);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        myRecyclerView = (RecyclerView) findViewById(R.id.recycler_plan);





//        //=========== adding recycleview in gride shape=============//
//        lLayout = new GridLayoutManager(MainActivity.this,2);
////        myRecyclerView.setHasFixedSize(true);
//        myRecyclerView.setLayoutManager(lLayout);
//       // =========================finish=================//



        //adding home recycleview fragment
        Home_recycleView_fragment homeFragment = new Home_recycleView_fragment();
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction ft1 = fm1.beginTransaction();
        ft1.add(R.id.home_recycleviw_fragment_container, homeFragment, "main_fragment");
        ft1.commit();


        initCollapsingToolbar();

//        //get data from firebase
        FirebaseDatabase mfirebasedatabase = FirebaseDatabase.getInstance();
        mDatabase1 = mfirebasedatabase.getReference("root").child("Places");

        try {
            Glide.with(this).load(R.drawable.colliapsimage).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        progressDialog = new ProgressDialog(this);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);

        navigationView.getMenu().findItem(R.id.tracking).setEnabled(true);


        Intent intent = getIntent();
        String email = intent.getStringExtra("Email");
        String name = intent.getStringExtra("Name");
///////fac img profile///////
        String idface = intent.getStringExtra("idface");
        if(idface!=null) {
            profilePictureFace = (ProfilePictureView) header.findViewById(R.id.friendProfilePicture);
            profilePictureFace.setProfileId(idface);
            profilePictureFace.setVisibility(View.VISIBLE);
        }
////////////////////////////google img
        String googleImg = intent.getStringExtra("GoogleImg");
        imgProfileGoogle = (ImageView) header.findViewById(R.id.imgProfileGoogle);
        Glide.with(getApplicationContext()).load(googleImg)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfileGoogle);

///////////////////////////////
        final TextView textViewName=(TextView)header.findViewById(R.id.textViewName);
       if(name !=null) {
           textViewName.setText(name);
       }


//        Toast.makeText(this, idface, Toast.LENGTH_SHORT).show();
    }

    //============= to go back when press on arrow in toolbar==========//
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }
    //==============================================================//

    private void signOut(){
        LoginManager.getInstance().logOut();
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, LoginActivity.class));

    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }


    ///////////////////////////////////
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void enable_buttons() {
        navigationView.getMenu().findItem(R.id.tracking).setEnabled(true);
    }

    private boolean runtime_permittions() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            return true;
        }
        return false;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                enable_buttons();
            } else {
                runtime_permittions();
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();

//        if (id == R.id.nav_1day) {
//            day_num = 1;
//            Fragment fragment = getFragmentManager().findFragmentByTag("home_fragment");
//            FragmentManager fm1 = getFragmentManager();
//            if (fragment instanceof PlanFragment) {
//                FragmentTransaction trans = fm1.beginTransaction();
//                trans.remove(fragment);
//                trans.commit();
//                fm1.popBackStack();
//            }
//
//            PlanFragment day1Fragment = new PlanFragment();
//            Bundle bundl = new Bundle();
//            bundl.putInt("Type", 1);
//            day1Fragment.setArguments(bundl);
//            FragmentTransaction ft1 = fm1.beginTransaction();
//            ft1.replace(R.id.home_recycleviw_fragment_container, day1Fragment, "home_fragment");
//            ft1.addToBackStack(null);
//            ft1.commit();
//
//        } else if (id == R.id.nav_2days) {
//            day_num = 2;
//            Fragment fragment = getFragmentManager().findFragmentByTag("home_fragment");
//            FragmentManager fm1 = getFragmentManager();
//            if (fragment instanceof PlanFragment) {
//                FragmentTransaction trans = fm1.beginTransaction();
//                trans.remove(fragment);
//                trans.commit();
//                fm1.popBackStack();
//            }
//            PlanFragment day1Fragment = new PlanFragment();
//
//            Bundle bundl = new Bundle();
//            bundl.putInt("Type", 2);
//            day1Fragment.setArguments(bundl);
//
//
//            FragmentTransaction ft1 = fm1.beginTransaction();
//            ft1.replace(R.id.home_recycleviw_fragment_container, day1Fragment, "home_fragment");
//            ft1.addToBackStack(null);
//            ft1.commit();
//
//
//        } else if (id == R.id.nav_3days) {
//            day_num = 3;
//            Fragment fragment = getFragmentManager().findFragmentByTag("home_fragment");
//            FragmentManager fm1 = getFragmentManager();
//            if (fragment instanceof PlanFragment) {
//                FragmentTransaction trans = fm1.beginTransaction();
//                trans.remove(fragment);
//                trans.commit();
//                fm1.popBackStack();
//            }
//            PlanFragment day1Fragment = new PlanFragment();
//
//            Bundle bundl = new Bundle();
//            bundl.putInt("Type", 3);
//            day1Fragment.setArguments(bundl);
//
//            FragmentTransaction ft1 = fm1.beginTransaction();
//            ft1.replace(R.id.home_recycleviw_fragment_container, day1Fragment, "home_fragment");
//            ft1.addToBackStack(null);
//            ft1.commit();
//
//
//            {
//
//            }
//
//        }
       if (id== R.id.myPlans) {
            Intent intent = new Intent(this,ListOfMyPlansActivity.class);

            startActivity(intent);


        } else if (id ==R.id.addnewplace){
            Intent addplaceinetent=new Intent(this,AddNewPlaceActivity.class);
            startActivity(addplaceinetent);

        } else if (id == R.id.nav_signout) {
            signOut();


        } else if (id == R.id.tracking) {
            String isStarted = PrefsUtils.readPrefs(PrefsUtils.PREFS_SERVICE_FLAG, "", MainActivity.this);
            if (TextUtils.isEmpty(isStarted)) {
                Intent serviceIntent = new Intent(getApplicationContext(), LocationService.class);
                startService(serviceIntent);
                Toast.makeText(this, "Start Notification", Toast.LENGTH_SHORT).show();
                PrefsUtils.writePref(PrefsUtils.PREFS_SERVICE_FLAG, "Start Notification", MainActivity.this);
                item.setTitle("Stop Notification");

            } else {
                Intent serviceIntent = new Intent(getApplicationContext(), LocationService.class);
                stopService(serviceIntent);
                Toast.makeText(this, "Stop Notification ", Toast.LENGTH_SHORT).show();
                PrefsUtils.removePref(PrefsUtils.PREFS_SERVICE_FLAG, MainActivity.this);
                item.setTitle("Notify My Nearby Placse");
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}





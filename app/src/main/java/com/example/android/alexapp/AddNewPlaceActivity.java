package com.example.android.alexapp;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by amany on 4/3/2017.
 */

public class AddNewPlaceActivity extends AppCompatActivity implements View.OnClickListener {
   final int CAMERA_REQUEST = 1;
   final int GALLERY_REQUEST = 2;
   final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 3;
    boolean flag = false;
    int countNum;
    String DOWNLOAD_URL;

    com.example.android.alexapp.Place myNewPlace;

    private ImageView place_ImageView;
    private ImageButton cameraBtn, galleryBtn;
    private DatabaseReference mDatabase1;
    private StorageReference storageRef;
    private FloatingActionButton addBtn;
    EditText place_Location,place_name,place_description,opining_time,visit_duration,opening_timeto;
    RadioButton historical,shoping,restaurant;
    Place newPlace;
    Uri downloadUrl;

    int hourfrom, minfrom,hourto,minto;
    private final static int DIALOG_FROM_TIME_PICKER = 0;
    private final static int DIALOG_TO_TIME_PICKER = 1;
    private int dialog=0;
    private String total_opening_time;
    String strDatefrom;
    String strDateto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_place_layout);

        //============================= toolbar Editing===============//
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetialsActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Place");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //=============================================================//

       // find views by id
        cameraBtn = (ImageButton) findViewById(R.id.camera_btn);
        galleryBtn = (ImageButton) findViewById(R.id.gallery_btn);
        addBtn = (FloatingActionButton) findViewById(R.id.floating_add_new_place);
        place_ImageView = (ImageView) findViewById(R.id.place_image_view);

        place_Location = (EditText) findViewById(R.id.new_place_location);
        place_name = (EditText) findViewById(R.id.new_place_name);
        place_description = (EditText) findViewById(R.id.new_place_desc);

        //===================Edited two EditText for Time Picker ========================================//
        opining_time = (EditText) findViewById(R.id.new_place_hourOfWork);
        opening_timeto=(EditText)findViewById(R.id.new_place_hourOfWorkto);
        //==============================================================================================//

        visit_duration = (EditText)findViewById(R.id.new_place_visitDuration);
        historical = (RadioButton) findViewById(R.id.radioButton_historical);
        shoping = (RadioButton) findViewById(R.id.radioButton_shoping);
        restaurant = (RadioButton) findViewById(R.id.radioButton_restaurant);


        myNewPlace = new com.example.android.alexapp.Place();

        //set click listener on buttons
        historical.setOnClickListener(this);
        shoping.setOnClickListener(this);
        restaurant.setOnClickListener(this);
        galleryBtn.setOnClickListener(this);
        cameraBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        opining_time.setOnClickListener(this);
        opening_timeto.setOnClickListener(this);


        // [START initialize_database_ref]
        FirebaseDatabase mfirebasedatabase = FirebaseDatabase.getInstance();
        mDatabase1 = mfirebasedatabase.getReference("root");

        //get reference of data storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
//        storageRef = storage.getReferenceFromUrl("gs://alexapp-258bf.appspot.com");
        storageRef = storage.getReference();

        //auto complete forplace location
        place_Location.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP)
                    try {
                        flag = false;
                        Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                .build(AddNewPlaceActivity.this);
                        startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                    } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                        // Handle the error.
                        e.printStackTrace();
                    }
                return false;
            }
        });

        }


    // on activity result for openeing from gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_REQUEST:
                    Uri selectedImageUri = data.getData();
                    if (null != selectedImageUri) {
                        // Get the path from the Uri
                        String path = getPathFromURI(selectedImageUri);
                        Log.i("IMAGE PATH TAG", "Image Path : " + path);
                        // Set the image in ImageView
                        place_ImageView.setImageURI(selectedImageUri);
                    }
                    break;
                case CAMERA_REQUEST:
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    place_ImageView.setImageBitmap(photo);
                    break;
                case PLACE_AUTOCOMPLETE_REQUEST_CODE:
                     newPlace = PlaceAutocomplete.getPlace(this, data);
                    place_Location.setText(newPlace.getName());
                    break;

            }
        }
    }


    private String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gallery_btn:
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), GALLERY_REQUEST);
             break;
            case R.id.camera_btn:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST);
                break;
            case R.id.floating_add_new_place:

                mDatabase1.child("count").addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       if(dataSnapshot.getValue(Integer.class) != null)
                           countNum = dataSnapshot.getValue(Integer.class);
                       else
                            countNum = 0;
                       if (place_ImageView.getDrawable() != null) {

                           StorageReference myfileRef = storageRef.child(++countNum+".jpg");
                           place_ImageView.setDrawingCacheEnabled(true);
                           place_ImageView.buildDrawingCache();
                           Bitmap bitmap = place_ImageView.getDrawingCache();
                           ByteArrayOutputStream baos = new ByteArrayOutputStream();
                           bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                           byte[] data = baos.toByteArray();

                           UploadTask uploadTask = myfileRef.putBytes(data);
                           uploadTask.addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception exception) {
                                   Toast.makeText(AddNewPlaceActivity.this, "TASK FAILED", Toast.LENGTH_SHORT).show();
                               }
                           }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                               @Override
                               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                   Toast.makeText(AddNewPlaceActivity.this, "TASK SUCCEEDED", Toast.LENGTH_SHORT).show();
                                   downloadUrl = taskSnapshot.getDownloadUrl();
                                   DOWNLOAD_URL = downloadUrl.getPath();
                                   myNewPlace.setName( place_name.getText().toString());
                                   myNewPlace.setDescribtion(place_description.getText().toString());
                                   myNewPlace.setHourOfWork(total_opening_time);
                                   myNewPlace.setLocation(newPlace.getLatLng().toString());
                                   myNewPlace.setVisitDuration(Integer.parseInt(visit_duration.getText().toString()));
                                   myNewPlace.setImage(downloadUrl.toString());


                                   myNewPlace.setId(countNum);
                                   mDatabase1.child("count").setValue(countNum);
                                   mDatabase1.child("Places").child(String.valueOf(countNum)).setValue(myNewPlace);
                                   finish();
                               }
                           });

                       } else {
                           Toast.makeText(AddNewPlaceActivity.this, "Please,complete place data defor adding", Toast.LENGTH_SHORT).show();
                       }

                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });

                break;
            case R.id.radioButton_historical:
                myNewPlace.setType("Historical");
                break;
            case R.id.radioButton_shoping:
                myNewPlace.setType("Entertainments and Shopping");
                break;
            case R.id.radioButton_restaurant:
                myNewPlace.setType("Hotels and Restaurant");
                break;
     //=========================== adding  action time picker from and to ==================================//
            case R.id.new_place_hourOfWork :
                dialog = DIALOG_FROM_TIME_PICKER;
                showTimePickerDialog (DIALOG_FROM_TIME_PICKER);
                break;
            case R.id.new_place_hourOfWorkto :
                dialog = DIALOG_TO_TIME_PICKER;
                showTimePickerDialog(DIALOG_TO_TIME_PICKER);
                break;
     //======================================================================================================//

        }
    }

    //=========================for time piker================//
    private void showTimePickerDialog ( int id )
    {
        switch ( id )
        {
            case DIALOG_FROM_TIME_PICKER:

                new TimePickerDialog(AddNewPlaceActivity.this,mTimeSetListener,hourfrom,minfrom,false).show();
                break;
            case DIALOG_TO_TIME_PICKER:
                new TimePickerDialog(AddNewPlaceActivity.this, mTimeSetListener, hourto,minto, false).show();
        }
    }

    private TimePickerDialog.OnTimeSetListener mTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if ( dialog == DIALOG_FROM_TIME_PICKER ) {
                hourfrom = hourOfDay;
                minfrom = minute;

                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
                Date date = new Date(0, 0, 0, hourfrom, minfrom);
                strDatefrom = timeFormat.format(date);

                opining_time.setText("" + strDatefrom);
            }else  if ( dialog == DIALOG_TO_TIME_PICKER ){
                hourto = hourOfDay;
                minto = minute;

                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
                Date date = new Date(0, 0, 0, hourto, minto);
                strDateto = timeFormat.format(date);

                opening_timeto.setText("" + strDateto);
                total_opening_time = "from "+strDatefrom+" to "+strDateto;

            }


        }
    };
    //==================================================================//
}
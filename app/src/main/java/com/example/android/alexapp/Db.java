package com.example.android.alexapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by amany on 3/20/2017.
 */

public class Db extends AppCompatActivity {

    private DatabaseReference mDatabase1;
    private Bitmap bm;
    private StorageReference storageRef;
    private Uri imageURL;
    ImageView myImage;

    Button send;
    int id1,id2,id3,id4,id5,id6,id7,id8,id9,id10,id11,id12,id13,id14,visitDuration1,visitDuration2,visitDuration3,visitDuration4,visitDuration5,visitDuration6
            ,visitDuration7,visitDuration8,visitDuration9,visitDuration10,visitDuration11,visitDuration12,visitDuration13,visitDuration14;
    String d1,h1,location,img1,name1,type1,d2,h2,location2,img2,name2,type2,d3,h3,location3,img3,name3,type3,
            d4,h4,location4,img4,name4,type4,d5,h5,location5,img5,name5,type5,d6,h6,location6,img6,name6,type6,d7,h7,location7,img7,name7,type7,d8,h8,location8,img8,name8,type8,
            d9,h9,location9,img9,name9,type9,d10,h10,location10,img10,name10,type10,d11,h11,location11,img11,name11,type11,
            d12,h12,location12,img12,name12,type12,d13,h13,location13,img13,name13,type13,d14,h14,location14,img14,name14,type14;
    boolean isVisited1,isVisited2,isVisited3,isVisited4,isVisited5,isVisited6,isVisited7,isVisited8,isVisited9,isVisited10,isVisited11,isVisited12,isVisited13,isVisited14;
    ArrayList<Place> place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db);

        myImage = (ImageView)findViewById(R.id.myImageView);

        // [START initialize_database_ref]
        FirebaseDatabase mfirebasedatabase = FirebaseDatabase.getInstance();
        mDatabase1 = mfirebasedatabase.getReference("root");

        //get reference of data storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        storageRef = storage.getReferenceFromUrl("gs://alexapp-258bf.appspot.com");
        myImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"),100 );
            }
        });



        //find view element
        send = (Button) findViewById(R.id.buttonSend);

        d1 = "Fort Qaitbey was built by Mamluke Sultan Qaitbey in an effort to fortify this important Egyptian port from attack, and rubble from the toppled lighthouse was used in its construction. Inside, you can explore the series of stone-walled chambers and climb up to the roof to look out over the Mediterranean.";
        h1="From 8 AM to 5 PM";
        location = "lat/lng: (31.2140156,29.887827)";
        name1 = "Citadel of Qaitbay ";
        type1 = "Historical";
        img1 = "http://www.planetware.com/photos-large/EGY/egypt-alexandria-fort-qaitbey.jpg";
        id1 = 1;
        visitDuration1=2;
        isVisited1 = false;
        Place p1 = new Place(d1,h1,location,img1,name1,type1,id1,visitDuration1,isVisited1);

        d2 = "The library was arguably one of the largest and most significant libraries of the ancient world, details about it are a mixture of history and legend.Its main purpose was to show off the wealth of Egypt, with research as a lesser goal,but its contents were used to aid the ruler of Egypt.";
        h2="From 11 AM to 7 PM";
        location2 = "lat/lng: (31.2088705,29.9092012)";
        name2 = "Library of Alexandria";
        img2 = "http://www.planetware.com/photos-large/EGY/egypt-alexandria-facade-of-the-bibliotheca-alexandrina.jpg";
        type2 = "Historical";
        id2 = 2;
        visitDuration2=3;
        isVisited2 = false;
        Place p2 = new Place(d2,h2,location2,img2,name2,type2,id2,visitDuration2,isVisited2);

        d3 = "The Greco-Roman Museum, founded in 1892, has a priceless collection of approximately 40,000 objects, including sculpture, mosaics, woodwork, and coins.";
        h3="From 9 AM to 4 PM";
        location3 = "lat/lng: (31.1991587,29.9087806)";
        name3 = "The Greco-Roman Museum";
        img3 = "http://static.egypt.travel/2016/07/IMG_3678-final_Alexthegreat_Statue_Grecoromanmuseum.jpg";
        type3 = "Historical";
        id3 = 3;
        visitDuration3=2;
        isVisited3 = false;
        Place p3 = new Place(d3,h3,location3,img3,name3,type3,id3,visitDuration3,isVisited3);

        d4 = "The Royal Jewelry Museum, located in the neighborhood of Zizenia in the city of Alexandria Museum hosts the most valuable displays including the jewelry and the ornaments that the queens and the princesses of the last royal family of Egypt have wore for a considerable period of time ";
        h4="From 9 AM to 4 PM";
        location4 = "lat/lng: (31.2407,29.9654887)";
        name4 = "Royal Jewellery Museum";
        img4 = "http://home.bt.com/images/st-edwards-crown-136380943414803901-130604140444.jpg";
        type4 = "Historical";
        id4 = 4;
        visitDuration4=1;
        isVisited4 =false;
        Place p4 = new Place(d4,h4,location4,img4,name4,type4,id4,visitDuration4,isVisited4);

        d5 = "Kom el-Dikka, which literally means a ‘pile of rubble,’ was a slum until 1959 when a team of Poles excavated the site in search of the tomb of Alexander the Great";
        h5="From 9 AM to 5 PM";
        location5 = "lat/lng: (31.1944778,29.9068199)";
        name5 = "Kom el-Dikka";
        img5 = "http://static.egypt.travel/2016/08/Greco_Roman1.jpg";
        type5= "Historical";
        id5 = 5;
        visitDuration5=2;
        isVisited5 = false;
        Place p5 = new Place(d5,h5,location5,img5,name5,type5,id5,visitDuration5,isVisited5);

        d6 = "One of Alexandria's major landmarks, the Abu Abbas al-Mursi Mosque was built in 1796 over the tomb of the 13th-century Sufi holy man Abu Abbas al-Mursi.";
        h6="From 9 AM to 5 PM";
        location6 = "lat/lng: (31.2057038,29.8845669)";
        name6 = "Abu al-Abbas al-Mursi";
        img6 = "http://blogs.universityprep.org/wp-content/uploads/sites/73/2015/01/k3.jpg";
        type6= "Historical";
        id6 = 6;
        visitDuration6=2;
        isVisited6 = false;
        Place p6 = new Place(d6,h6,location6,img6,name6,type6,id6,visitDuration6,isVisited6);

        d7 = "Located in one of Alexandria’s most luxurious areas, the San Stefano houses the Four Seasons hotel, private residences, a private marina, and a lavish mall: the Grand Plaza.";
        h7="24 hours";
        location7 = "lat/lng: (31.2454887,29.9697671)";
        name7 = "San Stefano";
        img7 = "http://www.fourseasons.com/content/dam/fourseasons/images/web/ALX/ALX_064_aspect16x9.jpg/jcr:content/renditions/cq5dam.web.1280.720.jpeg";
        type7= "Entertainments and Shopping";
        id7 = 7;
        visitDuration7=3;
        isVisited7 = false;
        Place p7 = new Place(d7,h7,location7,img7,name7,type7,id7,visitDuration7,isVisited7);

        d8 = "Along Fouad Street,a Costa coffee shop near old buildings with Italian and French architecture reminds Egyptians that commercial ventures threaten to erase traces of Alexandria's aristocratic past.";
        h8="24 hours";
        location8 = "lat/lng: (31.1957622,29.9028839)";
        name8 = "Fouad street";
        img8 = "http://www.lprojectmgt.com/uploads/2015/10/L-Passage-7.jpg";
        type8= "Entertainments and Shopping";
        id8 = 8;
        visitDuration8=4;
        isVisited8 = false;
        Place p8 = new Place(d8,h8,location8,img8,name8,type8,id8,visitDuration8,isVisited8);

        d9 = "Samak mashwy (grilled fish) is the signature experience for all visitors to Egypt beautiful northern coast. When you are in the mood to catch Murgan (Gray Mullet),and Barbuni),525 km of azure Mediterranean coastline await you.";
        h9="24 hours";
        location9 = "lat/lng: (31.2352033,29.9511374)";
        name9 = "Fishing";
        img9 = "http://www.planetware.com/photos-large/EGY/egypt-alexandria-eastern-harbour.jpg";
        type9= "Entertainments and Shopping";
        id9 = 9;
        visitDuration9=4;
        isVisited9 = false;
        Place p9 = new Place(d9,h9,location9,img9,name9,type9,id9,visitDuration9,isVisited9);

        d10 = "Situated on the northern coast, the Radisson Blu Hotel, Alexandria lies about 10 minutes from the gorgeous Mediterranean shoreline and just steps from lovely Lake Mariout. Guests also enjoy proximity to the Borg El Arab airport .";
        h10="24 hours";
        location10 = "lat/lng: (31.0557614,29.7219363)";
        name10 = "The Radisson Blu Hotel";
        img10 = "https://cache.carlsonhotels.com/galleries/radblu/photos/webextra/alyzr/home_marquee/ALYZR_exterior_1440x550.jpg";
        type10= "Hotels and Restaurant";
        id10 = 10;
        visitDuration10= 4;
        isVisited10 = false;
        Place p10 = new Place(d10,h10,location10,img10,name10,type10,id10,visitDuration10,isVisited10);

        d11 = "Hilton is only steps away from 370 shops. With two outdoor pools, it also features a sun deck and fitness centre. Alexandria Al Nozha airport is 10 minutes’ drive away.";
        h11="24 hours";
        location11 = "lat/lng: (31.206354,29.9675447)";
        name11 = "Hilton Alexandria Green Plaza";
        img11 = "http://fawzya.com/wp-content/uploads/2015/04/Hilton-Alexandria-Green-Plaza-900x727.jpg";
        type11= "Hotels and Restaurant";
        id11 = 11;
        visitDuration11=5;
        isVisited11 = false;
        Place p11 = new Place(d11,h11,location11,img11,name11,type11,id11,visitDuration11,isVisited11);

        d12 = "Brazilian Coffee Shop in Alexandria is a basic coffee shop located on Saad Zaghloul Street (in front of the Stock Market) in Ramleh; but it’s not your typical café.";
        h12="From 7.5 AM to 10 PM";
        location12 = "lat/lng: (31.1994291,29.9018604)";
        name12 = "Brazilian Coffee Shop";
        img12 = "https://c1.staticflickr.com/4/3398/3189079694_7b4645d19e_b.jpg";
        type12= "Hotels and Restaurant";
        id12 = 12;
        visitDuration12=2;
        isVisited12 = false;
        Place p12 = new Place(d12,h12,location12,img12,name12,type12,id12,visitDuration12,isVisited12);

        d13 = "Mohamed Ahmed is an Alexandrian favourite for its fresh falafel bites and delicious foul sandwiches. Located on Shakour Street in Downtown Alexandria (just around the corner from Ramleh Station and the Metropole Hotel).";
        h13="24 hours";
        location13 = "lat/lng: (31.2001288,29.9028165)";
        name13 = "Mohamed Ahmed";
        img13 = "https://assets.cairo360.com/app/uploads/2011/04/article_original_1333_20101212_43117863-600x323.jpeg";
        type13= "Hotels and Restaurant";
        id13 = 13;
        visitDuration13=2;
        isVisited13 = false;
        Place p13 = new Place(d13,h13,location13,img13,name13,type13,id13,visitDuration13,isVisited13);

        d14 = "Opposite Midan Ramla, this place lives and breathes nostalgia. The cafe part still has its original 1940s fittings, and pastries that taste like they’ve been sitting around since then.";
        h14="24 hours";
        location14 = "lat/lng: (31.2021955,29.9032035)";
        name14 = "Athineos restaurant";
        img14 = "http://www.alexandria.gov.eg/alex/english/images/Restaurants/athineos.jpg";
        type14= "Hotels and Restaurant";
        id14 = 14;
        visitDuration14=2;
        isVisited14 = false;
        Place p14 = new Place(d14,h14,location14,img14,name14,type14,id14,visitDuration14,isVisited14);



        place = new ArrayList<>();
        place.add(p1);
        place.add(p6);
        place.add(p7);
        place.add(p3);
        place.add(p4);
        place.add(p8);
        place.add(p2);
        place.add(p5);
        place.add(p9);
        place.add(p10);
        place.add(p11);
        place.add(p12);
        place.add(p13);
        place.add(p14);



//        //read datafrom firebase(can get any  data from any child)
        send.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
//               StorageReference myfileRef = storageRef.child("myuploadImage1.jpg");
//               Glide.with(Db.this).using(new FirebaseImageLoader()).load(myfileRef).into(myImage);
//              StorageReference myfileRef = storageRef.child("Image3.jpg");
//               myImage.setDrawingCacheEnabled(true);
//               myImage.buildDrawingCache();
//               Bitmap bitmap = myImage.getDrawingCache();
//               ByteArrayOutputStream baos = new ByteArrayOutputStream();
//               bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//               byte[] data = baos.toByteArray();
//
//               myfileRef.putBytes(data).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                   @Override
//                   public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                       if(task.isSuccessful()){
//                           Toast.makeText(Db.this, "you succesfully choose image", Toast.LENGTH_SHORT).show();
//                           Uri downloadUrl =task.getResult().getDownloadUrl();
//                           String DOWNLOAD_URL = downloadUrl.getPath();
//                           Log.v("DOWNLOAD URL", DOWNLOAD_URL);
//                           Toast.makeText(Db.this, DOWNLOAD_URL, Toast.LENGTH_SHORT).show();
//                       }
//                   }
//               }).addOnFailureListener(new OnFailureListener() {
//                   @Override
//                   public void onFailure(@NonNull Exception exception) {
//                       Toast.makeText(Db.this, "TASK FAILED", Toast.LENGTH_SHORT).show();
//                   }
//               }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                   @Override
//                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                       Toast.makeText(Db.this, "TASK SUCCEEDED", Toast.LENGTH_SHORT).show();
//                       Uri downloadUrl =taskSnapshot.getDownloadUrl();
//                       String DOWNLOAD_URL = downloadUrl.getPath();
//                       Log.v("DOWNLOAD URL", DOWNLOAD_URL);
//                       Toast.makeText(Db.this, DOWNLOAD_URL, Toast.LENGTH_SHORT).show();
//                   }
//               });

                mDatabase1.child("Places").setValue(place);
//               mDatabase1.addValueEventListener(new ValueEventListener() {
//                   @Override
//                   public void onDataChange(DataSnapshot dataSnapshot) {
//                    // Place p =   dataSnapshot.child("count").getValue(Place.class);
//                       Toast.makeText(Db.this, ""+ dataSnapshot.child("count").getValue(), Toast.LENGTH_SHORT).show();
//
//                   }
//
//                   @Override
//                   public void onCancelled(DatabaseError databaseError) {
//
//                   }
//               });

//               startActivityForResult(new Intent(""), 1);

            }
        });
        //remove child in db
        // mDatabase1.removeValue();

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(resultCode==RESULT_OK){
//            if(requestCode==100){
//                Uri selectedImageUri = data.getData();
//                if (null != selectedImageUri) {
//                    // Get the path from the Uri
//                    String path = getPathFromURI(selectedImageUri);
//                    Log.i("IMAGE PATH TAG", "Image Path : " + path);
//                    // Set the image in ImageView
//                    myImage.setImageURI(selectedImageUri);
//
//                }
//            }
//        }
//
//    }
//    private String getPathFromURI(Uri contentUri) {
//        String res = null;
//        String[] proj = {MediaStore.Images.Media.DATA};
//        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
//        if (cursor.moveToFirst()) {
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            res = cursor.getString(column_index);
//        }
//        cursor.close();
//        return res;
//    }
    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        bm = (Bitmap) data.getExtras().get("data");
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        StorageReference imageRef=storageRef.ge
//        imageRef.child("test.jpg").putBytes(baos.toByteArray()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                if (task.isSuccessful()){
//                    imageURL = task.getResult().getDownloadUrl();
//                    imageURL.toString();
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
//    }
}
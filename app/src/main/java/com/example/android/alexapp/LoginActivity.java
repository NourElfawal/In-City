package com.example.android.alexapp;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener  {

    private Button buttonSignIn;
//    private EditText editTextEmail;
//    private EditText editTextPassword;
    private TextView textViewSignup;
    private  TextView textViewRest;

    //firebase auth object
    private FirebaseAuth firebaseAuth;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static String TAG = "AUTH";

    private Context context;


    private GoogleApiClient mGoogleApiClient;
    private static int RC_SIGN_IN = 0;



    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirm;
    private Button buttonSignup;
    private TextView textViewSignin;

    private ImageView  imgProfileGoogle;

    private ProgressDialog mProgressDialog;
    ///reg
    Button btn;
    private static final int CAMERA_REQUEST =123 ;
    ImageView imageView;
    ////
    private TextView info;
    private LoginButton mFacebookSignInButton ;
    private CallbackManager callbackManager;
    public String idFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

//        //if the objects getcurrentuser method is not null
//        //means user is already logged in
//        if (firebaseAuth.getCurrentUser() != null) {
//            //close this activity
//            finish();
//            //opening profile activity
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//        }


        buttonSignIn=(Button)findViewById(R.id.buttonSignin);
        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        textViewSignup=(TextView)findViewById(R.id.textViewSignUp);
        textViewRest=(TextView)findViewById(R.id.textViewReset);


        buttonSignIn.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
        textViewRest.setOnClickListener(this);


        // Configure Google Sign In
        findViewById(R.id.sign_in_google).setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();





        // Initialize Facebook Login button
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button_face);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
                //////////
                final Profile profile = Profile.getCurrentProfile();
                String idFacebook=profile.getId();
                String personName=profile.getName();

                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("idface",idFacebook);
                intent.putExtra("Name",personName);
                startActivity(intent);
                //////////////////////////


            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });


        ///////////////////////


        /////////////////////////////////////////////////////////
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("r.auth", "onAuthStateChanged:signed_in:" + user.getEmail());
                    finish();

                    String personId=user.getUid();
                    String personEmail=user.getEmail();
                    String personName=user.getDisplayName();
                    Uri personPhoto = user.getPhotoUrl();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("Email",personEmail);
                    intent.putExtra("Name",personName);
                    intent.putExtra("id",personId);

                    startActivity(intent);

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };



    }

    public void btnClick(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        showProgressDialog();


        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d("Nour", "signInWithCredential:onComplete:" + task.isSuccessful());


                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("Nour", "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        else {
//                            Toast.makeText(RegistrationActivity.this, "Authentication successful.",
//                                    Toast.LENGTH_SHORT).show();

//                            Intent intent = new Intent(RegistrationActivity.this,MainActivity.class);
//                            intent.putExtra("Id",personId);
//                            intent.putExtra("Email",nameEmail);
//                            startActivity(intent);

//                            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
//                            finish();
                            hideProgressDialog();
                        }                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }

        /////fb/////
        callbackManager.onActivityResult(requestCode, resultCode, data);
        ////////
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                finish();
                String personName = account.getDisplayName();
                String googleImgUrl=account.getPhotoUrl().toString();

                Log.e(TAG, "Name: " + personName
                        + ", Image: " + googleImgUrl);


//                Glide.with(getApplicationContext()).load(googleImgUrl)
//                        .thumbnail(0.5f)
//                        .crossFade()
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .into(imgProfileGoogle);


                String nameEmail = account.getEmail();
                String personId = account.getId();
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("Id",personId);
                intent.putExtra("Name",personName);
                intent.putExtra("GoogleImg",googleImgUrl);
                startActivity(intent);

//                Uri personPhoto = account.getPhotoUrl();

//                Toast.makeText(RegistrationActivity.this,personId,
//                        Toast.LENGTH_SHORT).show();
//
//
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));




            } else {
                // Google Sign In failed, update UI appropriately
                Log.d(TAG, "Google Login Failed");
            }
        }
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    ///////////sign in by google////////////////
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    //method for user login
    private void userLogin() {
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       // mProgressDialog.show();
                        //if the task is successfull
                        if (task.isSuccessful()) {
                            //start the profile activity
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            finish();
//                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            String personId=user.getUid();
                            String personEmail=user.getEmail();
                            String personName=user.getDisplayName();
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra("Id",personId);
                            intent.putExtra("Email",personEmail);
                            intent.putExtra("Name",personName);

                            startActivity(intent);
                        }
                    }
                });



    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    /////////////////////google///////////////////////
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSignin:
                userLogin();
                break;
            case R.id.textViewSignUp:
                startActivity(new Intent(this,RegistrationActivity.class));
                finish();
                break;
            case R.id.textViewReset:
                startActivity(new Intent(this,ResetPasswordActivity.class));
                break;
            case R.id.sign_in_google:
                signIn();
                break;


        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
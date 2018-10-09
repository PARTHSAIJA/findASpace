package com.findaspace.findaspace.main.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.findaspace.findaspace.base.FindASpaceApplication;
import com.findaspace.findaspace.dialog.LoadingDialog;
import com.findaspace.findaspace.main.room.RoomActivity;
import com.findaspace.findaspace.app.R;
import com.findaspace.findaspace.main.search.SearchActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static EditText emailTxt;
    private static EditText passwordTxt;
    private static Button login_button;
    private static final String TAG = "LoginPage";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    /**
     *
     */
    private LoadingDialog mLoginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        findViewById(R.id.btnLogin).setOnClickListener(this);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference refDb = database.getReference();
//        refDb.child("Room").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.d(TAG, "Starting search");
//                if (dataSnapshot.exists()) {
//                    int i = 0;
//                    for(DataSnapshot d : dataSnapshot.getChildren()) {
//                        Log.d(TAG, "room: " + d.getKey());
//                        System.out.println("room: " + d.getKey().toString());
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.d(TAG, "->onCancelled");
//                System.out.println("room: cancelled");
//            }
//        });


        emailTxt = (EditText) findViewById(R.id.editText3);
        passwordTxt = (EditText) findViewById(R.id.editText4);
        login_button = (Button) findViewById(R.id.btnLogin);

        //Get a reference to the Firebase auth object
        mAuth = FirebaseAuth.getInstance();

        //Attach a new AuthListener to detect sign in and out
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "Signed in: " + user.getUid());
                } else {
                    Log.d(TAG, "Currently Signed Out");
                }
            }
        };

        updateStatus();
    }

    private void signUserInWithEmail() {
        final String email = emailTxt.getText().toString();
        String password = passwordTxt.getText().toString();

        if (email.length() > 0 && password.length() > 0) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent;
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                if (email.contains(getString(R.string.login_admin))) {
                                    //
                                    FindASpaceApplication.getInstance().mUserFlag = FindASpaceApplication.UserFlag.ADMIN;
                                    intent = new Intent(LoginActivity.this, RoomActivity.class);
                                } else if (email.contains("@security")) {
                                    //
                                    intent = new Intent(LoginActivity.this, RoomActivity.class);
                                    FindASpaceApplication.getInstance().mUserFlag = FindASpaceApplication.UserFlag.SECURITY;
                                } else {
                                    //
                                    intent = new Intent(LoginActivity.this, SearchActivity.class);
                                    FindASpaceApplication.getInstance().mUserFlag = FindASpaceApplication.UserFlag.USER;
                                }
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Exception exception = task.getException();
                                Log.w(TAG, "signInWithEmail:failure", exception);
                                if (exception != null) {
                                    Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            updateStatus();
                            if (mLoginDialog != null) {
                                mLoginDialog.dismiss();
                            }
                        }
                    });
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        //Add the AuthListener
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        //Remove the AuthListener
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void updateStatus() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            Toast.makeText(LoginActivity.this, "Signed in: " + user.getEmail(), Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(LoginActivity.this, "Signed out" , Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                signUserInWithEmail();
                showLoginDialog();
                break;
        }
    }

    /**
     *
     */
    private void showLoginDialog() {
        if (mLoginDialog == null) {
            mLoginDialog = new LoadingDialog(this);
            //
            mLoginDialog.setText(getString(R.string.logging));
        }
        mLoginDialog.show();
    }

    //WRITE TO DB
    /*
     * Retrieve an instance of your database using getInstance()
     * and reference the location you want to write to.
     *
     * You can save a range of data types to the database this way, including Java objects.
     * When you save an object the responses from any getters will be saved as children of
     * this location.
     */
    // Write a message to the database
    //FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference myRef = database.getReference("message");

    //myRef.setValue("Hello, World!");


}
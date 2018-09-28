package com.findaspace.findaspace.readDB;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public interface OnGetDataListener {

    //Interfaces are used for method declarations

    //this is for callbacks

    void onSuccess(DataSnapshot dataSnapshot);
    void onStart();
    void onFailure();

}

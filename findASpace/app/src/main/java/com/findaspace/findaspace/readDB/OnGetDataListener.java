package com.findaspace.findaspace.readDB;

import com.google.firebase.database.DataSnapshot;

public interface OnGetDataListener {

    //Interfaces are used for method declarations these methods are used for callbacks
    void onSuccess(DataSnapshot dataSnapshot);
    void onStart();
    void onFailure();

}

package com.findaspace.findaspace.readDB;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

public class UTSRooms{

    private static final String TAG = "SearchActivity";

    public String[] getAllRooms(){

        final LinkedList<String> rooms = new LinkedList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refDb = database.getReference();


        readData(refDb.child("room"), new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int i = 0;
                    for(DataSnapshot d : dataSnapshot.getChildren()) {
                        rooms.add(d.getKey());
                        i++;
                    }
                }
            }
            @Override
            public void onStart() {
                //when starting
                Log.d(TAG, "onStart-Started");
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "onFailure-Failed");
            }
        });

        return null;
    }

    public void readData(DatabaseReference ref, final OnGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }
            
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailure();
            }
        });

    }
}

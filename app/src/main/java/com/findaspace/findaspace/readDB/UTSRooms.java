package com.findaspace.findaspace.readDB;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

public class UTSRooms {

    private FirebaseDatabase database;
    private DatabaseReference refDb;

    public String[] getAllRooms(){

        final LinkedList<String> rooms = new LinkedList<>();

        database = FirebaseDatabase.getInstance();
        refDb = database.getReference();

        refDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int i = 0;
                    for(DataSnapshot d : dataSnapshot.getChildren()) {
                        rooms.add(d.getKey());
                        i++;
                    }
                }
            }//onDataChange

            @Override
            public void onCancelled(DatabaseError error) {

            }//onCancelled
        });


        return null;
    }
}

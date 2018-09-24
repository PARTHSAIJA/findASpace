package com.findaspace.findaspace.readDB;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UTSRooms {

    private FirebaseDatabase database;
    private DatabaseReference refDb;

    public String[] getAllRooms(){

        database = FirebaseDatabase.getInstance();
        refDb = database.getReference();

        refDb.
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int i = 0;
                    for(DataSnapshot d : dataSnapshot.getChildren()) {
                        name[i] = d.getKey();
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

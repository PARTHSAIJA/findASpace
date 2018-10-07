package com.findaspace.findaspace.readDB;

import android.support.annotation.NonNull;
import android.util.Log;

import com.findaspace.findaspace.main.member.MemberActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

public class UTSRooms{

    private static final String TAG = "SearchActivity";
    private MemberActivity activity;

    /**
     *
     * @param activity
     */
    public UTSRooms(MemberActivity activity) {
        this.activity = activity;
    }

    /**
     *
     * @throws InterruptedException
     */
    public void getAllRooms() throws InterruptedException {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refDb = database.getReference();

        final LinkedList<String> rooms = new LinkedList<>();

        readData(refDb.child("Room"), new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Starting search");
                if (dataSnapshot.exists()) {
                    int i = 0;
                    for(DataSnapshot d : dataSnapshot.getChildren()) {
                        Log.d(TAG, "room:" + d.getKey());
                        rooms.add(d.getKey());
                        i++;
                    }
                }
                String[] formattedRooms = FormatRooms(rooms);
                activity.setRoomsUTS(formattedRooms);
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
    }

    /**
     * @brief This method applies the UTS formatting to the rooms (CB1105404 -> CB11.05.404)
     * @param dbRoomsList
     * @return
     */
    private String[] FormatRooms(LinkedList<String> dbRoomsList) {

        String[] formattedRooms = new String[dbRoomsList.size()];

        for(int i=0; i < dbRoomsList.size(); i++) {
            //Get just the building number
            String roomBuilding = ((dbRoomsList.get(i)).substring(2)).substring(0, Math.min((dbRoomsList.get(i)).substring(2).length(), 2));
            //Get just the level number
            String roomLvl = ((dbRoomsList.get(i)).substring(4)).substring(0, Math.min((dbRoomsList.get(i)).substring(4).length(), 2));
            //Get just the room
            String roomNo = (dbRoomsList.get(i)).substring(6);
            //Add UTS room formatting
            formattedRooms[i] = "CB" + roomBuilding + "." + roomLvl + "." + roomNo;
            Log.w(TAG, "CB" + roomBuilding + "." + roomLvl + "." + roomNo);
        }

        return formattedRooms;
    }

    /**
     *
     * @param ref
     * @param listener
     */
    public void readData(DatabaseReference ref, final OnGetDataListener listener) {
        listener.onStart();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "->onDataChange");
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "->onCancelled");
                listener.onFailure();
            }
        });
    }
}

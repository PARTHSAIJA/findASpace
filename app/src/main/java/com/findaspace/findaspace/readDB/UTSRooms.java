package com.findaspace.findaspace.readDB;

import android.support.annotation.NonNull;
import android.util.Log;

import com.findaspace.findaspace.main.search.SearchActivity;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class UTSRooms {

    private static final String TAG = "SearchActivity";


    private SearchActivity activity;

    public UTSRooms(SearchActivity activity) {
        this.activity = activity;
    }

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
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        Log.d(TAG, "room:" + d.getKey());
                        rooms.add(d.getKey().toString());
                        i++;
                    }
                }
                String[] formattedRooms = FormatRooms(rooms);
                activity.setBuilding(formattedRooms);
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

    private String[] FormatRooms(LinkedList<String> dbRoomsList) {

        String[] formattedRooms = new String[dbRoomsList.size()];

        for (int i = 0; i < dbRoomsList.size(); i++) {
            if (dbRoomsList.get(i).length() < 6) {
                continue;
            }
            String roomBuilding = ((dbRoomsList.get(i)).substring(2)).substring(0, Math.min((dbRoomsList.get(i)).substring(2).length(), 2));
            String roomLvl = ((dbRoomsList.get(i)).substring(4)).substring(0, Math.min((dbRoomsList.get(i)).substring(4).length(), 2));
            String roomNo = (dbRoomsList.get(i)).substring(6);
            formattedRooms[i] = "CB" + roomBuilding + "." + roomLvl + "." + roomNo;
            Log.w(TAG, "CB" + roomBuilding + "." + roomLvl + "." + roomNo);
        }

        return formattedRooms;
    }


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

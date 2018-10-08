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
    private String buildingNumber;
    public LinkedList<RoomRecord> roomRecord;

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    /**
     *
     * @param activity
     */
    public UTSRooms(MemberActivity activity) {
        this.activity = activity;
    }

    /**
     *
     * @param buildingNo
     */
    public void getSelectedRoom(String buildingNo) {
        setBuildingNumber(buildingNo);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refDb = database.getReference();

        final LinkedList<String> rooms = new LinkedList<>();

        //CB1105404
        readData(refDb.child("Room"), new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Starting search");
                if (dataSnapshot.exists()) {
                    for(DataSnapshot d : dataSnapshot.getChildren()) {
                        if(d.getKey().contains("CB" + getBuildingNumber())){
                            Log.d(TAG, "room:" + d.getKey());
                            String roomStr = d.getKey();
                            rooms.add(roomStr);
                            //Get just the building number
                            String roomBuilding = ((roomStr).substring(2)).substring(0, Math.min((roomStr).substring(2).length(), 2));
                            //Get just the level number
                            String roomLvl = ((roomStr).substring(4)).substring(0, Math.min((roomStr).substring(4).length(), 2));
                            //Get just the room
                            String roomNo = (roomStr).substring(6);

                            for(DataSnapshot childOfKey : d.getChildren()){
                                RoomRecord roomDetails = new RoomRecord
                                        (
                                                roomBuilding,
                                                roomLvl,
                                                roomNo,
                                                Boolean.valueOf(childOfKey.child("Blocked").getValue().toString()),
                                                childOfKey.child("CloseTime").getValue().toString(),
                                                Integer.valueOf(childOfKey.child("MaxCap").getValue().toString()),
                                                childOfKey.child("OpenTime").getValue().toString(),
                                                childOfKey.child("UnitNo").getValue().toString()
                                        );
                                roomRecord.add(roomDetails);
                            }
                        }
                    }
                }
                //String[] formattedRooms = FormatRooms(rooms);
                activity.setRoomsUTS(roomRecord);
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
//    private String[] FormatRooms(LinkedList<String> dbRoomsList) {
//
//        String[] formattedRooms = new String[dbRoomsList.size()];
//
//        for(int i=0; i < dbRoomsList.size(); i++) {
//            //Get just the building number
//            String roomBuilding = ((dbRoomsList.get(i)).substring(2)).substring(0, Math.min((dbRoomsList.get(i)).substring(2).length(), 2));
//            //Get just the level number
//            String roomLvl = ((dbRoomsList.get(i)).substring(4)).substring(0, Math.min((dbRoomsList.get(i)).substring(4).length(), 2));
//            //Get just the room
//            String roomNo = (dbRoomsList.get(i)).substring(6);
//            //Add UTS room formatting
//            formattedRooms[i] = "CB" + roomBuilding + "." + roomLvl + "." + roomNo;
//            Log.w(TAG, "CB" + roomBuilding + "." + roomLvl + "." + roomNo);
//        }
//
//        return formattedRooms;
//    }

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

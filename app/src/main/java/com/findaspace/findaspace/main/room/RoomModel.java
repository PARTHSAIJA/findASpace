package com.findaspace.findaspace.main.room;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.findaspace.findaspace.base.FindASpaceApplication;
import com.findaspace.findaspace.entity.Room;
import com.findaspace.findaspace.entity.RoomBean;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RoomModel {

    private static final String TAG = "RoomModel";
    private Gson mGson;
    /**
     * Room List class
     */
    private List<RoomBean> mRooms = new ArrayList<>();

    public RoomModel() {
        if (mGson == null) {
            mGson = new Gson();
        }
    }

    public void getRooms(final OnGetRoomsCallback onGetRoomsCallback) {
        mRooms.clear();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("Room");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        Object value = d.getValue();
                        if (value != null) {
                            Room room = mGson.fromJson(value.toString(), Room.class);
                            mRooms.add(new RoomBean(d.getKey(), room.getUnitNo(), room.getMaxCap(), room.getOpenTime(), room.getCloseTime(), room.isBlocked()));
                        }
                    }
                    onGetRoomsCallback.onGetRoomsSuccess(mRooms);
                } else {
                    onGetRoomsCallback.onGetRoomsFail();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FindASpaceApplication.getInstance(), "Cancel get Room", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getAvailableRooms(final OnGetAvailableRoomsCallBack onGetAvailableRoomsCallBack) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("Room");
        mRooms.clear();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        Object value = d.getValue();
                        if (value != null) {
                            Room room = mGson.fromJson(value.toString(), Room.class);
                            if (!room.isBlocked()) {
                                // only add AvailableRoom
                                mRooms.add(new RoomBean(d.getKey(), room.getUnitNo(), room.getMaxCap(), room.getOpenTime(), room.getCloseTime(), room.isBlocked()));
                            }
                        }
                    }
                    onGetAvailableRoomsCallBack.onGetAvailableRoomsSuccess(mRooms);
                } else {
                    onGetAvailableRoomsCallBack.onGetAvailableRoomsFail();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FindASpaceApplication.getInstance(), "cancel get Room", Toast.LENGTH_LONG).show();
            }
        });
    }

    public interface OnGetRoomsCallback {
        void onGetRoomsSuccess(List<RoomBean> rooms);

        void onGetRoomsFail();
    }

    public interface OnGetAvailableRoomsCallBack {
        void onGetAvailableRoomsSuccess(List<RoomBean> availableRooms);

        void onGetAvailableRoomsFail();
    }
}

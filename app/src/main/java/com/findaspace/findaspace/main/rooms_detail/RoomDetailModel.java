package com.findaspace.findaspace.main.rooms_detail;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.findaspace.findaspace.base.FindASpaceApplication;
import com.findaspace.findaspace.entity.Room;
import com.findaspace.findaspace.entity.RoomBean;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RoomDetailModel {

    public void modifyRoomData(RoomBean mRoom, boolean blocked, final OnModifyCompleteCallback onModifyCompleteCallback) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Room");
        String roomKey = databaseReference.child("Room").child(mRoom.getRoomName()).push().getKey();
        Room room = new Room(blocked, mRoom.getCloseTime(), mRoom.getMaxCap(), mRoom.getOpenTime(), mRoom.getUnitNo());
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> roomMap = room.toMap();
        childUpdates.put("/Room/" + roomKey, roomMap);
        Task<Void> task = databaseReference.updateChildren(childUpdates);
        task.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                onModifyCompleteCallback.onModifyComplete();
            }
        });
    }

    public interface OnModifyCompleteCallback {
        void onModifyComplete();
    }
}

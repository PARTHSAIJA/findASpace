package com.findaspace.findaspace.feitresearchapi;

import com.findaspace.findaspace.main.member.MemberActivity;
import com.findaspace.findaspace.readDB.RoomRecord;

import java.util.LinkedList;

public class CallAPIPeopleCount implements AsyncCallTaskResponse{

    private MemberActivity activity;
    private LinkedList<RoomRecord> roomsUTS;

    /**
     *
     * @param activity
     */
    public CallAPIPeopleCount(MemberActivity activity) {
        this.activity = activity;
    }

    /**
     * @brief Create API request for each room to determine how many in the room
     * @param roomsUTS
     */
    public void callAPIPeopleCount(LinkedList<RoomRecord> roomsUTS){
        this.roomsUTS = roomsUTS;
        new AsyncCallTask(this).execute();

    }

    //this override the implemented method from AsyncResponse
    @Override
    public void processFinish(LinkedList<PeopleCountRecord> peopleCountRecord){
        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.
        activity.setPeopleCount(peopleCountRecord);
    }

    @Override
    public LinkedList<RoomRecord> getPeopleCount() {
        return roomsUTS;
    }
}

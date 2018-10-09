package com.findaspace.findaspace.feitresearchapi;

import com.findaspace.findaspace.readDB.RoomRecord;

import java.util.LinkedList;

public interface AsyncCallTaskResponse {
    void processFinish(LinkedList<PeopleCountRecord> peopleCountRecord);
    LinkedList<RoomRecord> getRoomsUTS();
}

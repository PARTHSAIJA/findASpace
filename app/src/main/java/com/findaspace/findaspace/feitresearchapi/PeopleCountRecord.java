package com.findaspace.findaspace.feitresearchapi;

import java.util.Date;

public class PeopleCountRecord {

    public String Room;
    public String DateTime;
    public Integer PeopleCount;

    public PeopleCountRecord(String room, String dateTime, Integer peopleCount) {
        Room = room;
        DateTime = dateTime;
        PeopleCount = peopleCount;
    }

    public String getRoom() {
        return Room;
    }

    public void setRoom(String room) {
        Room = room;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public Integer getPeopleCount() {
        return PeopleCount;
    }

    public void setPeopleCount(Integer peopleCount) {
        PeopleCount = peopleCount;
    }
}

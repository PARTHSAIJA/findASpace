package com.findaspace.findaspace.main.member;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.findaspace.findaspace.app.R;
import com.findaspace.findaspace.feitresearchapi.CallAPIPeopleCount;
import com.findaspace.findaspace.feitresearchapi.PeopleCountRecord;
import com.findaspace.findaspace.readDB.RoomRecord;
import com.findaspace.findaspace.readDB.UTSRooms;

import java.util.LinkedList;

public class MemberActivity extends Activity
{
    // Array of strings...
    ListView simpleList;
    String countryList[] = {"2.101 - 10 SEATS LEFT","2.102 - 12 SEATS LEFT","2.103 - 18 SEATS LEFT","2.104 - 22 SEATS LEFT","2.105 - 15 SEATS LEFT"};
    public LinkedList<RoomRecord> roomsUTS;
    public LinkedList<PeopleCountRecord> peopleCount;
    public String building;
    public int numOfPeople;

    public String getBuilding() {
        return this.building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }

    public void setNumOfPeople(int numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    public LinkedList<RoomRecord> getRoomsUTS() {
        return roomsUTS;
    }

    public void setRoomsUTS(LinkedList<RoomRecord> roomsUTS) {
        this.roomsUTS = roomsUTS;
    }

    public LinkedList<PeopleCountRecord> getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(LinkedList<PeopleCountRecord> peopleCount) {
        this.peopleCount = peopleCount;
    }


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get the values passed from SeachActivity.java
        Intent intent = getIntent();
        setBuilding(intent.getStringExtra("building"));
        setNumOfPeople(intent.getIntExtra("numOfPeople", 0));


        setContentView(R.layout.student_view);
        simpleList = (ListView)findViewById(R.id.simpleListView);

        callAsyncGetRooms();
        callAsyncGetPeopleCount();

        //while(roomsUTS.length > 0);
        //Filter out the rooms where there is not enough space, blocked or closed
        filterRoomsNotSuitable();

        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView, countryList);
        //simpleList.setAdapter(arrayAdapter);
    }

    /**
     * @brief The UTSRooms will call this method when the Async method is completed
     */
    public void callAsyncGetPeopleCount() {
        CallAPIPeopleCount callAPI = new CallAPIPeopleCount(this);
        callAPI.callAPIPeopleCount(getRoomsUTS());
    }

    /**
     * @brief This gets all the rooms from Firebase DB
     */
    private void callAsyncGetRooms() {
        UTSRooms dbRooms = new UTSRooms(this);
        dbRooms.getSelectedRoom(getBuilding());
    }

    /**
     * @brief
     */
    private void filterRoomsNotSuitable() {
        //TODO: Filter out the rooms where there is not enough space, blocked or closed
    }

    /**
     * @brief
     */
    private void callAPIPeopleCount() {
        //TODO: Create API request for each room to determine how many in the room
    }

    /**
     * @brief
     */
    private void sortRoomsForSelectedBuilding() {
        //TODO: Go through and get all the rooms associated with the building number the user selected. In this fill the RoomRecord.java
    }
}

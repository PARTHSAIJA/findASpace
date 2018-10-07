package com.findaspace.findaspace.main.member;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.findaspace.findaspace.app.R;
import com.findaspace.findaspace.feitresearchapi.CallAPIPeopleCount;
import com.findaspace.findaspace.readDB.UTSRooms;

public class MemberActivity extends Activity
{
    // Array of strings...
    ListView simpleList;
    String countryList[] = {"2.101 - 10 SEATS LEFT","2.102 - 12 SEATS LEFT","2.103 - 18 SEATS LEFT","2.104 - 22 SEATS LEFT","2.105 - 15 SEATS LEFT"};
    public String[] roomsUTS;
    public String building;
    public int numOfPeople;

    public String getBuilding() {
        return building;
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

    public String[] getRoomsUTS() {
        return roomsUTS;
    }

    public void setRoomsUTS(String[] roomsUTS) {
        this.roomsUTS = roomsUTS;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      setContentView(R.layout.student_view);
        simpleList = (ListView)findViewById(R.id.simpleListView);

        //Get all the rooms
        CallAPIPeopleCount callAPI = new CallAPIPeopleCount();
        callAPI.CallAPIPeopleCount();
        //while(roomsUTS.length > 0);
        //Go through and get all the rooms associated with the building number the user selected. In this fill the RoomRecord.java
        sortRoomsForSelectedBuilding();
        //Create API request for each room to determine how many in the room
        callAPIPeopleCount();
        //Filter out the rooms where there is not enough space, blocked or closed
        filterRoomsNotSuitable();

        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView, countryList);
        //simpleList.setAdapter(arrayAdapter);
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



    /**
     * @brief
     */
    private void callAsyncGetRooms() {
        UTSRooms dbRooms = new UTSRooms(this);
        try {
            dbRooms.getAllRooms();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

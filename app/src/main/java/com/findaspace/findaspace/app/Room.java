package com.findaspace.findaspace.app;

public class Room {

    public String BuildingNo;
    public String LevelNo;
    public Integer RoomNo;
    public Integer NoOfSeatsAvaliable;
    public Integer NoOfPeopleInSpace;

    public Room(){
        //Default constructor requreied for calls to DataSnapshot.getValue(User.class)
    }

    public Room(String buildingNo, String levelNo, Integer roomNo,
                Integer noOfSeatsAvaliable, Integer noOfPeopleInSpace){
        this.BuildingNo = buildingNo;
        this.LevelNo = levelNo;
        this.RoomNo = roomNo;
        this.NoOfSeatsAvaliable = noOfSeatsAvaliable;
        this.NoOfPeopleInSpace = noOfPeopleInSpace;
    }
}

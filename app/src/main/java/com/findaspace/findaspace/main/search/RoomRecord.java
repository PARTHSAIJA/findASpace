package com.findaspace.findaspace.main.search;

public class RoomRecord {

    public String BuildingNo;
    public String LevelNo;
    public Integer RoomNo;
    public Integer NoOfSeatsAvaliable;
    public Integer NoOfPeopleInSpace;

    public RoomRecord(String buildingNo, String levelNo, Integer roomNo, Integer noOfSeatsAvaliable, Integer noOfPeopleInSpace){
        this.BuildingNo = buildingNo;
        this.LevelNo = levelNo;
        this.RoomNo = roomNo;
        this.NoOfSeatsAvaliable = noOfSeatsAvaliable;
        this.NoOfPeopleInSpace = noOfPeopleInSpace;
    }

    public String getBuildingNo() {
        return BuildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        BuildingNo = buildingNo;
    }

    public String getLevelNo() {
        return LevelNo;
    }

    public void setLevelNo(String levelNo) {
        LevelNo = levelNo;
    }

    public Integer getRoomNo() {
        return RoomNo;
    }

    public void setRoomNo(Integer roomNo) {
        RoomNo = roomNo;
    }

    public Integer getNoOfSeatsAvaliable() {
        return NoOfSeatsAvaliable;
    }

    public void setNoOfSeatsAvaliable(Integer noOfSeatsAvaliable) {
        NoOfSeatsAvaliable = noOfSeatsAvaliable;
    }

    public Integer getNoOfPeopleInSpace() {
        return NoOfPeopleInSpace;
    }

    public void setNoOfPeopleInSpace(Integer noOfPeopleInSpace) {
        NoOfPeopleInSpace = noOfPeopleInSpace;
    }
}

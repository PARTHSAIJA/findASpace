package com.findaspace.findaspace.readDB;

public class RoomRecord {

    public String BuildingNo;
    public String LevelNo;
    public String RoomNo;
    public Boolean RoomBlocked;
    public String CloseTime;
    public Integer MaxCap;
    public String OpenTime;
    public String UnitNo;

    public RoomRecord(String buildingNo, String levelNo, String roomNo, Boolean roomBlocked, String closeTime, Integer maxCap, String openTime, String unitNo) {
        BuildingNo = buildingNo;
        LevelNo = levelNo;
        RoomNo = roomNo;
        RoomBlocked = roomBlocked;
        CloseTime = closeTime;
        MaxCap = maxCap;
        OpenTime = openTime;
        UnitNo = unitNo;
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

    public String getRoomNo() {
        return RoomNo;
    }

    public void setRoomNo(String roomNo) {
        RoomNo = roomNo;
    }

    public Boolean getRoomBlocked() {
        return RoomBlocked;
    }

    public void setRoomBlocked(Boolean roomBlocked) {
        RoomBlocked = roomBlocked;
    }

    public String getCloseTime() {
        return CloseTime;
    }

    public void setCloseTime(String closeTime) {
        CloseTime = closeTime;
    }

    public Integer getMaxCap() {
        return MaxCap;
    }

    public void setMaxCap(Integer maxCap) {
        MaxCap = maxCap;
    }

    public String getOpenTime() {
        return OpenTime;
    }

    public void setOpenTime(String openTime) {
        OpenTime = openTime;
    }

    public String getUnitNo() {
        return UnitNo;
    }

    public void setUnitNo(String unitNo) {
        UnitNo = unitNo;
    }
}

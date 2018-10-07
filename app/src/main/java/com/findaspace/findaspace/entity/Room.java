package com.findaspace.findaspace.entity;

import java.util.HashMap;
import java.util.Map;

public class Room {
    /**
     * Blocked : false
     * CloseTime : 2300
     * MaxCap : 50
     * OpenTime : 0500
     * UnitNo : PCB1.04
     */

    private boolean Blocked;
    private String CloseTime;
    private int MaxCap;
    private String OpenTime;
    private String UnitNo;

    public Room() {
    }

    public Room(boolean blocked, String closeTime, int maxCap, String openTime, String unitNo) {
        Blocked = blocked;
        CloseTime = closeTime;
        MaxCap = maxCap;
        OpenTime = openTime;
        UnitNo = unitNo;
    }

    public boolean isBlocked() {
        return Blocked;
    }

    public void setBlocked(boolean Blocked) {
        this.Blocked = Blocked;
    }

    public String getCloseTime() {
        return CloseTime;
    }

    public void setCloseTime(String CloseTime) {
        this.CloseTime = CloseTime;
    }

    public int getMaxCap() {
        return MaxCap;
    }

    public void setMaxCap(int MaxCap) {
        this.MaxCap = MaxCap;
    }

    public String getOpenTime() {
        return OpenTime;
    }

    public void setOpenTime(String OpenTime) {
        this.OpenTime = OpenTime;
    }

    public String getUnitNo() {
        return UnitNo;
    }

    public void setUnitNo(String UnitNo) {
        this.UnitNo = UnitNo;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Blocked", Blocked);
        result.put("CloseTime", CloseTime);
        result.put("MaxCap", MaxCap);
        result.put("OpenTime", OpenTime);
        result.put("UnitNo", UnitNo);
        return result;
    }
}

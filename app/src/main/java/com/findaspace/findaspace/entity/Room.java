package com.findaspace.findaspace.entity;

/**
 * get room detail from firebase
 */
public class Room {
    /**
     * blocked : false
     * closeTime : 2300
     * maxCap : 50
     * openTime : 0500
     * unitNo : PCB1.04
     */
    private boolean blocked;
    private String closeTime;
    private int maxCap;
    private String openTime;
    private String unitNo;

    public Room() {
    }

    public Room(boolean blocked, String closeTime, int maxCap, String openTime, String unitNo) {
        this.blocked = blocked;
        this.closeTime = closeTime;
        this.maxCap = maxCap;
        this.openTime = openTime;
        this.unitNo = unitNo;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean Blocked) {
        this.blocked = Blocked;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String CloseTime) {
        this.closeTime = CloseTime;
    }

    public int getMaxCap() {
        return maxCap;
    }

    public void setMaxCap(int MaxCap) {
        this.maxCap = MaxCap;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String OpenTime) {
        this.openTime = OpenTime;
    }

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String UnitNo) {
        this.unitNo = UnitNo;
    }

    @Override
    public String toString() {
        return "Room{" +
                "blocked=" + blocked +
                ", closeTime='" + closeTime + '\'' +
                ", maxCap=" + maxCap +
                ", openTime='" + openTime + '\'' +
                ", unitNo='" + unitNo + '\'' +
                '}';
    }
}

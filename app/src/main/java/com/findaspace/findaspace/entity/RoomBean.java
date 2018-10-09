package com.findaspace.findaspace.entity;


import android.os.Parcel;
import android.os.Parcelable;

/**
 */
public class RoomBean implements Parcelable {
    private String roomName;
    private String unitNo;
    private int maxCap;
    private String openTime;
    private String closeTime;
    private boolean blocked;
    private int personNumber;

    public RoomBean() {
    }

    public RoomBean(String roomName, String unitNo, int maxCap, String openTime, String closeTime, boolean blocked) {
        this.roomName = roomName;
        this.unitNo = unitNo;
        this.maxCap = maxCap;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.blocked = blocked;
    }

    protected RoomBean(Parcel in) {
        roomName = in.readString();
        unitNo = in.readString();
        maxCap = in.readInt();
        openTime = in.readString();
        closeTime = in.readString();
        blocked = in.readByte() != 0;
        personNumber = in.readInt();
    }

    public static final Creator<RoomBean> CREATOR = new Creator<RoomBean>() {
        @Override
        public RoomBean createFromParcel(Parcel in) {
            return new RoomBean(in);
        }

        @Override
        public RoomBean[] newArray(int size) {
            return new RoomBean[size];
        }
    };

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    public int getMaxCap() {
        return maxCap;
    }

    public void setMaxCap(int maxCap) {
        this.maxCap = maxCap;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public int getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(int personNumber) {
        this.personNumber = personNumber;
    }

    @Override
    public String toString() {
        return "RoomBean{" +
                "roomName='" + roomName + '\'' +
                ", unitNo='" + unitNo + '\'' +
                ", maxCap=" + maxCap +
                ", openTime='" + openTime + '\'' +
                ", closeTime='" + closeTime + '\'' +
                ", blocked=" + blocked +
                ", personNumber=" + personNumber +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(roomName);
        dest.writeString(unitNo);
        dest.writeInt(maxCap);
        dest.writeString(openTime);
        dest.writeString(closeTime);
        dest.writeByte((byte) (blocked ? 1 : 0));
        dest.writeInt(personNumber);
    }
}

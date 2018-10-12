package com.findaspace.findaspace.main.room;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.findaspace.findaspace.Constants;
import com.findaspace.findaspace.base.FindASpaceApplication;
import com.findaspace.findaspace.entity.Room;
import com.findaspace.findaspace.entity.RoomBean;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * get room data
 */
public class RoomModel {

    private static final String TAG = "RoomModel";
    private Gson mGson;
    /**
     * room list
     */
    private List<RoomBean> mRooms = new ArrayList<>();
    private SimpleDateFormat mSimpleDateFormat;
    private Calendar mCalendar;
    private Date mCurDate;
    private SimpleDateFormat mDateFormat;
    private LoadPeopleCountThread mLoadPeopleCountThread;
    private OnGetAvailableRoomsCallBack mOnGetAvailableRoomsCallBack;
    private OnGetRoomsCallback mOnGetRoomsCallback;
    private Random mRandom;
    private static final String AVAILABLE_ROOMS = "availableRooms";

    public RoomModel() {
        if (mGson == null) {
            mGson = new Gson();
        }
    }

    /**
     * get all room data
     *
     * @param onGetRoomsCallback  after get all room data call back， return， get successfully or failed get data
     */
    public void getRooms(final OnGetRoomsCallback onGetRoomsCallback) {
        mOnGetRoomsCallback = onGetRoomsCallback;
        mRooms.clear();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("Room");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mRooms.clear();
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {

                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        Object value = d.getValue();
                        if (value != null) {
                            Log.w(TAG, "Value: " + d.getKey() + " " + value);
                            // Gson read data
                            Room room = mGson.fromJson(value.toString(), Room.class);
                            // room to RoomBean，for displaying
                            mRooms.add(new RoomBean(d.getKey(), room.getUnitNo(), room.getMaxCap(), room.getOpenTime(), room.getCloseTime(), room.isBlocked()));
                        }
                    }
                    if (mLoadPeopleCountThread == null) {
                        mLoadPeopleCountThread = new LoadPeopleCountThread();
                        mLoadPeopleCountThread.start();
                    }
                    // get roomSucess
//                    onGetRoomsCallback.onGetRoomsSuccess(mRooms);
                } else {
                    // get room fail
                    onGetRoomsCallback.onGetRoomsFail();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FindASpaceApplication.getInstance(), "Cancel getRoom", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     *getAvailableRooms
     */
    public void getAvailableRooms(final int noSeat, final OnGetAvailableRoomsCallBack onGetAvailableRoomsCallBack) {
        Log.w(TAG, "noSeat: " + noSeat);
        mOnGetAvailableRoomsCallBack = onGetAvailableRoomsCallBack;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference("Room");
        mRooms.clear();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        Object value = d.getValue();
                        if (value != null) {
                            // Gson read the data
                            Room room = mGson.fromJson(value.toString(), Room.class);
                            if (!room.isBlocked() && compareDate(room.getCloseTime()) && compareFromDate(room.getOpenTime())) {
                                // only add AvailableRoom
                                mRooms.add(new RoomBean(d.getKey(), room.getUnitNo(), room.getMaxCap(), room.getOpenTime(), room.getCloseTime(), room.isBlocked()));
                            }
                        }
                    }
                    if (mLoadPeopleCountThread == null) {
                        mLoadPeopleCountThread = new LoadPeopleCountThread();
                        mLoadPeopleCountThread.setNoSeat(noSeat);
                    }
                    mLoadPeopleCountThread.start();
//                    onGetAvailableRoomsCallBack.onGetAvailableRoomsSuccess(mRooms);
                } else {
                    // getAvailable rooms Fail
                    onGetAvailableRoomsCallBack.onGetAvailableRoomsFail();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FindASpaceApplication.getInstance(), "cancel getRoom", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * according person Num get room
     *
     * @param rooms
     * @param minPersonNumber minimal Person Number
     */
    public void eliminateByPersonNumber(List<RoomBean> rooms, int minPersonNumber) {

    }

    /**
     * get all rooms call back
     */
    public interface OnGetRoomsCallback {
        /**
         * get rooms success
         *
         * @param rooms room data
         */
        void onGetRoomsSuccess(List<RoomBean> rooms);

        /**
         * Get Rooms Fail
         */
        void onGetRoomsFail();
    }

    /**
     * get available room data
     */
    public interface OnGetAvailableRoomsCallBack {
        /**
         * get available rooms success
         *
         * @param availableRooms available Rooms data
         */
        void onGetAvailableRoomsSuccess(List<RoomBean> availableRooms);

        /**
         * get available rooms fail
         */
        void onGetAvailableRoomsFail();
    }

    /**
     * true: open time  >= current time
     * false:close time 《  current time
     *  only display the rooms before close time
     */
    private boolean compareDate(String closeTime) {
        try {
            if (mCurDate == null) {
                mCurDate = new Date();
            }
            if (mCalendar == null) {
                mCalendar = Calendar.getInstance();
            }
            int year = mCalendar.get(Calendar.YEAR);
            int month = mCalendar.get(Calendar.MONTH) + 1;
            int day = mCalendar.get(Calendar.DAY_OF_MONTH);

            if (mSimpleDateFormat == null) {
                mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HHmm", Locale.getDefault());
            }
            closeTime = year + "-" + month + "-" + day + " " + closeTime;
            Date closeDate = mSimpleDateFormat.parse(closeTime);
            if (mCurDate.before(closeDate)) {
                return true;
            } else if (closeDate.equals(mCurDate)) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * true: open time  >= current time
     *      * false:close time 《  current time
     *      *  only display the rooms before close time
     */
    private boolean compareFromDate(String fromTime) {
        try {
            if (mCurDate == null) {
                mCurDate = new Date();
            }
            if (mCalendar == null) {
                mCalendar = Calendar.getInstance();
            }
            int year = mCalendar.get(Calendar.YEAR);
            int month = mCalendar.get(Calendar.MONTH) + 1;
            int day = mCalendar.get(Calendar.DAY_OF_MONTH);

            if (mSimpleDateFormat == null) {
                mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HHmm", Locale.getDefault());
            }
            fromTime = year + "-" + month + "-" + day + " " + fromTime;
            Date fromDate = mSimpleDateFormat.parse(fromTime);
            if (mCurDate.after(fromDate)) {
                return true;
            } else if (fromDate.equals(mCurDate)) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * get from data
     */
    private String getFromDate() {
        if (mDateFormat == null) {
            mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        }
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        date = calendar.getTime();
        return mDateFormat.format(date);
    }

    /**
     * getToDate
     */
    private String getToDate() {
        if (mDateFormat == null) {
            mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        }
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        calendar.add(Calendar.HOUR_OF_DAY, +1);
        date = calendar.getTime();
        return mDateFormat.format(date);
    }

    public class LoadPeopleCountThread extends Thread {

        private final NotifyHandler mNotifyHandler;
        private int mNoSeat = -1;

        LoadPeopleCountThread() {
            mNotifyHandler = new NotifyHandler();
        }

        public void setNoSeat(int noSeat) {
            mNoSeat = noSeat;
        }

        @Override
        public void run() {
            super.setName(LoadPeopleCountThread.class.getSimpleName());
            super.run();
            for (int i = 0; i < mRooms.size(); i++) {
                final RoomBean roomBean = mRooms.get(i);
                String sensorAPIURL = "http://eif-research.feit.uts.edu.au/api/json/?rFromDate=" + getFromDate() + "&rToDate=" + getToDate() + "&rFamily=people&rSensor=" + " " + roomBean.getUnitNo() + " (Out)";
                try {
                    URL url = new URL(sensorAPIURL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();       //Commence connection
                    InputStream inputstream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputstream));
                    StringBuilder strBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        strBuilder.append(line);
                    }
                    String s = strBuilder.toString();
                    int j = s.indexOf(",");
                    int j1 = s.indexOf("]");
                    if (j >= 0 && j1 > j + 1) {
                        String substring = s.substring(s.indexOf(",") + 1, s.indexOf("]"));
                        try {
                            int personNumber = Integer.parseInt(substring);
                            roomBean.setPersonNumber(personNumber);
                            if (mNoSeat > roomBean.getMaxCap() - roomBean.getPersonNumber()) {
                                Log.w(TAG, "Room1: " + roomBean);
                            }
                            Log.w(TAG, "Room3: " + roomBean);
                        } catch (NumberFormatException e) {
                            Log.e(TAG, "NumberFormatException: " + e);
                        }
                    }
                } catch (MalformedURLException e) {
                    if (mRandom == null) {
                        mRandom = new Random();
                    }
                    roomBean.setPersonNumber(mRandom.nextInt(50));
                } catch (IOException e) {
                    if (mRandom == null) {
                        mRandom = new Random();
                    }
                    roomBean.setPersonNumber(mRandom.nextInt(50));
                }
            }
            ArrayList<RoomBean> rooms = new ArrayList<>();
            for (int i = 0; i < mRooms.size(); i++) {
                RoomBean roomBean = mRooms.get(i);
                if (mNoSeat <= 0 || mNoSeat <= roomBean.getMaxCap() - roomBean.getPersonNumber()) {
                    Log.w(TAG,mNoSeat +" <= " +  (roomBean.getMaxCap() - roomBean.getPersonNumber()));
                    rooms.add(roomBean);
                }
            }
            Message message = mNotifyHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(AVAILABLE_ROOMS, rooms);
            message.setData(bundle);
            message.what = NotifyHandler.LOAD_SUCCESS;
            mNotifyHandler.sendMessage(message);
        }
    }

    private class NotifyHandler extends Handler {
        static final int LOAD_SUCCESS = 0x01;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOAD_SUCCESS:
                    if (mOnGetAvailableRoomsCallBack != null) {
                        Bundle bundle = msg.getData();
                        ArrayList<RoomBean> availRooms = bundle.getParcelableArrayList(AVAILABLE_ROOMS);
                        mOnGetAvailableRoomsCallBack.onGetAvailableRoomsSuccess(availRooms);
                    }
                    if (mOnGetRoomsCallback != null) {
                        mOnGetRoomsCallback.onGetRoomsSuccess(mRooms);
                    }
                    break;
                default:
                    break;
            }
        }
    }

}

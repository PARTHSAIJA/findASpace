package com.findaspace.findaspace.main.room;

import android.os.Handler;
import android.os.Message;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *
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

    public RoomModel() {
        if (mGson == null) {
            mGson = new Gson();
        }
    }

    /**
     * get all room data
     *
     * @param onGetRoomsCallback
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
                            // room  to RoomBean，for display
                            mRooms.add(new RoomBean(d.getKey(), room.getUnitNo(), room.getMaxCap(), room.getOpenTime(), room.getCloseTime(), room.isBlocked()));
                        }
                    }
                    if (mLoadPeopleCountThread == null) {
                        mLoadPeopleCountThread = new LoadPeopleCountThread();
                        mLoadPeopleCountThread.start();
                    }
                    // get s
//                    onGetRoomsCallback.onGetRoomsSuccess(mRooms);
                } else {

                    onGetRoomsCallback.onGetRoomsFail();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FindASpaceApplication.getInstance(), "cancel get Room", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     *
     */
    public void getAvailableRooms(final int noSeat, final OnGetAvailableRoomsCallBack onGetAvailableRoomsCallBack) {
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
                            //
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
                    //
                    onGetAvailableRoomsCallBack.onGetAvailableRoomsFail();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FindASpaceApplication.getInstance(), "cancel get Room", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * according personNum display available room
     *
     * @param rooms
     * @param minPersonNumber Mini Number of person
     */
    public void eliminateByPersonNumber(List<RoomBean> rooms, int minPersonNumber) {

    }

    /**
     *
     */
    public interface OnGetRoomsCallback {
        /**
         *
         *
         * @param rooms
         */
        void onGetRoomsSuccess(List<RoomBean> rooms);

        /**
         *
         */
        void onGetRoomsFail();
    }

    /**
     *
     */
    public interface OnGetAvailableRoomsCallBack {
        /**
         *
         *
         * @param availableRooms
         */
        void onGetAvailableRoomsSuccess(List<RoomBean> availableRooms);

        /**
         *
         */
        void onGetAvailableRoomsFail();
    }

    /**
     *
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
     *
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
     *
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
     *
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
        boolean isPause;
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
                isPause = true;
                final RoomBean roomBean = mRooms.get(i);
                OkGo.<String>get(Constants.BASE_ROOM_DETAIL_URL)
                        .params(Constants.FROM_DATE, getFromDate())
                        .params(Constants.TO_DATE, getToDate())
                        .params(Constants.FAMILY, "people")
                        .params(Constants.SENSOR, " " + roomBean.getUnitNo() + " (Out)")
                        .retryCount(3)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                                String value = response.body();
                                int j = value.indexOf(",");
                                int j1 = value.indexOf("]");
                                if (j >= 0 && j1 > j + 1) {
                                    String substring = value.substring(value.indexOf(",") + 1, value.indexOf("]"));
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
                                isPause = false;
                                if (mNoSeat > 0 && mNoSeat > roomBean.getMaxCap() - roomBean.getPersonNumber()) {
                                    Log.w(TAG, "Room: " + roomBean);
                                    mRooms.remove(roomBean);
                                }
                            }

                            @Override
                            public void onError(com.lzy.okgo.model.Response<String> response) {
                                super.onError(response);
                                isPause = false;
                            }
                        });
                while (isPause) {
                    SystemClock.sleep(100);
                }
            }
            mNotifyHandler.sendEmptyMessage(NotifyHandler.LOAD_SUCCESS);
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
                        mOnGetAvailableRoomsCallBack.onGetAvailableRoomsSuccess(mRooms);
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

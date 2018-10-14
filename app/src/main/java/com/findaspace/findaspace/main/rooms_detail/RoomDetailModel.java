package com.findaspace.findaspace.main.rooms_detail;

import android.support.annotation.NonNull;
import android.util.Log;

import com.findaspace.findaspace.Constants;
import com.findaspace.findaspace.entity.Room;
import com.findaspace.findaspace.entity.RoomBean;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 */
public class RoomDetailModel {

    private static final String TAG = "RoomDetailModel";
    private SimpleDateFormat mSimpleDateFormat;

    /**
     *
     *
     * @param mRoom
     * @param blocked
     * @param onModifyCompleteCallback
     */
    public void modifyRoomData(RoomBean mRoom, boolean blocked, final OnModifyCompleteCallback onModifyCompleteCallback) {
        Room room = new Room(blocked, mRoom.getCloseTime(), mRoom.getMaxCap(), mRoom.getOpenTime(), mRoom.getUnitNo());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Room");
        Task<Void> task = databaseReference.child(mRoom.getRoomName()).setValue(room);
        task.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // modify finished
                onModifyCompleteCallback.onModifyComplete();
            }
        });
    }

    /**
     *
     *
     * @param unitNo Sensor
     */
    public void getCurRoomPersonNum(String unitNo, final OnGetCurRoomPersonCallback onGetCurRoomPersonCallback) {
        Log.w(TAG, "getCurRoomPersonNum");
        if (mSimpleDateFormat == null) {
            mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        }
        OkGo.<String>get(Constants.BASE_ROOM_DETAIL_URL)
                .params(Constants.FROM_DATE, getFromDate())
                .params(Constants.TO_DATE, getToDate())
                .params(Constants.FAMILY, "people")
                .params(Constants.SENSOR, " " + unitNo + " (Out)")
                .retryCount(3)
                .cacheKey(Constants.CACHE)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .cacheTime(3600 * 30)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String value = response.body();
                        int i = value.indexOf(",");
                        int i1 = value.indexOf("]");
                        if (i >= 0 && i1 > i + 1) {
                            String substring = value.substring(value.indexOf(",") + 1, value.indexOf("]"));
                            try {
                                int personNumber = Integer.parseInt(substring);
                                onGetCurRoomPersonCallback.onGetCurRoomPersonCallback(personNumber);
                            } catch (NumberFormatException e) {
                                Log.e(TAG, "NumberFormatException: " + e);
                            }
                        }
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {
                        super.onCacheSuccess(response);
                        String value = response.body();
                        int i = value.indexOf(",");
                        int i1 = value.indexOf("]");
                        if (i >= 0 && i1 > i + 1) {
                            String substring = value.substring(value.indexOf(",") + 1, value.indexOf("]"));
                            try {
                                int personNumber = Integer.parseInt(substring);
                                onGetCurRoomPersonCallback.onGetCurRoomPersonCallback(personNumber);
                            } catch (NumberFormatException e) {
                                Log.e(TAG, "NumberFormatException: " + e);
                            }
                        }
                    }
                });
    }

    /**
     * get Fromdate
     */
    private String getFromDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        date = calendar.getTime();
        return mSimpleDateFormat.format(date);
    }

    /**
     * get toDate
     */
    private String getToDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        calendar.add(Calendar.HOUR_OF_DAY, +1);
        date = calendar.getTime();
        return mSimpleDateFormat.format(date);
    }

    /**
     *
     */
    public interface OnGetCurRoomPersonCallback {
        /**
         *
         *
         * @param personNumber
         */
        void onGetCurRoomPersonCallback(int personNumber);
    }

    /**
     *
     */
    public interface OnModifyCompleteCallback {
        /**
         *
         */
        void onModifyComplete();
    }
}

package com.findaspace.findaspace.main.rooms_detail;

import android.text.format.DateUtils;
import android.util.Log;

import com.findaspace.findaspace.Constants;
import com.findaspace.findaspace.entity.RoomBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RoomDetailPresenterImpl implements IRoomDetailPresenter {

    private IRoomDetailView mView;
    private RoomDetailModel mRoomDetailModel;
    private SimpleDateFormat mSimpleDateFormat;

    @Override
    public void attachView(IRoomDetailView view) {
        mView = view;
        mRoomDetailModel = new RoomDetailModel();
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void modifyRoomData(RoomBean mRoom, boolean blocked) {
        mRoomDetailModel.modifyRoomData(mRoom, blocked, new RoomDetailModel.OnModifyCompleteCallback() {
            @Override
            public void onModifyComplete() {
                mView.modifyComplete();
            }
        });
    }

    /**
     * get current person number
     *
     * @param unitNo
     */
    @Override
    public void getCurRoomPersonNum(String unitNo) {
        mRoomDetailModel.getCurRoomPersonNum(unitNo, new RoomDetailModel.OnGetCurRoomPersonCallback() {
            @Override
            public void onGetCurRoomPersonCallback(int personNumber) {
                if (mView != null) {
                    mView.showCurRoomPersonNumber(personNumber);
                }
            }
        });
    }
}

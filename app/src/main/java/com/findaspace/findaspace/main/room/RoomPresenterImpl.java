package com.findaspace.findaspace.main.room;

import android.util.Log;

import com.findaspace.findaspace.base.BaseView;
import com.findaspace.findaspace.entity.RoomBean;

import java.util.List;

public class RoomPresenterImpl implements IRoomPresenter {

    private static final String TAG = RoomPresenterImpl.class.getSimpleName();
    private IRoomView mView;
    private RoomModel mRoomModel;

    @Override
    public void attachView(IRoomView view) {
        mView = view;
        mRoomModel = new RoomModel();
    }

    @Override
    public void detachView() {
        mView = null;
        mRoomModel = null;
    }

    @Override
    public void getAvailableRooms() {
        mRoomModel.getAvailableRooms(new RoomModel.OnGetAvailableRoomsCallBack() {
            @Override
            public void onGetAvailableRoomsSuccess(List<RoomBean> availableRooms) {
                if (availableRooms.size() <= 0) {
                    mView.showNullView();
                } else {
                    mView.showRoomView(availableRooms);
                }
            }

            @Override
            public void onGetAvailableRoomsFail() {
                mView.showLoadFailView();
            }
        });
    }

    @Override
    public void getRooms() {
        mRoomModel.getRooms(new RoomModel.OnGetRoomsCallback() {
            @Override
            public void onGetRoomsSuccess(List<RoomBean> rooms) {
                if (rooms.size() <= 0) {
                    mView.showNullView();
                } else {
                    mView.showRoomView(rooms);
                }
            }

            @Override
            public void onGetRoomsFail() {
                mView.showLoadFailView();
            }
        });
    }
}

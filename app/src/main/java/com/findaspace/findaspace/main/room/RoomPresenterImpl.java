package com.findaspace.findaspace.main.room;

import com.findaspace.findaspace.entity.RoomBean;

import java.util.List;

/**
 *
 *
 */
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

    /**
     *
     */
    @Override
    public void getAvailableRooms(int noSeat) {
        mView.showLoadingDialog();
        mRoomModel.getAvailableRooms(noSeat, new RoomModel.OnGetAvailableRoomsCallBack() {
            @Override
            public void onGetAvailableRoomsSuccess(List<RoomBean> availableRooms) {
                if (availableRooms.size() <= 0) {
                    //
                    if (mView != null) {
                        mView.showNullView();
                        mView.dismissLoadingDialog();
                    }
                } else {
                    //
                    if (mView != null) {
                        mView.showRoomView(availableRooms);
                        mView.dismissLoadingDialog();
                    }
                }
            }

            @Override
            public void onGetAvailableRoomsFail() {
                //
                mView.showLoadFailView();
                mView.dismissLoadingDialog();
            }
        });
    }

    /**
     *
     */
    @Override
    public void getRooms() {
        mView.showLoadingDialog();
        mRoomModel.getRooms(new RoomModel.OnGetRoomsCallback() {
            @Override
            public void onGetRoomsSuccess(List<RoomBean> rooms) {
                if (rooms.size() <= 0) {
                    //
                    mView.showNullView();
                    mView.dismissLoadingDialog();
                } else {
                    //
                    mView.showRoomView(rooms);
                    mView.dismissLoadingDialog();
                }
            }

            @Override
            public void onGetRoomsFail() {
                //
                mView.showLoadFailView();
                mView.dismissLoadingDialog();
            }
        });
    }
}

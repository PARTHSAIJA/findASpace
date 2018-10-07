package com.findaspace.findaspace.main.rooms_detail;

import com.findaspace.findaspace.entity.RoomBean;

public class RoomDetailPresenterImpl implements IRoomDetailPresenter {

    private IRoomDetailView mView;
    private RoomDetailModel mRoomDetailModel;

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
}

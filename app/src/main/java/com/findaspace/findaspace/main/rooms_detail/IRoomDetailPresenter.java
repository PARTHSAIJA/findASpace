package com.findaspace.findaspace.main.rooms_detail;

import com.findaspace.findaspace.base.BasePresenter;
import com.findaspace.findaspace.entity.RoomBean;

public interface IRoomDetailPresenter extends BasePresenter<IRoomDetailView> {
    /**
     * modify room data
     */
    void modifyRoomData(RoomBean mRoom, boolean blocked);
}

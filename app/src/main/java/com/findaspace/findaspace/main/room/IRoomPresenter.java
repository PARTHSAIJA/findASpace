package com.findaspace.findaspace.main.room;

import com.findaspace.findaspace.base.BasePresenter;

/**
 *
 */
public interface IRoomPresenter extends BasePresenter<IRoomView> {
    /**
     *
     */
    void getAvailableRooms(int noSeat);

    /**
     *
     */
    void getRooms();
}

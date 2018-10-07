package com.findaspace.findaspace.main.room;

import com.findaspace.findaspace.base.BasePresenter;

/**
 * Created by Administrator on 2018/10/5.
 */

public interface IRoomPresenter extends BasePresenter<IRoomView> {

    void getAvailableRooms();


    void getRooms();
}

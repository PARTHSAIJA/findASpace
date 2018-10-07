package com.findaspace.findaspace.main.room;

import com.findaspace.findaspace.base.BaseView;
import com.findaspace.findaspace.entity.RoomBean;

import java.util.List;

/**
 * Created by Administrator on 2018/10/5.
 */

public interface IRoomView extends BaseView {

    /**
     * showNullView
     */
    void showNullView();

    /**
     * showRoomView
     */
    void showRoomView(List<RoomBean> rooms);

    /**
     * showLoadFailView
     */
    void showLoadFailView();
}

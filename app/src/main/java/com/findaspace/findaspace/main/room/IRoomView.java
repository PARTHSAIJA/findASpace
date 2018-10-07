package com.findaspace.findaspace.main.room;

import com.findaspace.findaspace.base.BaseView;
import com.findaspace.findaspace.entity.RoomBean;

import java.util.List;

/**
 * Created by Administrator on 2018/10/5.
 */

public interface IRoomView extends BaseView {

    /**
     * show null
     */
    void showNullView();

    /**
     * show room view
     */
    void showRoomView(List<RoomBean> rooms);

    /**
     * showLoadFail
     */
    void showLoadFailView();
}

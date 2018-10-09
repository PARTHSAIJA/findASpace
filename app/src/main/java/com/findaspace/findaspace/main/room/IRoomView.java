package com.findaspace.findaspace.main.room;

import com.findaspace.findaspace.base.BaseView;
import com.findaspace.findaspace.entity.RoomBean;

import java.util.List;

/**
 *
 */
public interface IRoomView extends BaseView {

    /**
     *
     */
    void showNullView();

    /**
     *
     *
     *
     */
    void showRoomView(List<RoomBean> rooms);

    /**
     *
     */
    void showLoadFailView();

    /**
     *
     */
    void showLoadingDialog();

    /**
     *
     */
    void dismissLoadingDialog();
}

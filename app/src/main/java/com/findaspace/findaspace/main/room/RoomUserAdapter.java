package com.findaspace.findaspace.main.room;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.findaspace.findaspace.app.R;
import com.findaspace.findaspace.entity.RoomBean;

import java.util.List;

/**
 *
 */
public class RoomUserAdapter extends BaseQuickAdapter<RoomBean, BaseViewHolder> {

    /**
     *
     */
    private int mNoSeat;

    public RoomUserAdapter(@Nullable List<RoomBean> data, int noSeat) {
        super(R.layout.room_item_user, data);
        mNoSeat = noSeat;
    }

    @Override
    protected void convert(BaseViewHolder helper, RoomBean item) {
        helper.setText(R.id.available_room_tv, item.getRoomName());
    }
}

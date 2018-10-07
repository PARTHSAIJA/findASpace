package com.findaspace.findaspace.main.room;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.findaspace.findaspace.app.R;
import com.findaspace.findaspace.entity.RoomBean;

import java.util.List;

public class RoomSecurityAdapter extends BaseQuickAdapter<RoomBean, BaseViewHolder> {
    public RoomSecurityAdapter(@Nullable List<RoomBean> data) {
        super(R.layout.room_item_user, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RoomBean item) {
        helper.setText(R.id.available_room_tv, item.getRoomName());
    }
}

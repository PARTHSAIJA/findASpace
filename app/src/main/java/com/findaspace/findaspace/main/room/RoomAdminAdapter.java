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
public class RoomAdminAdapter extends BaseQuickAdapter<RoomBean, BaseViewHolder> {
    public RoomAdminAdapter(@Nullable List<RoomBean> data) {
        super(R.layout.room_item_admin, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RoomBean item) {

        helper.setText(R.id.room_name_tv, item.getRoomName());

        helper.addOnClickListener(R.id.room_modify_btn);
    }
}

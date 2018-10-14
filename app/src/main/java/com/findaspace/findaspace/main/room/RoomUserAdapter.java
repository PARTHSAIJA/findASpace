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

        //Format the room to UTS Room formatting (CB1104101 -> CB11.04.101)
        String roomUnformatted = item.getRoomName();
        String roomBuilding = ((roomUnformatted).substring(2)).substring(0, Math.min((roomUnformatted).substring(2).length(), 2));
        //Get just the level number
        String roomLvl = ((roomUnformatted).substring(4)).substring(0, Math.min((roomUnformatted).substring(4).length(), 2));
        //Get just the room
        String roomNo = (roomUnformatted).substring(6);
        String roomFormatted = "CB" + roomBuilding + "." + roomLvl + "." + roomNo;

        helper.setText(R.id.available_room_tv, roomFormatted);
    }
}

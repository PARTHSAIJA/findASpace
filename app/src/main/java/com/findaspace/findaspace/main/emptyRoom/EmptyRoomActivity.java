package com.findaspace.findaspace.main.emptyRoom;

import com.findaspace.findaspace.app.R;
import com.findaspace.findaspace.base.BaseActivity;
import com.findaspace.findaspace.base.BasePresenter;
import com.findaspace.findaspace.base.FindASpaceApplication;

public class EmptyRoomActivity extends BaseActivity {

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {
        FindASpaceApplication.UserFlag userFlag = FindASpaceApplication.getInstance().mUserFlag;
        switch (userFlag) {
            case USER:
                setActionBarTitle("Available Room");
                break;
            case ADMIN:
                setActionBarTitle("Room");
                break;
            case SECURITY:
                setActionBarTitle("Room");
                break;
            default:
                break;
        }
    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_empty_room;
    }
}

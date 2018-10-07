package com.findaspace.findaspace.main.room;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.findaspace.findaspace.app.R;
import com.findaspace.findaspace.base.BaseActivity;
import com.findaspace.findaspace.base.FindASpaceApplication;
import com.findaspace.findaspace.entity.RoomBean;
import com.findaspace.findaspace.main.rooms_detail.RoomDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class RoomActivity extends BaseActivity<IRoomView, IRoomPresenter> implements IRoomView {

    @BindView(R.id.room_recycle)
    RecyclerView mRoomRecycle;
    private BaseQuickAdapter mRoomAdapter;
    private List<RoomBean> mRooms = new ArrayList<>();
    public static final String ROOM_DETAIL = "room_detail";
    public static final String ROOM_MODIFY = "room_modify";
    private RoomPresenterImpl mPresenter;

    @Override
    protected void initEvent() {

        mRoomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent(RoomActivity.this, RoomDetailActivity.class);
                intent.putExtra(ROOM_DETAIL, mRooms.get(position));
                intent.putExtra(ROOM_MODIFY, false);
                startActivity(intent);
            }
        });

        if (mRoomAdapter instanceof RoomAdminAdapter) {
            mRoomAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (view.getId()) {
                        case R.id.room_block_switch:

                            break;
                        case R.id.room_modify_btn:
                            Intent intent = new Intent(RoomActivity.this, RoomDetailActivity.class);
                            intent.putExtra(ROOM_DETAIL, mRooms.get(position));
                            intent.putExtra(ROOM_MODIFY, true);
                            startActivity(intent);
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }

    @Override
    protected void initView() {
        setActionBarTitle();
        mPresenter = (RoomPresenterImpl) getPresenter();
        initRecycle();
        getRooms();
    }

    @Override
    protected IRoomPresenter setPresenter() {
        return new RoomPresenterImpl();
    }


    private void setActionBarTitle() {
        switch (FindASpaceApplication.getInstance().mUserFlag) {
            case USER:
                setActionBarTitle("Available Rooms");
                break;
            default:
                setActionBarTitle("Rooms");
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_available_room;
    }



    private void getRooms() {
        switch (FindASpaceApplication.getInstance().mUserFlag) {
            case USER:
                mPresenter.getAvailableRooms();
                break;
            case SECURITY:
                mPresenter.getRooms();
                break;
            case ADMIN:
                mPresenter.getRooms();
                break;
            default:
                break;
        }

        /*mRooms.clear();
        RoomBean roomBean1 = new RoomBean("CB1100100", "PC00.05", 50, "0500", "2300", false);
        RoomBean roomBean2 = new RoomBean("CB1100401", "PC00.06", 50, "0500", "2300", false);
        RoomBean roomBean3 = new RoomBean("CB1100440", "PC00.07", 50, "0500", "2300", false);
        RoomBean roomBean4 = new RoomBean("CB1100SR05", "PC00.08", 50, "0500", "2300", false);
        RoomBean roomBean5 = new RoomBean("CB1100ST03", "PC00.09", 50, "0500", "2300", false);
        RoomBean roomBean6 = new RoomBean("CB110102", "PC00.11", 50, "0500", "2300", false);
        RoomBean roomBean7 = new RoomBean("CB1101201", "PC00.13", 50, "0500", "2300", false);
        RoomBean roomBean8 = new RoomBean("CB1101301", "PC00.12", 50, "0500", "2300", false);
        RoomBean roomBean9 = new RoomBean("CB1102100", "PC00.14", 50, "0500", "2300", false);
        RoomBean roomBean10 = new RoomBean("CB1102102", "PC00.15", 50, "0500", "2300", false);
        RoomBean roomBean11 = new RoomBean("CB1102ST02", "PC00.16", 50, "0500", "2300", false);
        RoomBean roomBean12 = new RoomBean("CB1103202", "PC00.17", 50, "0500", "2300", false);
        RoomBean roomBean13 = new RoomBean("CB1104400", "PC00.20", 50, "0500", "2300", false);
        RoomBean roomBean14 = new RoomBean("CB1105101", "PC00.21", 50, "0500", "2300", false);
        mRooms.add(roomBean1);
        mRooms.add(roomBean2);
        mRooms.add(roomBean3);
        mRooms.add(roomBean4);
        mRooms.add(roomBean5);
        mRooms.add(roomBean6);
        mRooms.add(roomBean7);
        mRooms.add(roomBean8);
        mRooms.add(roomBean9);
        mRooms.add(roomBean10);
        mRooms.add(roomBean11);
        mRooms.add(roomBean12);
        mRooms.add(roomBean13);
        mRooms.add(roomBean14);

        mRoomAdapter.notifyDataSetChanged();*/
    }


    private void initRecycle() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRoomRecycle.setLayoutManager(linearLayoutManager);
        mRoomRecycle.addItemDecoration(dividerItemDecoration);
        switch (FindASpaceApplication.getInstance().mUserFlag) {
            case ADMIN:
                mRoomAdapter = new RoomAdminAdapter(mRooms);
                break;
            case SECURITY:
                mRoomAdapter = new RoomSecurityAdapter(mRooms);
                break;
            case USER:
                mRoomAdapter = new RoomUserAdapter(mRooms);
                break;
            default:
                mRoomAdapter = new RoomUserAdapter(mRooms);
                break;
        }
        mRoomRecycle.setAdapter(mRoomAdapter);
    }

    @Override
    public void showNullView() {
        TextView emptyView = new TextView(this);
        emptyView.setText(R.string.room_empty);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        emptyView.setLayoutParams(layoutParams);
        emptyView.setGravity(Gravity.CENTER);
        mRoomAdapter.setEmptyView(emptyView);
    }

    @Override
    public void showRoomView(List<RoomBean> rooms) {
        mRooms.clear();
        mRooms.addAll(rooms);
        mRoomAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadFailView() {
        TextView failView = new TextView(this);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        failView.setLayoutParams(layoutParams);
        failView.setGravity(Gravity.CENTER);
        failView.setText(R.string.room_load_fail);
        failView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRooms();
            }
        });
        mRoomAdapter.setEmptyView(failView);
    }
}

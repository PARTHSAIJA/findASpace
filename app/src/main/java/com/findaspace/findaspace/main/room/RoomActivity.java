package com.findaspace.findaspace.main.room;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.findaspace.findaspace.app.R;
import com.findaspace.findaspace.base.BaseActivity;
import com.findaspace.findaspace.base.FindASpaceApplication;
import com.findaspace.findaspace.dialog.LoadingDialog;
import com.findaspace.findaspace.entity.RoomBean;
import com.findaspace.findaspace.main.rooms_detail.RoomDetailActivity;
import com.findaspace.findaspace.main.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class RoomActivity extends BaseActivity<IRoomView, IRoomPresenter> implements IRoomView {

    @BindView(R.id.room_recycle)
    RecyclerView mRoomRecycle;
    private BaseQuickAdapter mRoomAdapter;
    /**
     *
     * get room data from firebase， using for RecyclerView
     */
    private List<RoomBean> mRooms = new ArrayList<>();
    /**
     *
     * Bundle key value， click item， deliver click item to RoomDetailActivity by intent
     */
    public static final String ROOM_DETAIL = "room_detail";
    /**
     * Bundle key value， click item modify button， deliver click item to RoomDetailActivity by intent
     *
     *
     */
    public static final String ROOM_MODIFY = "room_modify";
    /**
     * connect room model and room view
     *
     */
    private RoomPresenterImpl mPresenter;
    /**
     * loading
     */
    private LoadingDialog mLoadingDialog;
    /**
     *
     */
    private int mNoSeat;

    @Override
    protected void initEvent() {
        // setOnItemClickListener
        mRoomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (FindASpaceApplication.getInstance().mUserFlag) {
                    case USER:
                        break;
                    default:
                        //
                        Intent intent = new Intent(RoomActivity.this, RoomDetailActivity.class);

                        RoomBean roomBean = mRooms.get(position);
                        Log.w("TAGA", "roomBean: " + roomBean);
                        intent.putExtra(ROOM_DETAIL, roomBean);
                        intent.putExtra(ROOM_MODIFY, false);
                        startActivity(intent);
                        break;
                }
            }
        });

        if (mRoomAdapter instanceof RoomAdminAdapter) {
            mRoomAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (view.getId()) {
                        case R.id.room_modify_btn:
                            // turn to RoomDetailActivity，modify room data
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
        // setActionBarTitle
        setActionBarTitle();
        // get Presenter
        mPresenter = (RoomPresenterImpl) getPresenter();
        Intent intent = getIntent();
        if (intent != null) {
            mNoSeat = intent.getIntExtra(SearchActivity.NO_SEAT, 0);
        }
        // Initiate RecyclerView，using for display room data
        initRecycle();
        // get room data
        getRooms();
    }

    /**
     * set Presenter
     */
    @Override
    protected IRoomPresenter setPresenter() {
        return new RoomPresenterImpl();
    }

    /**
     * setActionBarTitle
     */
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


    /**
     * get available room
     *
     */
    private void getRooms() {
        switch (FindASpaceApplication.getInstance().mUserFlag) {
            case USER:
                // get available room
                mPresenter.getAvailableRooms(mNoSeat);
                break;
            case SECURITY:
                // get all room
                mPresenter.getRooms();
                break;
            case ADMIN:
                //get all room
                mPresenter.getRooms();
                break;
            default:
                break;
        }
    }

    /**
     * initiate Recycleview
     * display available room
     */
    private void initRecycle() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRoomRecycle.setLayoutManager(linearLayoutManager);
        mRoomRecycle.addItemDecoration(dividerItemDecoration);
        //
        // different type of user , use different adapter show room data
        switch (FindASpaceApplication.getInstance().mUserFlag) {
            case ADMIN:
                mRoomAdapter = new RoomAdminAdapter(mRooms);
                break;
            case SECURITY:
                mRoomAdapter = new RoomSecurityAdapter(mRooms);
                break;
            case USER:
                mRoomAdapter = new RoomUserAdapter(mRooms, mNoSeat);
                break;
            default:
                mRoomAdapter = new RoomUserAdapter(mRooms, mNoSeat);
                break;
        }
        mRoomRecycle.setAdapter(mRoomAdapter);
    }

    /**
     * if get no room show  nothing
     */
    @Override
    public void showNullView() {
        TextView emptyView = new TextView(this);
        emptyView.setText(R.string.room_empty);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        emptyView.setLayoutParams(layoutParams);
        emptyView.setGravity(Gravity.CENTER);
        mRoomAdapter.setEmptyView(emptyView);
    }

    /**
     * if get room data，update Adapter
     *
     * @param rooms get room list from firebase
     */
    @Override
    public void showRoomView(List<RoomBean> rooms) {
        mRooms.clear();
        mRooms.addAll(rooms);
        mRoomAdapter.notifyDataSetChanged();
    }

    /**
     * show Load Fail View
     */
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
                // re get room
                getRooms();
            }
        });
        mRoomAdapter.setEmptyView(failView);
    }

    /**
     * show loading
     */
    @Override
    public void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    /**
     *
     */
    @Override
    public void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }
}

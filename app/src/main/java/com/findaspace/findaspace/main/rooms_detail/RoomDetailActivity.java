package com.findaspace.findaspace.main.rooms_detail;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.findaspace.findaspace.app.R;
import com.findaspace.findaspace.base.BaseActivity;
import com.findaspace.findaspace.entity.RoomBean;
import com.findaspace.findaspace.main.room.RoomActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Room Detail
 * display the data of available room
 */
public class RoomDetailActivity extends BaseActivity<IRoomDetailView, IRoomDetailPresenter> implements IRoomDetailView {

    @BindView(R.id.room_detail_name_tv)
    TextView mRoomDetailNameTv;
    @BindView(R.id.room_unit_no_tv)
    TextView mRoomUnitNoTv;
    @BindView(R.id.room_max_cap_tv)
    TextView mRoomMaxCapTv;
    @BindView(R.id.room_open_time_tv)
    TextView mRoomOpenTimeTv;
    @BindView(R.id.room_close_time_tv)
    TextView mRoomCloseTimeTv;
    @BindView(R.id.room_block_tv)
    TextView mRoomBlockTv;
    @BindView(R.id.room_block_switch)
    Switch mRoomBlockSwitch;
    @BindView(R.id.room_confirm_btn)
    Button mRoomConfirmBtn;
    private IRoomDetailPresenter mPresenter;
    private RoomBean mRoom;
    private Calendar mCalendar;
    private TimePickerDialog mOpenTimePickerDialog;
    private TimePickerDialog mCloseTimePickerDialog;

    @Override
    protected void initEvent() {
        mRoomBlockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setText(isChecked ? "unAvailable" : "available");
            }
        });
    }

    @Override
    protected void initView() {
        mPresenter = getPresenter();
        //
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        Parcelable parcelableExtra = intent.getParcelableExtra(RoomActivity.ROOM_DETAIL);
        boolean showModifyView = intent.getBooleanExtra(RoomActivity.ROOM_MODIFY, false);
        if (showModifyView) {
            // setVisibility visible
            mRoomBlockSwitch.setVisibility(View.VISIBLE);
            mRoomConfirmBtn.setVisibility(View.VISIBLE);
        }
        // get Instance of Calendar
        mCalendar = Calendar.getInstance();
        if (parcelableExtra instanceof RoomBean) {
            mRoom = (RoomBean) parcelableExtra;
            mRoomDetailNameTv.setText("RoomName: " + mRoom.getRoomName());
            mRoomUnitNoTv.setText("UnitNo: " + mRoom.getUnitNo());
            mRoomMaxCapTv.setText("MaxCap: " + mRoom.getMaxCap());
            String openTime = mRoom.getOpenTime();
            String openTimeHour = openTime.substring(0, 2);
            String openTimeMin = openTime.substring(2, openTime.length());
            mRoomOpenTimeTv.setText("OpenTime: " + openTimeHour + ":" + openTimeMin);
            String closeTime = mRoom.getCloseTime();
            String closeTimeHour = closeTime.substring(0, 2);
            String closeTimeMin = closeTime.substring(2, openTime.length());
            mRoomCloseTimeTv.setText("CloseTime: " + closeTimeHour + ":" + closeTimeMin);
            boolean blocked = mRoom.isBlocked();
            mRoomBlockSwitch.setChecked(blocked);
            mRoomBlockTv.setText(blocked ? "Status: unAvailable" : "Status: available");
        }
    }

    @Override
    protected IRoomDetailPresenter setPresenter() {
        return new RoomDetailPresenterImpl();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_room_detail;
    }

    @OnClick({R.id.room_confirm_btn, R.id.room_open_time_tv, R.id.room_close_time_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.room_confirm_btn:
                // modify Room data
                mPresenter.modifyRoomData(mRoom, mRoomBlockSwitch.isChecked());
                break;
            case R.id.room_open_time_tv:
                // modify open time Dialog
                if (mOpenTimePickerDialog == null) {
                    mOpenTimePickerDialog = new TimePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String hour = String.format("%02d", hourOfDay);
                            String min = String.format("%02d", minute);
                            mRoom.setOpenTime(hour + min);
                            mRoomOpenTimeTv.setText("OpenTime: " + hour + ":" + min);
                        }
                    }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);
                }
                mOpenTimePickerDialog.show();
                break;
            case R.id.room_close_time_tv:
                // Modify close time Dialog
                if (mCloseTimePickerDialog == null) {
                    mCloseTimePickerDialog = new TimePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String hour = String.format("%02d", hourOfDay);
                            String min = String.format("%02d", minute);
                            mRoom.setCloseTime(hour + min);
                            mRoomCloseTimeTv.setText("CloseTime: " + hour + ":" + min);
                        }
                    }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);
                }
                mCloseTimePickerDialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void modifyComplete() {
        // finished modify
        finish();
    }
}

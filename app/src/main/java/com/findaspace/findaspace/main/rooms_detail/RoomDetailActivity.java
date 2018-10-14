package com.findaspace.findaspace.main.rooms_detail;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.findaspace.findaspace.app.R;
import com.findaspace.findaspace.base.BaseActivity;
import com.findaspace.findaspace.base.FindASpaceApplication;
import com.findaspace.findaspace.entity.RoomBean;
import com.findaspace.findaspace.main.room.RoomActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Room Detail
 */
public class RoomDetailActivity extends BaseActivity<IRoomDetailView, IRoomDetailPresenter> implements IRoomDetailView {

    private static final String ROOM = "Room";
    private static final String MAX_CAP = "Max Cap";
    private static final String OPEN_TIME = "Open Time";
    private static final String CLOSE_TIME = "Close Time";
    private static final String STATUS_BLOCKED = "Status: Blocked";
    private static final String STATUS_AVAILABLE = "Status: Available";
    private static final String PEOPLE_COUNT = "People Count";
    private static final String SENSOR_ROOM_UNIT_NO = "Sensor Room Unit No";
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
    @BindView(R.id.room_person_number_tv)
    TextView mRoomPersonNumberTv;
    @BindView(R.id.room_block_switch)
    Switch mRoomBlockSwitch;
    @BindView(R.id.room_confirm_btn)
    Button mRoomConfirmBtn;
    private IRoomDetailPresenter mPresenter;
    private RoomBean mRoom;
    private Calendar mCalendar;
    private TimePickerDialog mOpenTimePickerDialog;
    private TimePickerDialog mCloseTimePickerDialog;
    private boolean mShowModifyView;

    @Override
    protected void initEvent() {
        mRoomBlockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Switch
                mRoomBlockTv.setText(isChecked ? STATUS_BLOCKED : "Status: Available");
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
        mShowModifyView = intent.getBooleanExtra(RoomActivity.ROOM_MODIFY, false);
        if (mShowModifyView) {
            //
            mRoomBlockSwitch.setVisibility(View.VISIBLE);
            mRoomConfirmBtn.setVisibility(View.VISIBLE);
        }
        //
        mCalendar = Calendar.getInstance();
        // set room data
        if (parcelableExtra instanceof RoomBean) {
            mRoom = (RoomBean) parcelableExtra;

            //Format the room to UTS Room formatting (CB1104101 -> CB11.04.101)
            String roomUnformatted = mRoom.getRoomName();
            String roomBuilding = ((roomUnformatted).substring(2)).substring(0, Math.min((roomUnformatted).substring(2).length(), 2));
            //Get just the level number
            String roomLvl = ((roomUnformatted).substring(4)).substring(0, Math.min((roomUnformatted).substring(4).length(), 2));
            //Get just the room
            String roomNo = (roomUnformatted).substring(6);
            String roomFormatted = "CB" + roomBuilding + "." + roomLvl + "." + roomNo;

            mRoomDetailNameTv.setText(String.valueOf(ROOM + ": " + roomFormatted));
            mRoomUnitNoTv.setText(SENSOR_ROOM_UNIT_NO + ": " + mRoom.getUnitNo());
            mRoomMaxCapTv.setText(String.valueOf(MAX_CAP + ": " + mRoom.getMaxCap()));
            String openTime = mRoom.getOpenTime();
            String openTimeHour = openTime.substring(0, 2);
            String openTimeMin = openTime.substring(2, openTime.length());
            mRoomOpenTimeTv.setText(String.valueOf(OPEN_TIME + ": " + openTimeHour + ":" + openTimeMin));
            String closeTime = mRoom.getCloseTime();
            String closeTimeHour = closeTime.substring(0, 2);
            String closeTimeMin = closeTime.substring(2, openTime.length());
            mRoomCloseTimeTv.setText(String.valueOf(CLOSE_TIME + ": " + closeTimeHour + ":" + closeTimeMin));
            boolean blocked = mRoom.isBlocked();
            mRoomBlockSwitch.setChecked(blocked);
            mRoomBlockTv.setText(blocked ? STATUS_BLOCKED : STATUS_AVAILABLE);
            if (FindASpaceApplication.getInstance().mUserFlag != FindASpaceApplication.UserFlag.USER) {
                mRoomPersonNumberTv.setText(String.format(PEOPLE_COUNT + ": %d", mRoom.getPersonNumber()));
            }
            //
//            mPresenter.getCurRoomPersonNum(mRoom.getUnitNo());
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
                //
                mPresenter.modifyRoomData(mRoom, mRoomBlockSwitch.isChecked());
                break;
            case R.id.room_open_time_tv:
                if (!mShowModifyView) {
                    //
                    break;
                }
                //
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
                if (!mShowModifyView) {
                    //
                    break;
                }
                //
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

        finish();
    }

    /**
     *
     *
     * @param personNumber
     */
    @Override
    public void showCurRoomPersonNumber(int personNumber) {
        mRoomPersonNumberTv.setText(String.format("personNumber: %d", personNumber));
    }
}

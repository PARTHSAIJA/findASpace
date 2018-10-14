package com.findaspace.findaspace.feature;


import com.findaspace.findaspace.main.room.*;
import com.findaspace.findaspace.main.room.RoomModel;
import com.findaspace.findaspace.main.room.IRoomView;
import com.findaspace.findaspace.main.search.RoomRecord;
import com.google.firebase.database.*;

import com.findaspace.findaspace.Constants;
import com.findaspace.findaspace.base.FindASpaceApplication;
import com.findaspace.findaspace.entity.Room;
import com.findaspace.findaspace.entity.RoomBean;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;
import com.findaspace.findaspace.entity.RoomBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.findaspace.findaspace.entity.RoomBean;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;



import org.junit.Test;


import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

//    @Test
//    public void searchCorrect(){
//        int noSeat = 6;
//        final IRoomView testView;
//
//        RoomModel testRoom = new RoomModel();   // Create Room model Object
//
//        Boolean dateTestResult = false;         // Build test case
//        String dateTest = "2018-10-14 1022";
//        testRoom.getAvailableRooms(noSeat, testRoom.getOnGetAvailableRoomsCallBack());
//        testRoom.setCurDate();
//
//        Boolean tested = testRoom.callCompareDate(dateTest);
//
//        assertEquals( tested, dateTestResult); // Run test case
//
//    }

    @Test
    public void testRoomrecordPlacesLeft(){
        String testBuildingNo = "11";
        String testLvlNo = "6";
        int testRoomNo = 10;
        int testNoOfSeatsAvaliable = 20;
        int testNoOfPeopleInSpace = 13;

        RoomRecord testRoom = new RoomRecord(testBuildingNo,testLvlNo,testRoomNo,testNoOfSeatsAvaliable,testNoOfPeopleInSpace);

        Integer expect = 7;
        Integer testPlaces = testRoom.placesLeft();
        assertEquals(testPlaces,expect);
    }
    @Test
    public void testRoomRecordNoPlaces(){
        String testBuildingNo = "11";
        String testLvlNo = "6";
        int testRoomNo = 10;
        int testNoOfSeatsAvailiable = 20;
        int testNoOfPeopleInSpace = 20;

        RoomRecord testRoom = new RoomRecord(testBuildingNo,testLvlNo,testRoomNo,testNoOfSeatsAvailiable,testNoOfPeopleInSpace);

        Integer expect = 0;
        Integer testPlaces = testRoom.placesLeft();
        assertEquals(testPlaces,expect);
    }

//    @Test
//    public void testAdminModifyDeets(){
//        String testBuildingNo = "11";
//        String testLvlNo = "6";
//        int testRoomNo = 10;
//        int testNoOfSeatsAvaliable = 20;
//        int testNoOfPeopleInSpace = 20;
//
//        RoomRecord testRoom = new RoomRecord(testBuildingNo,testLvlNo,testRoomNo,testNoOfSeatsAvaliable,testNoOfPeopleInSpace);
//
//        Integer expect = 0;
//
//    }

    @Test
    public void testBlockedRoom(){
        String testRoomName = "Room1";
        String testUnitNo = "Unit1";
        int testMaxCap = 20;
        String testOpenTime = "1200";
        String testCloseTime = "1400";
        boolean testBlocked = true;

        RoomBean testRoom = new RoomBean(testRoomName, testUnitNo, testMaxCap, testOpenTime, testCloseTime, testBlocked);

        // Test get and set blocking functions
        boolean expectBool = true;
        boolean actualBool = testRoom.isBlocked();
        assertEquals(expectBool, actualBool);

        // Test blocking change
        testRoom.setBlocked(false);
        expectBool = false;
        actualBool = testRoom.isBlocked();
        assertEquals(expectBool, actualBool);

        // Test Get and set maxCap for room
        int expectInt = 20;
        int actualInt = testRoom.getMaxCap();
        assertEquals(expectInt,actualInt);

        // Test maxCap change
        expectInt = 27;
        testRoom.setMaxCap(expectInt);
        actualInt = testRoom.getMaxCap();
        assertEquals(expectInt, actualInt);

        // Test get room name
        String expectString = testRoomName;
        String actualString = testRoom.getRoomName();
        assertEquals(expectString, actualString);

        //Test change room name
        expectString = "newRoom";
        testRoom.setRoomName(expectString);
        actualString = testRoom.getRoomName();
        assertEquals(expectString,actualString);

    }

}
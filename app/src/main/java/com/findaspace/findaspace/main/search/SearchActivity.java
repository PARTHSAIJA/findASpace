package com.findaspace.findaspace.main.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.findaspace.findaspace.app.R;
import com.findaspace.findaspace.main.login.LoginActivity;
import com.findaspace.findaspace.main.member.MemberActivity;
import com.findaspace.findaspace.readDB.UTSRooms;
import com.google.firebase.auth.FirebaseAuth;

import java.util.LinkedList;


public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final String TAG = "SearchActivity";
    private static Button submit_button;

    private LinkedList<String> list = new LinkedList<>();

    String[] buildingNo={"CB1","CB2","CB3","CB4","CB5","CB6","CB7","CB8","CB9","CB10","CB11"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//Getting the instance of Spinner and applying OnItemSelectedListener on it

        UTSRooms dbRooms = new UTSRooms(this);
        try {
            dbRooms.getAllRooms();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        submitButton();
    }

    public void setBuilding(String[] roomsUTS ){
        Spinner spin = (Spinner) findViewById(R.id.spinnerBuilding);
        spin.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,roomsUTS);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_POWER) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(SearchActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SearchActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        //Toast.makeText(getApplicationContext(), buildingNo[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
     // TODO Auto-generated method stub

    }

    public void submitButton(){
        submit_button = (Button)findViewById(R.id.button);


        submit_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchActivity.this,MemberActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    }


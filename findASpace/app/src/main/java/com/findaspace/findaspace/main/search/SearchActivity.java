package com.findaspace.findaspace.main.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.findaspace.findaspace.app.R;
import com.findaspace.findaspace.main.login.LoginActivity;
import com.findaspace.findaspace.main.member.MemberActivity;
import com.google.firebase.auth.FirebaseAuth;


public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final String TAG = "SearchActivity";
    private Button submit_button;

    private Spinner buildingSpr;
    private EditText noOfPeopleTxt;

    String[] buildingNo={"CB1","CB2","CB3","CB4","CB5","CB6","CB7","CB8","CB9","CB10","CB11"};

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//Getting the instance of Spinner and applying OnItemSelectedListener on it
        buildingSpr = findViewById(R.id.spinnerBuilding);
        noOfPeopleTxt = findViewById(R.id.txtNoOfSeats);

        setBuildingsSpinner();
        activateSubmitBtnListener();
    }

    /**
     * @brief
     */
    private void setBuildingsSpinner() {
        Spinner spin = (Spinner) findViewById(R.id.spinnerBuilding);
        spin.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,buildingNo);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }


    /**
     * @brief Sign user out from firebase when certain buttons are pressed.
     * @param keyCode
     * @param event
     * @return
     */
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

    /**
     * @brief When user clicks the submit button. Intended to be clicked after user has entered building and number of seats.
     */
    public void activateSubmitBtnListener(){

        //Todo: Ensure that the user has selected a building and a number of seats needed

        submit_button = (Button)findViewById(R.id.button);
        submit_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MemberActivity searchResults = new MemberActivity();
                        //Set the setBuilding in MemberActivity
                        searchResults.setBuilding(buildingSpr.getSelectedItem().toString());
                        try {
                            //Set the setNumOfPeople in MemberActivity
                            searchResults.setNumOfPeople(Integer.parseInt(noOfPeopleTxt.getText().toString()));
                        } catch (NumberFormatException e) {
                            //Todo: disable characters to be inputted into number of people searching for. Then no need for try/catch
                            throw e;
                        }
                        Intent intent = new Intent(SearchActivity.this,MemberActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    }


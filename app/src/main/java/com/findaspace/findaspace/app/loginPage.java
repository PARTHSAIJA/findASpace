package com.findaspace.findaspace.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class loginPage extends AppCompatActivity {

    private static EditText username;
    private static EditText password;
    private static Button login_button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        LoginButton();
    }




        public void LoginButton(){
            username = (EditText)findViewById(R.id.editText3);
            password = (EditText)findViewById(R.id.editText4);
            login_button = (Button)findViewById(R.id.btnLogin);


            login_button.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (username.getText().toString().equals("12130555") && password.getText().toString().equals("root"))
                            {
                                Toast.makeText(loginPage.this,"Username and password is correct",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(loginPage.this,MainActivity.class);
                                startActivity(intent);
                            }

                            else if (username.getText().toString().equals("admin") && password.getText().toString().equals("root")){
                                Toast.makeText(loginPage.this,"Username and password is correct",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(loginPage.this,adminPage.class);
                                startActivity(intent);
                            }

                        else {
                                Toast.makeText(loginPage.this,"Username and password is NOT correct",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
        }

        //WRITE TO DB
        /*
         * Retrieve an instance of your database using getInstance()
         * and reference the location you want to write to.
         *
         * You can save a range of data types to the database this way, including Java objects.
         * When you save an object the responses from any getters will be saved as children of
         * this location.
         */
        // Write a message to the database
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("message");

        //myRef.setValue("Hello, World!");




}


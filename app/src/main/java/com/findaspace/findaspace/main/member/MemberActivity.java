package com.findaspace.findaspace.main.member;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.findaspace.findaspace.app.R;

public class MemberActivity extends Activity
{
    // Array of strings...
    ListView simpleList;
    String countryList[] = {"2.101 - 10 SEATS LEFT","2.102 - 12 SEATS LEFT","2.103 - 18 SEATS LEFT","2.104 - 22 SEATS LEFT","2.105 - 15 SEATS LEFT"};

    @Override   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      setContentView(R.layout.student_view);
        simpleList = (ListView)findViewById(R.id.simpleListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView, countryList);
        simpleList.setAdapter(arrayAdapter);
    }
}

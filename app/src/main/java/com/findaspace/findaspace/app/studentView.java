package com.findaspace.findaspace.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class studentView extends Activity {
    // Array of strings...
    ListView simpleList;
    String countryList[] = {"2.101 - 10 SEATS LEFT", "2.102 - 12 SEATS LEFT", "2.103 - 18 SEATS LEFT", "2.104 - 22 SEATS LEFT", "2.105 - 15 SEATS LEFT"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_view);
        simpleList = (ListView) findViewById(R.id.simpleListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView, countryList);
        simpleList.setAdapter(arrayAdapter);

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String space = ((TextView)view.findViewById(R.id.textView)).getText().toString();

                Toast.makeText(studentView.this, space, Toast.LENGTH_LONG).show();

                switchToMonitor(space);
            }
        });
    }

    private void switchToMonitor(String space) {
        Intent intent = new Intent();
        intent.setClass(this, SpaceMonitorActivity.class);
        intent.putExtra("space", space);
        startActivity(intent);
    }
}

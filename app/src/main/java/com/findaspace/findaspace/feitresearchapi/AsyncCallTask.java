package com.findaspace.findaspace.feitresearchapi;

import android.os.AsyncTask;

import com.findaspace.findaspace.readDB.RoomRecord;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

public class AsyncCallTask extends AsyncTask<Void, Void, LinkedList<PeopleCountRecord>> {

    public String datetime;
    public String peopleCount;
    public LinkedList<PeopleCountRecord> peopleCountRecord;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(String peopleCount) {
        this.peopleCount = peopleCount;
    }

    public AsyncCallTaskResponse delegate = null;

    public AsyncCallTask(AsyncCallTaskResponse delegate){
        this.delegate = delegate;
    }

    /**
     * @brief Async task to get the JSON from the FEIT People Count Sensor
     * @param voids
     * @return
     */
    @Override
    protected LinkedList<PeopleCountRecord> doInBackground(Void... voids) {
        try{
            //LinkedList<RoomRecord> utsRooms =;
            for (RoomRecord room:  delegate.getPeopleCount()) {
                System.out.println("UNITNO:" + room.UnitNo);


                //TODO: Currently API URL hardcoded. Need to pass in the API ParametersRecord to complete the URL string
                String sensorAPIURL = "http://eif-research.feit.uts.edu.au/api/json/?rFromDate=2018-09-16T18%3A05%3A00&rToDate=2018-09-18T18%3A05%3A00&rFamily=people&rSensor=+PC00.08+%28In%29";

                //Set up URL connection
                URL url = new URL(sensorAPIURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();       //Commence connection

                //Get the outputted URL contents and put into the buffer
                InputStream inputstream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputstream));

                //Transfer buffer into a string builder
                StringBuilder strBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    strBuilder.append(line);
                }

                //Convert the string building into a JSON Array
                JsonElement je = new JsonParser().parse(strBuilder.toString());
                JsonArray myArray = je.getAsJsonArray();

                //Get the last position in the JSON Array (most current people count)  eg - ["2018-09-18 18:00:00",88]
                System.out.println("OUTPUT: " + myArray.get(myArray.size()-1).isJsonObject());
                System.out.println("OUTPUT_JSON_Object: " + myArray.get(myArray.size()-1));
                String currentPeopleCountStr = myArray.get(myArray.size()-1).toString();

                //Extract the DateTime as a String - 2018-09-18 18:00:00
                String [] dateStrToken = currentPeopleCountStr.split ("\"");
                String dateStr = dateStrToken[1];
                System.out.println("OUTPUT_Date: " + dateStr);

                //Extract the PeopleCount as a String 88
                String peopleCountStr = (currentPeopleCountStr.substring(0, currentPeopleCountStr.length() - 1));
                peopleCountStr = peopleCountStr.substring(peopleCountStr.lastIndexOf(",") + 1);
                System.out.println("OUTPUT_PeopleCount: " + peopleCountStr);

                setDatetime(dateStr);
                setPeopleCount(peopleCountStr);

                PeopleCountRecord peopleCountDetails = new PeopleCountRecord
                (
                        "CB" + room.BuildingNo + room.LevelNo + room.RoomNo,
                        dateStr,
                        Integer.valueOf(peopleCountStr)
                );
                peopleCountRecord.add(peopleCountDetails);
            }

            return peopleCountRecord;

        } catch (MalformedURLException e) {
            System.out.println("ERROR ->" + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.out.println("ERROR ->" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(LinkedList<PeopleCountRecord> peopleCountRecord) {
        delegate.processFinish(peopleCountRecord);
    }
}
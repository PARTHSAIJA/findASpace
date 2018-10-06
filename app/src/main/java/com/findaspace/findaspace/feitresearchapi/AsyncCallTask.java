package com.findaspace.findaspace.feitresearchapi;

import android.os.AsyncTask;

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

public class AsyncCallTask extends AsyncTask<Void, Void, String> {

    public AsyncCallTaskResponse delegate = null;

    public AsyncCallTask(AsyncCallTaskResponse delegate){
        this.delegate = delegate;
    }

    /**
     * @brief
     * @param voids
     * @return
     */
    @Override
    protected String doInBackground(Void... voids) {
        try{
            //TODO: Currently API URL hardcoded
            String sensorAPIURL = "http://eif-research.feit.uts.edu.au/api/json/?rFromDate=2018-09-16T18%3A05%3A00&rToDate=2018-09-18T18%3A05%3A00&rFamily=people&rSensor=+PC00.08+%28In%29";

            URL url = new URL(sensorAPIURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream inputstream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputstream));

            StringBuilder strBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                strBuilder.append(line);
            }

            JsonElement je = new JsonParser().parse(strBuilder.toString());
            JsonArray myArray = je.getAsJsonArray();

            //["2018-09-18 18:00:00",88]
            System.out.println("OUTPUT: " + myArray.get(myArray.size()-1).isJsonObject());
            System.out.println("OUTPUT_JSON_Object: " + myArray.get(myArray.size()-1));
            String currentPeopleCountStr = myArray.get(myArray.size()-1).toString();

            String [] dateStrToken = currentPeopleCountStr.split ("\"");
            String dateStr = dateStrToken[1];
            System.out.println("OUTPUT_Date: " + dateStr);

//            Date date = new Date();
//            String stringDate = DateFormat.getDateTimeInstance().format(dateStr);
//            @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            df.parse(dateStr);
//
//            //Date date = new Date(df.);
//            System.out.println("OUTPUT_Time: " + DateFormat.getTimeInstance(DateFormat.MEDIUM).format(dateStr));

            String peopleCountStr = (currentPeopleCountStr.substring(0, currentPeopleCountStr.length() - 1));
            peopleCountStr = peopleCountStr.substring(peopleCountStr.lastIndexOf(",") + 1);
            System.out.println("OUTPUT_PeopleCount: " + peopleCountStr);
            //JSONObject obj = new JSONObject(myArray.get(myArray.size()-1));

//            for (JsonElement e : myArray)
//            {
//                System.out.println("Object:"+e.isJsonObject());
//            }

             return null;
         } catch (MalformedURLException e) {
            System.out.println("ERROR ->" + e.getMessage());
            e.printStackTrace();
         } catch (IOException e) {
            System.out.println("ERROR ->" + e.getMessage());
            e.printStackTrace();
         }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }
}
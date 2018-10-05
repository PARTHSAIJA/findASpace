package com.findaspace.findaspace.feitresearchapi;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class AsyncCallTask extends AsyncTask<Void, Void, String> {

    public AsyncCallTaskResponse delegate = null;

    public AsyncCallTask(AsyncCallTaskResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(Void... voids) {


        try{
            String sensorAPIURL = "http://eif-research.feit.uts.edu.au/api/json/?rFromDate=2018-09-16T18%3A05%3A00&rToDate=2018-09-18T18%3A05%3A00&rFamily=people&rSensor=+PC00.08+%28In%29";

            URL url = new URL(sensorAPIURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream inputstream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputstream));

            StringBuilder stringbuffer = new StringBuilder();

            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringbuffer.append(line);
            }

            //Type myListType = new TypeToken<List<PeopleCountRecord>>(){}.getType();
            //List<PeopleCountRecord> myList = new Gson().fromJson(stringbuffer.toString(), myListType);

//            for (Object f: myList) {
//                System.out.println("TEST:" + f.toString());
//            }

            JsonElement je = new JsonParser().parse(stringbuffer.toString());
            JsonArray myArray = je.getAsJsonArray();

            for (JsonElement e : myArray)
            {
                // Access the element as a JsonObject
                //JsonObject jo = e.getAsJsonObject();
                //System.out.println(jo.keySet());
//                // Get the `timestamp` element from the object
//                // since it's a number, we get it as a JsonPrimitive
//                JsonPrimitive tsPrimitive = jo.getAsJsonPrimitive("timestamp");
//
//                // get the primitive as a Java long
//                long timestamp = tsPrimitive.getAsLong();
//                System.out.println("Timestamp: " + timestamp);
            }

//
//            String finalJson = stringbuffer.toString();
//
//            JSONObject parentObject = new JSONObject(finalJson);
//            JSONArray parentArray = parentObject.getJSONArray("items");
//
//            List<PeopleCountRecord> PeopleCountRecordList = new ArrayList<>();
//            String idText = null;
//            Gson gson = new Gson();
//            for(int i=0; i<parentArray.size(); i++){
//                JsonObject finalObject = parentArray.(i);
//
//                PeopleCountRecord PeopleCountRecord = gson.fromJson(finalObject.toString(),PeopleCountRecord.class);
//
//                PeopleCountRecordList.add(PeopleCountRecord);
//            }

            // Connect to the URL using java's native library
//             URL url = new URL(sensorAPIURL);
//             URLConnection request = url.openConnection();
//             try {
//                request.connect();
//             } catch (Exception e) {
//                System.out.println("ERROR ->" + e.getMessage());
//                e.printStackTrace();
//             }
             // Convert to a JSON object to print data
//             JsonParser jp = new JsonParser(); //from gson
//             JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
//             JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
//             //String zipcode = rootobj.get("zip_code").getAsString(); //just grab the zipcode
//             System.out.println("KEY ->" + rootobj.keySet());

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

// try {
//         String sensorAPIURL = "https://eif-research.feit.uts.edu.au/api/json/?rFromDate=2018-09-16T18%3A05%3A00&rToDate=2018-09-18T18%3A05%3A00&rFamily=people&rSensor=+PC00.08+%28In%29";
//         URL url = new URL(sensorAPIURL);
//
////            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//////            connection.connect();
//////
//////            InputStream inputstream = connection.getInputStream();
//////            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputstream));
//////
//////            StringBuffer stringbuffer = new StringBuffer();
//////
//////            String line = "";
//////            while ((line = bufferedReader.readLine()) != null) {
//////                stringbuffer.append(line);
//////            }
//////
//////            String finalJson = stringbuffer.toString();
//////
//////            JSONObject parentObject = new JSONObject(finalJson);
//////            JSONArray parentArray = parentObject.getJSONArray("items");
//////
//////            List<BookInfoModel> bookInfoModelList = new ArrayList<>();
//////            String idText = null;
//////            Gson gson = new Gson();
//////            for(int i=0; i<parentArray.length(); i++){
//////                JSONObject finalObject = parentArray.getJSONObject(i);
//////
//////                BookInfoModel bookInfoModel = new gson.fromJson(finalObject.toString(),BookInfoModel.class);
//////
//////                bookInfoModelList.add(bookInfoModel);
//////            }
//
//         //         // Connect to the URL using java's native library
//         //URL url = new URL(sensorAPIURL);
//         URLConnection request = url.openConnection();
//         try {
//         request.connect();
//         } catch (Exception e) {
//         System.out.println("ERROR ->" + e.getMessage());
//         e.printStackTrace();
//         }
//         // Convert to a JSON object to print data
//         JsonParser jp = new JsonParser(); //from gson
//         JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
//         JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
//         //String zipcode = rootobj.get("zip_code").getAsString(); //just grab the zipcode
//         System.out.println(rootobj.keySet());
//
//         return null;
//         } catch (MalformedURLException e) {
//         e.printStackTrace();
//         } catch (IOException e) {
//         e.printStackTrace();
//         }
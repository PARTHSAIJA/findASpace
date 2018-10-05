package com.findaspace.findaspace.feitresearchapi;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
            String sensorAPIURL = "https://eif-research.feit.uts.edu.au/api/json/?rFromDate=2018-09-16T18%3A05%3A00&rToDate=2018-09-18T18%3A05%3A00&rFamily=people&rSensor=+PC00.08+%28In%29";

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

            String finalJson = stringbuffer.toString();

            JsonObject parentObject = new JsonObject(finalJson);
            JsonArray parentArray = parentObject.getJsonArray("items");

            List<PeopleCountRecord> PeopleCountRecordList = new ArrayList<>();
            String idText = null;
            Gson gson = new Gson();
            for(int i=0; i<parentArray.length(); i++){
                JsonObject finalObject = parentArray.getJsonObject(i);

                PeopleCountRecord PeopleCountRecord = new gson.fromJson(finalObject.toString(),PeopleCountRecord.class);

                PeopleCountRecordList.add(PeopleCountRecord);
            }

//            // Connect to the URL using java's native library
//             URL url = new URL(sensorAPIURL);
//             URLConnection request = url.openConnection();
//             try {
//             request.connect();
//             } catch (Exception e) {
//             System.out.println("ERROR ->" + e.getMessage());
//             e.printStackTrace();
//             }
//             // Convert to a JSON object to print data
//             JsonParser jp = new JsonParser(); //from gson
//             JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
//             JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
//             //String zipcode = rootobj.get("zip_code").getAsString(); //just grab the zipcode
//             System.out.println(rootobj.keySet());

             return null;
         } catch (MalformedURLException e) {
         e.printStackTrace();
         } catch (IOException e) {
         e.printStackTrace();
         } catch (JSONException e) {
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
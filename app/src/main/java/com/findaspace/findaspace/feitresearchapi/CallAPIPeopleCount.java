package com.findaspace.findaspace.feitresearchapi;

public class CallAPIPeopleCount implements AsyncCallTaskResponse{

    /**
     * @brief Create API request for each room to determine how many in the room
     */
    public void CallAPIPeopleCount(){

        new AsyncCallTask(this).execute();
    }

    //this override the implemented method from AsyncResponse
    @Override
    public void processFinish(String output){
        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.
    }
}

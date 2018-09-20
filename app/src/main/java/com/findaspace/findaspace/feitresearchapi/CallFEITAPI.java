package com.findaspace.findaspace.feitresearchapi;

import java.util.LinkedList;

public class CallFEITAPI {
    private LinkedList<APIParametersRecord> inputAPILinkedList;
    private LinkedList<PeopleCountRecord> outputAPILinkedList;

    //https://eif-research.feit.uts.edu.au/api/json/?rFromDate=2018-09-16T18%3A05%3A00&rToDate=2018-09-18T18%3A05%3A00&rFamily=people&rSensor=+PC00.08+%28In%29
    public static final String urlAPI = "https://eif-research.feit.uts.edu.au/api/";
    public static final String outputFormat = "json/?";

    public void getInputParamenters(String rFromDate, String rToDate, String rFamily, String rSensor, String rSubSensor, String rBuilding, String rRoom)
    {
        inputAPILinkedList.add(new APIParametersRecord(
            rFromDate,
            rToDate,
            rFamily,
            rSensor,
            rSubSensor,
            rBuilding,
            rRoom
        ));
    }
}

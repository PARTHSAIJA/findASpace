package com.findaspace.findaspace.feitresearchapi;

import java.util.LinkedList;

public interface AsyncCallTaskResponse {
    void processFinish(LinkedList<PeopleCountRecord> peopleCountRecord);
}

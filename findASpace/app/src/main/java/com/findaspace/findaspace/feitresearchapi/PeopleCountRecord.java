package com.findaspace.findaspace.feitresearchapi;

import java.util.Date;

public class PeopleCountRecord {
    public String RecordDateTime;
    public Integer RecordPeopleCount;

    public PeopleCountRecord(String recordDateTime, Integer recordPeopleCount) {
        RecordDateTime = recordDateTime;
        RecordPeopleCount = recordPeopleCount;
    }

    public String getRecordDateTime() {
        return RecordDateTime;
    }

    public void setRecordDateTime(String recordDateTime) {
        RecordDateTime = recordDateTime;
    }

    public Integer getRecordPeopleCount() {
        return RecordPeopleCount;
    }

    public void setRecordPeopleCount(Integer recordPeopleCount) {
        RecordPeopleCount = recordPeopleCount;
    }
}

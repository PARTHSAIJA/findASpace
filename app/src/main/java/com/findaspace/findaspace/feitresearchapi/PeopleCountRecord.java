package com.findaspace.findaspace.feitresearchapi;

import java.util.Date;

public class PeopleCountRecord {
    public Date RecordDateTime;
    public Integer RecordPeopleCount;

    public PeopleCountRecord(Date recordDateTime, Integer recordPeopleCount) {
        RecordDateTime = recordDateTime;
        RecordPeopleCount = recordPeopleCount;
    }

    public Date getRecordDateTime() {
        return RecordDateTime;
    }

    public void setRecordDateTime(Date recordDateTime) {
        RecordDateTime = recordDateTime;
    }

    public Integer getRecordPeopleCount() {
        return RecordPeopleCount;
    }

    public void setRecordPeopleCount(Integer recordPeopleCount) {
        RecordPeopleCount = recordPeopleCount;
    }
}

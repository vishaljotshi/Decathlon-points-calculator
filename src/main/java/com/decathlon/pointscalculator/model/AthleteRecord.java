package com.decathlon.pointscalculator.model;

import com.decathlon.pointscalculator.event.EventName;
import com.decathlon.pointscalculator.exceptions.InvalidRecordColumnCountException;

import java.util.LinkedHashMap;
import java.util.Map;

public class AthleteRecord {
    public static final int TOTAL_RECORDS_COUNT = 11;
    private String  name;
    private Map<EventName,String> eventScoreMap=new LinkedHashMap<>();
    public AthleteRecord(String recordLine, String separator) {
        String[] records = recordLine.split(separator);
        validateRecordsCount(records);
        populateEventScoreMap(records);
    }

    private void populateEventScoreMap(String[] records) {
        int index=0;
        this.name= records[index++];
        eventScoreMap.put(EventName.HUNDRED_M, records[index++]);
        eventScoreMap.put(EventName.LONG_JUMP, records[index++]);
        eventScoreMap.put(EventName.SHOT_PUT, records[index++]);
        eventScoreMap.put(EventName.HIGH_JUMP, records[index++]);
        eventScoreMap.put(EventName.FOUR_HUNDRED_METERS, records[index++]);
        eventScoreMap.put(EventName.HUNDRED_TEN_M_HURDLES, records[index++]);
        eventScoreMap.put(EventName.DISCUS_THROW, records[index++]);
        eventScoreMap.put(EventName.POLE_VAULT, records[index++]);
        eventScoreMap.put(EventName.JAVELIN_THROW, records[index++]);
        eventScoreMap.put(EventName.FIFTEEN_HUNDRED_M, records[index]);
    }

    private void validateRecordsCount(String[] records) {
        if(records.length!= TOTAL_RECORDS_COUNT){
            throw new InvalidRecordColumnCountException("Incorrect record count:"+records.length+", Skipping this record");
        }
    }

    public String getName() {
        return name;
    }

    public Map<EventName, String> getEventScoreMap() {
        return eventScoreMap;
    }
}

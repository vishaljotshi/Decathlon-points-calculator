package com.decathlon.pointscalculator.model;

public class Record {
    private static final String CSV_SEPERATOR = ";";
    private String  name;
    private Timings timings;
    public Record(String recordLine) {
        String[] records = recordLine.split(CSV_SEPERATOR);
        if(records.length!=11){
            throw new RuntimeException("Incorrect record size, Skipping this record");
        }
        this.name=records[0];
        this.timings=new Timings(records);
    }

    public Timings getTimings() {
        return timings;
    }

    public String getName() {
        return name;
    }
}

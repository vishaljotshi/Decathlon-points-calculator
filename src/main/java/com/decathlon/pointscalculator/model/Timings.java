package com.decathlon.pointscalculator.model;

import com.decathlon.pointscalculator.model.event.Event;
import com.decathlon.pointscalculator.model.event.impl.Event100m;

import java.util.ArrayList;
import java.util.List;

public class Timings {
    private List<Event> events=new ArrayList<>();

    public Timings(String[] records) {
        int index=1;
        events.add(new Event100m(records[index++]));
    }

    public List<Event> getEvents() {
        return events;
    }
}

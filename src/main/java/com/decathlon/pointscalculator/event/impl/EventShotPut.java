package com.decathlon.pointscalculator.event.impl;

import com.decathlon.pointscalculator.event.Event;

public class EventShotPut implements Event {
    public EventShotPut() {
        // default constructor
    }

    @Override
    public String getName() {
        return "Shot Put";
    }

    @Override
    public Float getA() {
        return 51.39f;
    }

    @Override
    public Float getB() {
        return 1.5f;
    }

    @Override
    public Float getC() {
        return 1.05f;
    }

    @Override
    public Integer getPoints(String score) {
        return  (int)Math.floor(getA() * Math.pow(Float.valueOf(score) - getB(), getC()));
    }
}

package com.decathlon.pointscalculator.event.impl;

import com.decathlon.pointscalculator.event.Event;

public class EventDiscusThrow implements Event {
    public EventDiscusThrow() {
        // default constructor
    }

    @Override
    public String getName() {
        return "Discus Throw";
    }

    @Override
    public Float getA() {
        return 12.91f;
    }

    @Override
    public Float getB() {
        return 4f;
    }

    @Override
    public Float getC() {
        return 1.1f;
    }

    @Override
    public Integer getPoints(String score) {
        return  (int)Math.floor(getA() * Math.pow( Float.valueOf(score) - getB(), getC()));
    }
}

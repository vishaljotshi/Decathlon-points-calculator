package com.decathlon.pointscalculator.event.impl;

import com.decathlon.pointscalculator.event.Event;

public class EventJavelinThrow implements Event {
    public EventJavelinThrow() {
    }

    @Override
    public String getName() {
        return "Javelin Throw";
    }

    @Override
    public Float getA() {
        return 10.14f;
    }

    @Override
    public Float getB() {
        return 7f;
    }

    @Override
    public Float getC() {
        return 1.08f;
    }

    @Override
    public Integer getPoints(String score) {
        Integer calculatedPoints = (int)Math.floor(getA() * Math.pow(Float.valueOf(score) - getB(), getC()));
        return calculatedPoints;
    }
}

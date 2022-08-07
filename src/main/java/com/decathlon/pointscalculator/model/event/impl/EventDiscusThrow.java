package com.decathlon.pointscalculator.model.event.impl;

import com.decathlon.pointscalculator.model.event.Event;

public class EventDiscusThrow implements Event {
    public EventDiscusThrow() {
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
        Integer calculatedPoints = (int)Math.floor(getA() * Math.pow( Float.valueOf(score) - getB(), getC()));
        return calculatedPoints;
    }
}

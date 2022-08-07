package com.decathlon.pointscalculator.model.event.impl;

import com.decathlon.pointscalculator.model.event.Event;

public class EventShotPut implements Event {
    public EventShotPut() {
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
        Integer calculatedPoints = (int)Math.floor(getA() * Math.pow(Float.valueOf(score) - getB(), getC()));
        return calculatedPoints;
    }
}

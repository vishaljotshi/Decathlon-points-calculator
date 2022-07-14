package com.decathlon.pointscalculator.model.event.impl;

import com.decathlon.pointscalculator.model.event.Event;

public class Event100m implements Event {
    private Float score;
    public Event100m(String value) {
        score=Float.valueOf(value);
    }

    @Override
    public String getName() {
        return "100 meters";
    }

    @Override
    public Float getA() {
        return 25.4347f;
    }

    @Override
    public Float getB() {
        return 18f;
    }

    @Override
    public Float getC() {
        return 1.81f;
    }

    @Override
    public Integer getPoints() {
        Integer calculatedPoints = Math.toIntExact(Math.round(getA() * Math.pow(getB() - score, getC())));
        return calculatedPoints;
    }
}

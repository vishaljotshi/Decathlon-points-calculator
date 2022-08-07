package com.decathlon.pointscalculator.model.event.impl;

import com.decathlon.pointscalculator.model.event.Event;

public class Event100m implements Event {
    public Event100m() {
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
    public Integer getPoints(String score) {
        Integer calculatedPoints = (int)Math.floor(getA() * Math.pow(getB() - Float.valueOf(score), getC()));
        return calculatedPoints;
    }
}

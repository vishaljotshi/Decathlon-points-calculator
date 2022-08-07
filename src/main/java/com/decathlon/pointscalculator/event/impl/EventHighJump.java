package com.decathlon.pointscalculator.event.impl;

import com.decathlon.pointscalculator.event.Event;

public class EventHighJump implements Event {
    public EventHighJump() {
    }

    @Override
    public String getName() {
        return "High Jump";
    }

    @Override
    public Float getA() {
        return 0.8465f;
    }

    @Override
    public Float getB() {
        return 75f;
    }

    @Override
    public Float getC() {
        return 1.42f;
    }

    @Override
    public Integer getPoints(String score) {
        Integer calculatedPoints = (int)Math.floor(getA() * Math.pow((Float.valueOf(score)*100) - getB(), getC()));
        return calculatedPoints;
    }
}

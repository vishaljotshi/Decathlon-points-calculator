package com.decathlon.pointscalculator.event.impl;

import com.decathlon.pointscalculator.event.Event;

public class Event400m implements Event {
    public Event400m() {
    }

    @Override
    public String getName() {
        return "400 Meters";
    }

    @Override
    public Float getA() {
        return 1.53775f;
    }

    @Override
    public Float getB() {
        return 82f;
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

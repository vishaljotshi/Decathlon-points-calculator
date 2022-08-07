package com.decathlon.pointscalculator.event.impl;

import com.decathlon.pointscalculator.event.Event;

public class Event110mHurdles implements Event {
    public Event110mHurdles() {
    }

    @Override
    public String getName() {
        return "100 meters Hurdles";
    }

    @Override
    public Float getA() {
        return 5.74352f;
    }

    @Override
    public Float getB() {
        return 28.5f;
    }

    @Override
    public Float getC() {
        return 1.92f;
    }

    @Override
    public Integer getPoints(String score) {
        Integer calculatedPoints = (int)Math.floor(getA() * Math.pow(getB() - Float.valueOf(score), getC()));
        return calculatedPoints;
    }
}

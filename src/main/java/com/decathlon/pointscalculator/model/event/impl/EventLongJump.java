package com.decathlon.pointscalculator.model.event.impl;

import com.decathlon.pointscalculator.model.event.Event;

public class EventLongJump implements Event {
    public EventLongJump() {
    }

    @Override
    public String getName() {
        return "Long Jump";
    }

    @Override
    public Float getA() {
        return 0.14354f;
    }

    @Override
    public Float getB() {
        return 220f;
    }

    @Override
    public Float getC() {
        return 1.4f;
    }

    @Override
    public Integer getPoints(String score) {
        Integer calculatedPoints = (int)Math.floor(getA() * Math.pow((Float.valueOf(score)*100) - getB() , getC()));
        return calculatedPoints;
    }
}

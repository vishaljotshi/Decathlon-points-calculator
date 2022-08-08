package com.decathlon.pointscalculator.event.impl;

import com.decathlon.pointscalculator.event.Event;

public class EventLongJump implements Event {
    public EventLongJump() {
        // default constructor
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
        return  (int)Math.floor(getA() * Math.pow((Float.valueOf(score)*100) - getB() , getC()));
    }
}

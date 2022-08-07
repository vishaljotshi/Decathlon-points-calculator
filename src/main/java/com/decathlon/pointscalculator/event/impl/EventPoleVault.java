package com.decathlon.pointscalculator.event.impl;

import com.decathlon.pointscalculator.event.Event;

public class EventPoleVault implements Event {
    public EventPoleVault() {
    }

    @Override
    public String getName() {
        return "Pole Vault";
    }

    @Override
    public Float getA() {
        return 0.2797f;
    }

    @Override
    public Float getB() {
        return 100f;
    }

    @Override
    public Float getC() {
        return 1.35f;
    }

    @Override
    public Integer getPoints(String score) {
        Integer calculatedPoints = (int)Math.floor(getA() * Math.pow((Float.valueOf(score)*100) - getB(), getC()));
        return calculatedPoints;
    }
}

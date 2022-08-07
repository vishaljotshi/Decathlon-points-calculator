package com.decathlon.pointscalculator.model.event.impl;

import com.decathlon.pointscalculator.model.event.Event;

public class Event1500m implements Event {
    public Event1500m() {
    }

    @Override
    public String getName() {
        return "1500 meters";
    }

    @Override
    public Float getA() {
        return 0.03768f;
    }

    @Override
    public Float getB() {
        return 480f;
    }

    @Override
    public Float getC() {
        return 1.85f;
    }

    @Override
    public Integer getPoints(String score) {

        String[] splittedScore = score.split(":");
        if (splittedScore.length!=2){
            throw new RuntimeException(String.format("Invalid format for "+getName()+" event score value"));
        }
        float scoreInSeconds=(Float.valueOf(splittedScore[0])*60f)+Float.parseFloat(splittedScore[1]);

        Integer calculatedPoints = (int)Math.floor(getA() * Math.pow(getB() - scoreInSeconds, getC()));
        return calculatedPoints;
    }
}

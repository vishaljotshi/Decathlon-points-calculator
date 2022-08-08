package com.decathlon.pointscalculator.event.impl;

import com.decathlon.pointscalculator.event.Event;
import com.decathlon.pointscalculator.exceptions.InvalidScoreFormatException;

import java.util.logging.Logger;

public class Event1500m implements Event {
    Logger logger=Logger.getLogger(Event1500m.class.getName());
    public Event1500m() {
        // default constructor
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
            throw new InvalidScoreFormatException(String.format("Invalid format for %s event score value",getName()));
        }
        float scoreInSeconds=(Float.valueOf(splittedScore[0])*60f)+Float.parseFloat(splittedScore[1]);

        return  (int)Math.floor(getA() * Math.pow(getB() - scoreInSeconds, getC()));

    }
}

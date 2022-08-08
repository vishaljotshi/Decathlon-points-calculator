package com.decathlon.pointscalculator.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Result {
    private String eventName;
    private String score;

    Result(){
    }
    public Result(String eventName,String score){
        this.eventName=eventName;
        this.score=score;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}

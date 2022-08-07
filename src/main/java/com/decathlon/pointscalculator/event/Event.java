package com.decathlon.pointscalculator.event;

public interface Event {
    public String getName();
    public Float getA();
    public Float getB();
    public Float getC();
    public Integer getPoints(String score);
}

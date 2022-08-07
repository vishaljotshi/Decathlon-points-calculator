package com.decathlon.pointscalculator.model;

public class Athlete {
    private String name;
    private Integer points;
    private String position;

    public Athlete(String name, Integer points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Athlete{" +
                "name='" + name + '\'' +
                ", points=" + points +
                ", position='" + position + '\'' +
                "}\n";
    }
}

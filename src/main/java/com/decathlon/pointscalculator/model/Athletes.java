package com.decathlon.pointscalculator.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Athletes")
public class Athletes {
    private List<Athlete> athlete;
    public Athletes(){

    }
    public Athletes(List<Athlete> athletes) {
        this.athlete=athletes;
    }

    public void setAthletes(List<Athlete> athletes) {
        this.athlete = athletes;
    }

    @XmlElement(name = "Athlete")
    public List<Athlete> getAthlete() {
        return athlete;
    }
}

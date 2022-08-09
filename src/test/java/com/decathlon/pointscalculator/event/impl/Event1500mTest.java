package com.decathlon.pointscalculator.event.impl;

import com.decathlon.pointscalculator.exceptions.InvalidScoreFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Event1500mTest {

    @Test
    void event1500m_valid() {
        Event1500m event1500m = new Event1500m();
        Integer points = event1500m.getPoints("6:51.01");
        assertEquals(95, points, "Calculated points for 1500 meter event");
    }

    @Test
    void event1500m_invalidScore() {
        Event1500m event1500m = new Event1500m();

        Throwable exception = assertThrows(InvalidScoreFormatException.class, () -> event1500m.getPoints("6-51.01"));
        assertEquals("Invalid format for 1500 meters event score value", exception.getMessage());

    }
}

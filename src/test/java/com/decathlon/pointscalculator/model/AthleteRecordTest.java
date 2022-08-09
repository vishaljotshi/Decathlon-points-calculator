package com.decathlon.pointscalculator.model;

import com.decathlon.pointscalculator.exceptions.InvalidRecordColumnCountException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AthleteRecordTest {

    @Test
    void createAthleteRecord_success() {
        AthleteRecord record = new AthleteRecord("Jaan Lepp;13.75;4.84;10.12;1.50;68.44;19.18;30.85;2.80;33.88;6:22.75 ", ";");
        assertEquals("Jaan Lepp", record.getName(), "Verify athlete name");
    }

    @Test
    void createAthleteRecord_invalidRecords() {
        Throwable exception = assertThrows(InvalidRecordColumnCountException.class,
                () -> new AthleteRecord("Jaan Lepp;13.75;4.84;10.12;68.44;19.18;30.85;2.80;33.88;6:22.75 ", ";")); //Less number of records);
        assertEquals("Incorrect record count:10, Skipping this record", exception.getMessage(), "Verify Invalid record skipping");
    }
}

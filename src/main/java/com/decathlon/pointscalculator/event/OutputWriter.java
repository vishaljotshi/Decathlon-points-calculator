package com.decathlon.pointscalculator.event;

import com.decathlon.pointscalculator.model.Athletes;

public interface OutputWriter {

    public void convertAndWrite(Athletes athletes);
}

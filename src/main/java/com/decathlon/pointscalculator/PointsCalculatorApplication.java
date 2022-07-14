package com.decathlon.pointscalculator;


import com.decathlon.pointscalculator.model.Record;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PointsCalculatorApplication {

	public static void main(String[] args) throws IOException {
		Files.lines(Paths.get("/Users/vishaljotshi/Downloads/kuehne nagel/results-custom1.csv"))
				.filter(recordLine->recordLine!=null && recordLine.length()!=0)
				.map(recordLine-> getRecord(recordLine))
				.forEach(record->
						System.out.println(record.getName() + " :: "+record.getTimings().getEvents().get(0).getPoints()));
	}

	private static Record getRecord(String recordLine) {
		try {
			return new Record(recordLine);
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		return null; //TODO return optional
	}

}

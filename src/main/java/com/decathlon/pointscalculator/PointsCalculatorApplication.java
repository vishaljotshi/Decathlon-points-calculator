package com.decathlon.pointscalculator;


import com.decathlon.pointscalculator.event.EventName;
import com.decathlon.pointscalculator.event.impl.*;
import com.decathlon.pointscalculator.model.Athlete;
import com.decathlon.pointscalculator.model.Athletes;
import com.decathlon.pointscalculator.model.Record;
import com.decathlon.pointscalculator.event.Event;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class PointsCalculatorApplication {

	private static Map<EventName, Event> eventMap=new LinkedHashMap<>();
	public static void main(String[] args) throws IOException, ParseException {
		AtomicInteger currentPosition=new AtomicInteger(1);
		initializeEventMap();

		Athletes athletes = Files.lines(Paths.get("/Users/vishaljotshi/Downloads/kuehne nagel/results-custom1.csv"))
				.filter(recordLine -> recordLine.length() > 0)
				.map(recordLine -> getRecord(recordLine))
				.filter(optionalRecord -> optionalRecord.isPresent())
				.map(optionalRecord -> optionalRecord.get())
				.map(record -> getPerformance(record))
				.collect(groupingBy(athlete -> athlete.getPoints()))
				.entrySet()
				.stream()
				.sorted(Comparator.comparingInt((Map.Entry<Integer, List<Athlete>> value) -> value.getKey()).reversed())
				.flatMap(entry -> {
					String position = IntStream.range(currentPosition.get(), currentPosition.get() + entry.getValue().size())
							.mapToObj(val -> String.valueOf(val))
							.peek(val -> currentPosition.incrementAndGet())
							.collect(joining("-"));
					entry.getValue().stream().forEach(athlete -> athlete.setPosition(position));
					return entry.getValue().stream();
				})
				.collect(collectingAndThen(toList(),(List<Athlete>athletes1) -> new Athletes(athletes1)));
		String generatedXMLString = generateXMLString(athletes);
		writeOutput(generatedXMLString);
	}

	public static void writeOutput(String content) {
		try {
			Path path = Paths.get("/Users/vishaljotshi/Downloads/kuehne nagel/output.xml");
			byte[] strToBytes = content.getBytes();
			Files.write(path, strToBytes);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static String generateXMLString(Athletes athletes) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Athletes.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(athletes, sw);
			String xmlContent = sw.toString();
			//System.out.println( xmlContent );
			return xmlContent;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	private static Athlete getPerformance(Record record) {
		String name = record.getName();
		int points = record.getEventScoreMap().entrySet().stream()
				.mapToInt(eventEntry -> {
							return eventMap.get(eventEntry.getKey())
									.getPoints(eventEntry.getValue());
						}
				)
				.sum();
		Athlete athlete = new Athlete(name, points);
		return athlete;
	}

	private static void initializeEventMap() {
		eventMap.put(EventName.HUNDRED_M,new Event100m() );
		eventMap.put(EventName.LONG_JUMP,new EventLongJump());
		eventMap.put(EventName.SHOT_PUT,new EventShotPut());
		eventMap.put(EventName.HIGH_JUMP,new EventHighJump());
		eventMap.put(EventName.FOUR_HUNDRED_METERS,new Event400m());
		eventMap.put(EventName.HUNDRED_TEN_M_HURDLES,new Event110mHurdles() );
		eventMap.put(EventName.DISCUS_THROW, new EventDiscusThrow());
		eventMap.put(EventName.POLE_VAULT, new EventPoleVault());
		eventMap.put(EventName.JAVELIN_THROW, new EventJavelinThrow());
		eventMap.put(EventName.FIFTEEN_HUNDRED_M, new Event1500m());
	}

	private static Optional<Record> getRecord(String recordLine) {
		try {
			return Optional.of(new Record(recordLine));
		} catch (Exception e){
			System.out.println("Exception occurred while reading records : "+ e.getMessage());
		}
		return Optional.empty(); //TODO return optional
	}
}

package com.decathlon.pointscalculator;


import com.decathlon.pointscalculator.model.Athlete;
import com.decathlon.pointscalculator.model.Record;
import com.decathlon.pointscalculator.model.event.Event;
import com.decathlon.pointscalculator.model.event.impl.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class PointsCalculatorApplication {

	private static Map<EventName, Event> eventMap=new LinkedHashMap<>();
	public static void main(String[] args) throws IOException, ParseException {
		initializeEventMap();

		List<Athlete> athletes = Files.lines(Paths.get("/Users/vishaljotshi/Downloads/kuehne nagel/results-custom1.csv"))
				.filter(recordLine -> recordLine.length() > 0)
				.map(recordLine -> getRecord(recordLine))
				.filter(optionalRecord -> optionalRecord.isPresent())
				.map(optionalRecord -> optionalRecord.get())
				.map(record -> getPerformance(record))
				.sorted(Comparator.comparingInt(Athlete::getPoints).reversed())
				.collect(toList());

		List<Athlete> athList = IntStream.range(0, athletes.size())
				.mapToObj(num -> {
					Athlete athlete = athletes.get(num);
					athlete.setPosition(String.valueOf(num+1));
					return athlete;
				}).collect(toList());

		AtomicInteger currentPosition=new AtomicInteger(1);
		athList.stream()
				.collect(groupingBy(athlete -> athlete.getPoints()))
				.entrySet()
				.stream()
				.sorted(Comparator.comparingInt((Map.Entry<Integer,List<Athlete>> value) -> value.getKey()).reversed())
				.flatMap(entry->{
					String position=IntStream.range(currentPosition.get(),currentPosition.get()+entry.getValue().size())
							.mapToObj(val->String.valueOf(val))
							.peek(val->currentPosition.incrementAndGet())
							.collect(joining("-"));
					entry.getValue().stream().forEach(athlete -> athlete.setPosition(position));
					return entry.getValue().stream();
				})
				.collect(toList())
				.forEach(System.out::print);



	}

	private static Athlete getPerformance(Record record) {
		String name = record.getName();
		int points = record.getEventScoreMap().entrySet().stream()
				.mapToInt(eventEntry -> {
					System.out.println(eventEntry.getKey()+" "+eventMap.get(eventEntry.getKey())
							.getPoints(eventEntry.getValue()));
							return eventMap.get(eventEntry.getKey())
									.getPoints(eventEntry.getValue());
						}
				)
				.sum();
		System.out.println(String.format("Name : %s, Points : %s", name, points));
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
			System.out.println("Exception occured while reading records : "+ e.getMessage());
		}
		return Optional.empty(); //TODO return optional
	}

}

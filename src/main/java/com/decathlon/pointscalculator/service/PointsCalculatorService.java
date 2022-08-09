package com.decathlon.pointscalculator.service;

import com.decathlon.pointscalculator.event.Event;
import com.decathlon.pointscalculator.event.EventName;
import com.decathlon.pointscalculator.event.OutputWriter;
import com.decathlon.pointscalculator.event.impl.*;
import com.decathlon.pointscalculator.model.Athlete;
import com.decathlon.pointscalculator.model.Athletes;
import com.decathlon.pointscalculator.model.AthleteRecord;
import com.decathlon.pointscalculator.model.Result;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class PointsCalculatorService {
    Logger logger=Logger.getLogger(PointsCalculatorService.class.getName());
    private static Map<EventName, Event> eventMap = new LinkedHashMap<>();
    private String inputFilePath;
    private OutputWriter outputWriter;
    private String csvDelimiter;

    public PointsCalculatorService(String inputFilePath, String csvDelimiter, OutputWriter outputWriter) {
        this.inputFilePath=inputFilePath;
        this.outputWriter=outputWriter;
        this.csvDelimiter=csvDelimiter;
    }

    private void initializeEventMap() {
        eventMap.put(EventName.HUNDRED_M, new Event100m());
        eventMap.put(EventName.LONG_JUMP, new EventLongJump());
        eventMap.put(EventName.SHOT_PUT, new EventShotPut());
        eventMap.put(EventName.HIGH_JUMP, new EventHighJump());
        eventMap.put(EventName.FOUR_HUNDRED_METERS, new Event400m());
        eventMap.put(EventName.HUNDRED_TEN_M_HURDLES, new Event110mHurdles());
        eventMap.put(EventName.DISCUS_THROW, new EventDiscusThrow());
        eventMap.put(EventName.POLE_VAULT, new EventPoleVault());
        eventMap.put(EventName.JAVELIN_THROW, new EventJavelinThrow());
        eventMap.put(EventName.FIFTEEN_HUNDRED_M, new Event1500m());
    }

    public void process() throws IOException {
        AtomicInteger currentPosition = new AtomicInteger(1);
        initializeEventMap();

        Athletes athletes=null;
        try(Stream<String> inputFileStream=Files.lines(Paths.get(inputFilePath))) {
            athletes=inputFileStream
                    .filter(recordLine -> recordLine.length() > 0)
                    .map(this::getRecord)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(this::getPerformance)
                    .collect(groupingBy(Athlete::getPoints))
                    .entrySet()
                    .stream()
                    .sorted(Comparator.comparingInt((Map.Entry<Integer, List<Athlete>> value) -> value.getKey()).reversed())
                    .flatMap(entry -> {
                        String position = getPosition(currentPosition, entry);
                        entry.getValue().stream().forEach(athlete -> athlete.setPosition(position));
                        return entry.getValue().stream();
                    })
                    .collect(collectingAndThen(toList(), Athletes::new));
        }
        outputWriter.convertAndWrite(athletes);
    }

    private String getPosition(AtomicInteger currentPosition, Map.Entry<Integer, List<Athlete>> entry) {
        return IntStream.range(currentPosition.get(), currentPosition.get() + entry.getValue().size())
                .mapToObj(i -> {
                    currentPosition.incrementAndGet();
                    return String.valueOf(i);})
                .collect(joining("-"));
    }

    private Athlete getPerformance(AthleteRecord athleteRecord) {
        String name = athleteRecord.getName();
        List<Result> results=new ArrayList<>();
        int points = athleteRecord.getEventScoreMap().entrySet().stream()
                .mapToInt(eventEntry -> {
                            results.add(new Result(eventMap.get(eventEntry.getKey()).getName(),eventEntry.getValue()));
                            return eventMap.get(eventEntry.getKey())
                                    .getPoints(eventEntry.getValue());
                        }
                )
                .sum();
        return new Athlete(name, points,results);
    }

    private Optional<AthleteRecord> getRecord(String recordLine) {
        try {
            return Optional.of(new AthleteRecord(recordLine,csvDelimiter));
        } catch (Exception e) {
            logger.log(Level.SEVERE,String.format("Exception occurred while reading record : %s",e.getMessage()));
        }
        return Optional.empty();
    }
}



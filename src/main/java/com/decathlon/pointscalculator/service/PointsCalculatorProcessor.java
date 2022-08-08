package com.decathlon.pointscalculator.service;

import com.decathlon.pointscalculator.event.Event;
import com.decathlon.pointscalculator.event.EventName;
import com.decathlon.pointscalculator.event.impl.*;
import com.decathlon.pointscalculator.model.Athlete;
import com.decathlon.pointscalculator.model.Athletes;
import com.decathlon.pointscalculator.model.Record;
import com.decathlon.pointscalculator.model.Result;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toList;

public class PointsCalculatorProcessor {
    private static Map<EventName, Event> eventMap = new LinkedHashMap<>();
    private String inputFilePath;
    private String outputFilePath;
    private String csvDelimiter;

    public PointsCalculatorProcessor(String inputFilePath, String outputFilePath, String csvDelimiter) {
        this.inputFilePath=inputFilePath;
        this.outputFilePath=outputFilePath;
        this.csvDelimiter=csvDelimiter;
    }

    public void process() throws IOException {
        AtomicInteger currentPosition = new AtomicInteger(1);
        initializeEventMap();

        Athletes athletes = Files.lines(Paths.get(inputFilePath))
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
                    String position = getPosition(currentPosition, entry);
                    entry.getValue().stream().forEach(athlete -> athlete.setPosition(position));
                    return entry.getValue().stream();
                })
                .collect(collectingAndThen(toList(), (List<Athlete> athletes1) -> new Athletes(athletes1)));
        String generatedXMLString = generateXMLString(athletes);
        writeOutput(generatedXMLString);
    }

    private String getPosition(AtomicInteger currentPosition, Map.Entry<Integer, List<Athlete>> entry) {
        return IntStream.range(currentPosition.get(), currentPosition.get() + entry.getValue().size())
                .mapToObj(val -> String.valueOf(val))
                .peek(val -> currentPosition.incrementAndGet())
                .collect(joining("-"));
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

    public void writeOutput(String content) {
        try {
            Path path = Paths.get(outputFilePath);
            byte[] strToBytes = content.getBytes();
            Files.write(path, strToBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateXMLString(Athletes athletes) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Athletes.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(athletes, sw);
            String xmlContent = sw.toString();
            return xmlContent;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    private Athlete getPerformance(Record record) {
        String name = record.getName();
        List<Result> results=new ArrayList<>();
        int points = record.getEventScoreMap().entrySet().stream()
                .mapToInt(eventEntry -> {
                            results.add(new Result(eventMap.get(eventEntry.getKey()).getName(),eventEntry.getValue()));
                            return eventMap.get(eventEntry.getKey())
                                    .getPoints(eventEntry.getValue());
                        }
                )
                .sum();
        Athlete athlete = new Athlete(name, points,results);
        return athlete;
    }

    private Optional<Record> getRecord(String recordLine) {
        try {
            return Optional.of(new Record(recordLine,csvDelimiter));
        } catch (Exception e) {
            System.out.println("Exception occurred while reading records : " + e.getMessage());
        }
        return Optional.empty();
    }
}



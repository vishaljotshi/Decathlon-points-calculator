package com.decathlon.pointscalculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Collectors;

class PointsCalculatorApplicationTests {

    @Test
    void calculatePoints_success() throws IOException {
        String randomFileName = UUID.randomUUID().toString();
        ClassLoader classLoader = getClass().getClassLoader();
        String inputFilePath = new File(classLoader.getResource("sample-input.csv").getFile()).getAbsolutePath();
        String expectedFilePath = new File(classLoader.getResource("sample-output.xml").getFile()).getAbsolutePath();
        String outputFilePath = randomFileName + ".xml";
        Path outputPath = Paths.get(outputFilePath);

        String[] str = new String[]{String.format("--input-file=%s", inputFilePath), String.format("--output-file=%s", outputFilePath)};
        PointsCalculatorApplication.main(str);

        String expectedResponse = Files.lines(Paths.get(expectedFilePath)).collect(Collectors.joining());
        String calculatedResponse = Files.lines(outputPath).collect(Collectors.joining());
        Assertions.assertEquals(expectedResponse, calculatedResponse, "Calculated XML response validations");
        try {
            Files.delete(outputPath);    //Deleting test generated file
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete random file generated in unit test", e);
        }

    }
}

package com.decathlon.pointscalculator.service;

import com.decathlon.pointscalculator.event.OutputWriter;
import com.decathlon.pointscalculator.event.impl.XmlWriter;
import com.decathlon.pointscalculator.model.Athletes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

class PointsCalculatorServiceTest {

    OutputWriter outputWriter = mock(OutputWriter.class);

    @Test
    void pointsCalculatorService_success() {
        String randomFileName = UUID.randomUUID().toString();
        ClassLoader classLoader = getClass().getClassLoader();
        String inputFilePath = new File(classLoader.getResource("sample-input2.csv").getFile()).getAbsolutePath();
        String expectedFilePath = new File(classLoader.getResource("sample-output2.xml").getFile()).getAbsolutePath();
        String outputFilePath = randomFileName + ".xml";
        Path outputPath = Paths.get(outputFilePath);

        OutputWriter outputWriter = new XmlWriter(outputFilePath);

        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(inputFilePath, ";", outputWriter);
        try {
            pointsCalculatorService.process();
            String expectedResponse = Files.lines(Paths.get(expectedFilePath)).collect(Collectors.joining());
            String calculatedResponse = Files.lines(outputPath).collect(Collectors.joining());
            Assertions.assertEquals(expectedResponse, calculatedResponse, "Calculated XML response validations");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            Files.delete(outputPath);    //Deleting test generated file
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete random file generated in unit test", e);
        }
    }

    @Test
    void pointsCalculatorService_writeFail() {
        String randomFileName = UUID.randomUUID().toString();
        ClassLoader classLoader = getClass().getClassLoader();
        String inputFilePath = new File(classLoader.getResource("sample-input2.csv").getFile()).getAbsolutePath();
        doThrow(new RuntimeException("")).when(outputWriter).convertAndWrite(any(Athletes.class));
        PointsCalculatorService pointsCalculatorService = new PointsCalculatorService(inputFilePath, ";", outputWriter);
        Assertions.assertThrows(RuntimeException.class, () -> pointsCalculatorService.process());
    }
}

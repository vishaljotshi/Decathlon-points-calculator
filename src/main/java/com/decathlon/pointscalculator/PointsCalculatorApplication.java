package com.decathlon.pointscalculator;

import com.decathlon.pointscalculator.event.OutputWriter;
import com.decathlon.pointscalculator.event.impl.XmlWriter;
import com.decathlon.pointscalculator.exceptions.ProcessingException;
import com.decathlon.pointscalculator.service.PointsCalculatorProcessor;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class PointsCalculatorApplication {

	public static final String OUTPUT_FILE_ARG = "--output-file";
	public static final String INPUT_FILE_ARG = "--input-file";
	public static final String CSV_DELIMITER_ARG = "--csv-delimiter";
	public static final String DEFAULT_CSV_DELIMITER = ";";


	public static void main(String[] args) {

		Map<String, String> argMap = Arrays.stream(args)
				.filter(arg-> arg.split("=").length==2)
				.collect(toMap(arg -> arg.split("=")[0], arg -> arg.split("=")[1]));

		String inputFilePath=argMap.get(INPUT_FILE_ARG);
		String outputFilePath = argMap.get(OUTPUT_FILE_ARG);
		String csvDelimiter=argMap.getOrDefault(CSV_DELIMITER_ARG,DEFAULT_CSV_DELIMITER);

		OutputWriter outputWriter=new XmlWriter(outputFilePath);
		PointsCalculatorProcessor processor=new PointsCalculatorProcessor(inputFilePath,csvDelimiter,outputWriter);
		try {
			processor.process();
		} catch (IOException e) {
			throw new ProcessingException(String.format("Error occurred during file processing: %s",e.getMessage()));
		}

	}

}

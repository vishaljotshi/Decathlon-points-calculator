package com.decathlon.pointscalculator.event.impl;

import com.decathlon.pointscalculator.event.OutputWriter;
import com.decathlon.pointscalculator.exceptions.OutputFileWritingException;
import com.decathlon.pointscalculator.exceptions.XMLGenerationException;
import com.decathlon.pointscalculator.model.Athletes;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class XmlWriter implements OutputWriter {

    private final String outputFilePath;

    public XmlWriter(String outputFilePath) {
        this.outputFilePath=outputFilePath;
    }


    @Override
    public void convertAndWrite(Athletes athletes) {
        writeOutput(generateXMLString(athletes));
    }
    private String generateXMLString(Athletes athletes) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Athletes.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(athletes, sw);
            return sw.toString();
        } catch (JAXBException e) {
            throw new XMLGenerationException("Exception occurred while generating XML output :"+e.getMessage());
        }
    }

    public void writeOutput(String content) {
        try {
            Path path = Paths.get(outputFilePath);
            byte[] strToBytes = content.getBytes();
            Files.write(path, strToBytes);
        } catch (IOException e) {
            throw new OutputFileWritingException("Exception occurred while writing XML file : "+e.getMessage());
        }
    }
}

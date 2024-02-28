package com.server.positioninitializer;

import com.server.POJO.InstrumentDetails;
import com.server.POJO.PositionDetails;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component("mockPositionsReader")
public class MockPositionsReader {

    @Value("${positions_csv_location}")
    private String positions_csv_location;

    public List<PositionDetails> readCsv() {
        List<PositionDetails> positions = new ArrayList<>();
        String csvFile = positions_csv_location;
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFile))) {
            List<String[]> records = csvReader.readAll();
            for (String[] record : records) {
                String ticker = record[0];
                int quantity = Integer.parseInt(record[1]);
                InstrumentDetails instrumentDetails = new InstrumentDetails(ticker);
                positions.add(new PositionDetails(instrumentDetails, quantity));
            }
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
        return positions;
    }
}

package positioninitializer;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import POJO.PositionDetails;
import POJO.InstrumentDetails;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component("mockPositionsReader")
public class MockPositionsReader {

    public List<PositionDetails> readCsv(){
        List<PositionDetails> positions = new ArrayList<>();
        String csvFile = "C:/positions.csv";
        try(CSVReader csvReader = new CSVReader(new FileReader(csvFile))){
            List<String[]> records = csvReader.readAll();
            for(String[] record: records) {
                String ticker = record[0];
                int quantity = Integer.parseInt(record[1]);
                InstrumentDetails instrumentDetails = new InstrumentDetails(ticker);
                positions.add(new PositionDetails(instrumentDetails, quantity));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
        return positions;
    }
}

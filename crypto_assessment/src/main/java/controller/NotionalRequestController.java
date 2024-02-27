package controller;

import POJO.PositionDetails;
import notionalCalculator.NotionalCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import positioninitializer.MockPositionsReader;

import java.util.List;

@RestController
public class NotionalRequestController {

    List<NotionalCalculator> calculators;
    @Autowired
    public NotionalRequestController(MockPositionsReader mockPositionsReader) {
        List<PositionDetails> positions = mockPositionsReader.readCsv();
    }
}

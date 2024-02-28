package controller;

import POJO.NotionalData;
import POJO.PositionDetails;
import notionalCalculator.NotionalCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import positioninitializer.MockPositionsReader;
import positioninitializer.enrichers.Enricher;
import service.NotionalService;

import java.util.List;

@RestController
@RequestMapping("/api/notional")
public class NotionalRequestController {

    List<NotionalCalculator> calculators;
    @Autowired
    public NotionalRequestController(MockPositionsReader mockPositionsReader, List<Enricher> enrichers, NotionalService notionalService) {
        List<PositionDetails> positions = mockPositionsReader.readCsv();
        for(Enricher enricher: enrichers) {
            positions = (List<PositionDetails>) enricher.enrich(positions);
        }
        this.calculators = notionalService.execute(positions);
    }

//    @GetMapping(value = "/requestNotional", produces = "application/json")
//    public @ResponseBody NotionalData getLatestNotional() {
//        this.calculators;
//    }
}

package com.server.controller;

import com.server.POJO.NotionalData;
import com.server.POJO.PositionDetails;
import com.server.notionalCalculator.NotionalCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.server.positioninitializer.MockPositionsReader;
import com.server.enrichers.Enricher;
import com.server.service.NotionalService;
import com.server.service.TableCreationAndPopulationService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/notional")
public class NotionalRequestController {

//    private final SimpMessagingTemplate messagingTemplate;
//
//
//    public void broadcastMessage() {
//        // Handle the incoming message and perform any necessary processing
//        String message = "Hello, this is a server push!";
//        messagingTemplate.convertAndSend("/topic/live-stream", message);
//    }

    List<NotionalCalculator> calculators;

    @Autowired
    public NotionalRequestController(MockPositionsReader mockPositionsReader, List<Enricher> enrichers, TableCreationAndPopulationService tableCreationAndPopulationService, NotionalService notionalService) {
//                                     SimpMessagingTemplate messagingTemplate) {
        tableCreationAndPopulationService.init();
        List<PositionDetails> positions = mockPositionsReader.readCsv();
        for (Enricher enricher : enrichers) {
            positions = (List<PositionDetails>) enricher.enrich(positions);
        }
        this.calculators = notionalService.execute(positions);
//        this.messagingTemplate = messagingTemplate;
    }

    //    @MessageMapping("/send")
    @GetMapping(value = "/requestNotional")
    public ResponseEntity<List<NotionalData>> getLatestNotional() {
        List<NotionalData> notionalData = new ArrayList<>();
        double totalNotional = 0;

        try {
            for (NotionalCalculator notionalCalculator : this.calculators) {
                NotionalData notional = notionalCalculator.fetchLatestNotional();
                totalNotional += notional.getNotional();
                notionalData.add(notional);
            }
            notionalData.add(new NotionalData("TOTAL", totalNotional));
            return ResponseEntity.ok(notionalData);
        } catch (Exception e) {
            // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

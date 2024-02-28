package com.client;

import com.server.POJO.NotionalData;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotionalClient {


    public static void main(String[] args) throws InterruptedException {
        Logger logger =Logger.getLogger(NotionalClient.class.getName());
        RestTemplate restTemplate = new RestTemplate();
        String endpointUrl = "http://localhost:8080/api/notional/requestNotional";

        while(true) {
            // Make a GET request to the endpoint
            Thread.sleep(1600);
            ResponseEntity<List<NotionalData>> responseEntity =
                    restTemplate.exchange(endpointUrl, org.springframework.http.HttpMethod.GET, null,
                            new ParameterizedTypeReference<List<NotionalData>>() {
                            });

            // Check if the request was successful (HTTP status 2xx)
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                // Get the response body
                List<NotionalData> notionalDataList = responseEntity.getBody();
                System.out.println("NAV of the portfolio");
                System.out.println("Symbol                   Price          Quantity        Portfolio Value");
                // Display the result
                for (NotionalData notionalData : notionalDataList) {
                    if(!"TOTAL".equals(notionalData.getTicker())) {
                        if(notionalData.getTicker().length() <6) {
                            System.out.println(notionalData.getTicker() + "                     " + roundToDecimalPlaces(notionalData.getPrice(),1) + "          " + roundToDecimalPlaces(notionalData.getQuantity(), 1) + "           " + notionalData.getNotional());
                        } else {
                            System.out.println(notionalData.getTicker() + "      " + roundToDecimalPlaces(notionalData.getPrice(),1) + "          " + roundToDecimalPlaces(notionalData.getQuantity(), 1) + "             " + notionalData.getNotional());
                        }
                    } else {
                        System.out.println();
                        System.out.println(notionalData.getTicker() + ": " + notionalData.getNotional());
                        System.out.println();
                        System.out.println();
                        System.out.println();
                    }
                }
            } else {
                logger.log(Level.WARNING, "Error: " + responseEntity.getStatusCode());
            }
        }
    }

    public static double roundToDecimalPlaces(double value, int decimalPlaces) {
        if (decimalPlaces < 0) {
            throw new IllegalArgumentException("Decimal places cannot be negative.");
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }
}

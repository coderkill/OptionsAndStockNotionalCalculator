package pricingcalculator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component("stockPriceCalculator")
public class StockPricingCalculator {

    public static double calculate(double initialPrice, double drift, double volatility, double totalTimeInSeconds) {
        Random random = new Random();
        double randomValue = random.nextGaussian();
        double diffusion = volatility * randomValue * Math.sqrt(totalTimeInSeconds);
        double driftComponent = drift * initialPrice * totalTimeInSeconds;
        // Geometric Brownian Motion formula
        return initialPrice * Math.exp(driftComponent + diffusion);
    }
}
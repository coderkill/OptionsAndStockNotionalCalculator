package pricingcalculator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component("stockPriceCalculator")
public class StockPricingCalculatorSeconds {
    public static void main(String[] args) {
        double initialPrice = 100.0; // Initial stock price
        double drift = 0.05; // Drift (trend) component
        double volatility = 0.2; // Volatility component
        int totalTimeInSeconds = 60; // Total time for simulation in seconds
        double timeIncrement = 1.0; // Time increment in seconds
        calculate(initialPrice, drift, volatility, totalTimeInSeconds, timeIncrement);
    }
    private static void calculate(double initialPrice, double drift, double volatility,
                                  int totalTimeInSeconds, double timeIncrement) {
        Random random = new Random();
        double currentPrice = initialPrice;
        for (int currentTime = 0; currentTime < totalTimeInSeconds; currentTime += timeIncrement) {
            double randomValue = random.nextGaussian();
            double diffusion = volatility * randomValue * Math.sqrt(timeIncrement);
            double driftComponent = drift * currentPrice * timeIncrement;
            // Geometric Brownian Motion formula
            currentPrice = currentPrice * Math.exp(driftComponent + diffusion);
            System.out.println("Time: " + currentTime + " seconds, Stock Price: $" + currentPrice);
        }
    }
}
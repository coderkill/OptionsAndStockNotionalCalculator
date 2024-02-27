package pricingcalculator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component("stockPriceCalculator")
public class StockPricingCalculator {
    public static void main(String[] args) {
        // Parameters for GBM
        double initialPrice = 100.0;  // Initial stock price
        double drift = 0.05;          // Drift (average return)
        double volatility = 0.2;      // Volatility (standard deviation of return)
        double dt = 0.00001157407;              // Time step (in days)
        int numSteps = 86400;           // Number of steps (trading days in a year)
        // Simulate stock prices using GBM
        calculate(initialPrice, drift, volatility, dt, numSteps);
    }
    public static double calculate(double initialPrice, double drift, double volatility, double dt, int numSteps) {
        Random random = new Random();
        double randomSample = random.nextGaussian();
        double increment = drift * dt + volatility * Math.sqrt(dt) * randomSample;
        System.out.println(initialPrice * Math.exp(increment));
        return initialPrice * Math.exp(increment);
    }
}
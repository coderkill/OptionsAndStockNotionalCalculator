package pricingcalculator;

import java.util.Random;

public class StockPricingCalculatorTemp {
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
    public static void calculate(double initialPrice, double drift, double volatility, double dt, int numSteps) {
        Random random = new Random();
        double[] stockPrices = new double[numSteps + 1];
        stockPrices[0] = initialPrice;
        for (int i = 1; i <= numSteps; i++) {
            // Generate a random sample from a normal distribution
            double randomSample = random.nextGaussian();
            // Calculate the next stock price using GBM formula
            double increment = drift * dt + volatility * Math.sqrt(dt) * randomSample;
            stockPrices[i] = stockPrices[i - 1] * Math.exp(increment);
            // Print the simulated stock price for each step
            System.out.println("Day " + i + ": " + stockPrices[i]);
        }
    }
}
package com.server.pricingcalculator;

import java.util.Random;

//@Component("stockPriceCalculator")
public class StockPricingCalculator {

    public static double calculate(double initialPrice, double drift, double volatility, double incrementInSeconds) {
        Random random = new Random();
        double randomValue = random.nextGaussian();
//        double diffusion = volatility * randomValue * Math.sqrt(incrementInSeconds);
//        double driftComponent = drift * initialPrice * incrementInSeconds;
//        return initialPrice * Math.exp(driftComponent + diffusion);
        // Calculate the next stock price using GBM formula
        double increment = drift * 0.00001157407 + volatility * Math.sqrt(0.00001157407) * randomValue;
        return initialPrice * Math.exp(increment);
    }
}
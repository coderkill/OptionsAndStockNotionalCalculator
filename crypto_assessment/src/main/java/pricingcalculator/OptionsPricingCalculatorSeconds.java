package pricingcalculator;


//@Component("optionPricingCalculator")
public class OptionsPricingCalculatorSeconds {

    public static void main(String[] args) {
        // Example usage
        double spotPrice = 100; // Current stock price
        double strikePrice = 100; // Option strike price
        int timeToMaturityInMonths = 12; // Time to maturity in months
        // Convert months to seconds
        double timeToMaturityInSeconds = timeToMaturityInMonths * 30 * 24 * 60 * 60;
        double riskFreeRate = 0.05; // Risk-free interest rate
        double volatility = 0.2; // Volatility of the underlying stock
        double callOptionPrice = calculateEuropeanCallOptionPrice(spotPrice, strikePrice, timeToMaturityInSeconds, riskFreeRate, volatility);
        System.out.println("European Call Option Price: $" + callOptionPrice);
    }

    public static double calculateEuropeanCallOptionPrice(double spotPrice, double strikePrice, double timeToMaturityInSeconds, double riskFreeRate, double volatility) {
        double d1 = calculateD1(spotPrice, strikePrice, timeToMaturityInSeconds, riskFreeRate, volatility);
        double d2 = calculateD2(d1, volatility, timeToMaturityInSeconds);
        // Black-Scholes formula for European call option price
        return spotPrice * cumulativeDistributionFunction(d1) - strikePrice * Math.exp(-riskFreeRate * timeToMaturityInSeconds) * cumulativeDistributionFunction(d2);
    }

    private static double calculateD1(double spotPrice, double strikePrice, double timeToMaturityInSeconds, double riskFreeRate, double volatility) {
        return (Math.log(spotPrice / strikePrice) + (riskFreeRate + 0.5 * Math.pow(volatility, 2)) * timeToMaturityInSeconds) / (volatility * Math.sqrt(timeToMaturityInSeconds));
    }

    private static double calculateD2(double d1, double volatility, double timeToMaturityInSeconds) {
        return d1 - volatility * Math.sqrt(timeToMaturityInSeconds);
    }

    private static double cumulativeDistributionFunction(double x) {
        // In a real-world scenario, you might want to use a math library for a more accurate implementation
        return 0.5 * (1 + erf(x / Math.sqrt(2)));
    }

    private static double erf(double z) {
        // Error function approximation
        double t = 1.0 / (1.0 + 0.5 * Math.abs(z));
        // Use Horner's method for polynomial evaluation
        double ans = 1 - t * Math.exp(-z * z - 1.26551223 + t * (1.00002368 + t * (0.37409196 + t * (0.09678418 + t * (-0.18628806 + t * (0.27886807 + t * (-1.13520398 + t * (1.48851587 + t * (-0.82215223 + t * (0.17087277))))))))));
        if (z >= 0) return ans;
        else return -ans;
    }
}
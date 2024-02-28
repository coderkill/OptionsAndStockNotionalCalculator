# OptionsAndStockNotionalCalculator

This project calculates the real-time Net Asset Value (NAV) of a portfolio, which includes stocks and options. To execute the project, follow these steps:

    1.Configure Application Properties:
        a. Set the location of the positions.csv file in the variable positions_csv_location.
        b. Specify the location of the database file in the variable spring.datasource.url within the application.properties file located in "crypto_assessment/src/main/resources/".

    2.Run the Server:
        a.Execute the class RestServiceApp.java located in "crypto_assessment/src/main/java/com/server/".

    3.View Portfolio Data:
        a.Run the client using NotionalClient.java located in "crypto_assessment/src/main/java/com/client/".

    4.Sample File:
        a.Utilize the provided sample file for positions.csv located in "crypto_assessment/src/main/resources/".

Ensure that you have correctly set the necessary configurations and dependencies. The project is designed to provide a comprehensive overview of the portfolio's NAV, considering both stocks and options.

Design:

SQLLite has been integrated with spring and data is inserted into the file using the service "crypto_assessment/src/main/java/com/server/service/TableCreationAndPopulationService.java"
After CSV file is read we have enrichers which enrich the object from DB
After enrichment is done we pass each object to a notional calculator which runs a thread to generate MarketData based on the asset class.
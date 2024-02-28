# OptionsAndStockNotionalCalculator

This project calculates the real-time Net Asset Value (NAV) of a portfolio, which includes stocks and options. To execute the project, follow these steps:

1. **Configure Application Properties:**
   - Set the location of the `positions.csv` file in the variable `positions_csv_location`.
   - Specify the location of the database file in the variable `spring.datasource.url` within the `application.properties` file located in "crypto_assessment/src/main/resources/".

2. **Run the Server:**
   - Execute the class `RestServiceApp.java` located in "crypto_assessment/src/main/java/com/server/".

3. **View Portfolio Data:**
   - Run the client using `NotionalClient.java` located in "crypto_assessment/src/main/java/com/client/".

4. **Sample File:**
   - Utilize the provided sample file for `positions.csv` located in "crypto_assessment/src/main/resources/".

Ensure that you have correctly set the necessary configurations and dependencies. The project is designed to provide a comprehensive overview of the portfolio's NAV, considering both stocks and options.

## System Design Overview:

### Database Integration with Spring:
- Utilize Spring Data JPA for seamless integration with SQLite.
- Define entity classes annotated with `@Entity` to model the database tables.
- Implement repositories annotated with `@Repository` for efficient database operations.
- A dedicated configuration class, annotated with `@Configuration`, manages SQLite datasource settings.

### Data Population Service:
- Rename the existing service to `DatabaseInitializationService`.
- This service is responsible for creating tables and populating data into SQLite using Spring JDBC.

### CSV File Processing and Enrichment:
- Implement a `CsvReaderService` for reading CSV files, potentially using libraries like OpenCSV or Spring Batch for robust parsing.
- Enrichment of objects retrieved from the database is performed by specialized enricher services.
- Individual enrichers, like `DbEnricher`, focus on specific object types and interact with the database accordingly.

### Notional Calculation and Market Data Generation:
- Introduce a `NotionalCalculatorService` that receives enriched objects and initiates asynchronous threads for notional calculation.
- Market data generation is handled by a service, such as `MarketDataGenerator`, which utilizes the enriched objects to produce market data based on the asset class.

### Key Components:
- **Persistence Layer:** Spring Data JPA, entities, and repositories.
- **Database Configuration:** Dedicated configuration class for SQLite datasource settings.
- **Data Population:** `DatabaseInitializationService` for creating tables and populating data.
- **CSV Processing:** `CsvReaderService` for reading CSV files.
- **Enrichment:** Specialized enricher services (e.g., `DbEnricher`) for enriching objects from the database.
- **Notional Calculation:** `NotionalCalculatorService` for asynchronous notional calculation.
- **Market Data Generation:** `MarketDataGenerator` for generating market data based on enriched objects.

### Advantages:
- Improved modularity with focused services.
- Adherence to the Single Responsibility Principle.
- Effective use of Spring's features for database interaction and asynchronous processing.
- This design enhances readability, maintainability, and scalability while maintaining a clear separation of concerns in each component.

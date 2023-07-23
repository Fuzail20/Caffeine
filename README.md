# Caffeine Cache Integration with SpringBoot

### Project Overview

This project focuses on implementing the Caffeine cache in a Spring Boot application. Caffeine is a high-performance, near-optimal caching library that improves application response times by storing frequently accessed data in memory. The Caffeine Cache in Spring Boot project is designed to enhance the performance of an application that utilizes external API data. In this project, we integrate the third-party API - Fixer API - and effectively cache its responses to optimize response times.

Fixer API link-
(https://apilayer.com/marketplace/fixer-api?utm_source=apilayermarketplace&utm_medium=featured)

### Key Features
Utilization of the powerful Caffeine caching library to store and manage API responses.
Caffeine Cache implementation through annotations as well as through java code.
Automatic cache eviction strategies to ensure data freshness and prevent staleness.
Integration with the Fixer API to fetch foreign exchange rates and related data.

### Requirements
Java 8
SpringBoot version 2.7.4

### Usage

To use this project, follow these steps:

1. Clone the repository to your local machine.
2. Run the Spring Boot application.
3. Using postman , hit the following apis-
   
    a. To call the fixer API and get conversion rate of 2 currencies .

       GET http://localhost:8080/currency/converttoCurrency=INR&fromCurrency=SAR&amount=500

     On the Second call of this api with the same parameters , the value will come from cache instead of hitting the external API again.
   
    b. To print all the cache data
   
       GET http://localhost:8080/cache/data?cache-name=CURRENCY_CONVERTER_CACHE
    
    c. PUT value in cache
   
       POST http://localhost:8080/cache/cache-put?cache-name=CURRENCY_CONVERTER_CACHE&key=1&value=102222131
     
    d. To remove all values from cache
   
       GET http://localhost:8080/test/evict-all
    
    e. To remove a particular value from cache
   
       POST http://localhost:8080/cache/evict?cache-name=CURRENCY_CONVERTER_CACHE&key=1


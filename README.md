# Event-Tracking-Service

## Steps to setup event-tracking-service in local
 - Clone the event-tracking-service repo.
 - Install [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and [Maven 3](https://maven.apache.org)
 - Install [Postgresql 13](https://www.postgresql.org/download/)
 - Start Postgres Server on Local
 - Create Database with following details
   ```
   database_name = postgres
   user_name = postgres
   password = 
   ```
 - Build the jar using command *mvn clean install*
 - Run the jar using command *java -jar event-tracking-service.jar*

## Used case and definitions
https://www.google.com/url?q=https://rudderstacks.notion.site/Full-Stack-SDE-2-THA-25f2da47127944fd971296b126fad5de&source=gmail&ust=1692442055264000&usg=AOvVaw2H9ggQ45lYx9UHh2zl6lId

## High Level Design
<img width="760" alt="Screenshot 2023-08-18 at 10 52 35 PM" src="https://github.com/siddharth0815/Event-Tracking-Service/assets/46227716/8f236bd8-c575-409c-ba30-53bbc81acf04">

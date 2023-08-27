# Event-Tracking-Service

## Steps to setup event-tracking-service in local
 - Clone the **event-tracking-service** repo.
 - Install [Docker and Docker GUI](https://docs.docker.com/engine/install/)
 - Run ***docker-compose up -d***
 - In docker GUI, click on Rudderstack, all containers running under under Rudderstack network will be visible
   <img width="1155" alt="Screenshot 2023-08-28 at 4 09 52 AM" src="https://github.com/siddharth0815/Event-Tracking-Service/assets/46227716/31441e3f-fea2-44e7-aa48-d7144426b99b">
 - Click on postgres CLI and create Database, with name mentioned in docker-compose file
 - Run following cURL specifying debezium connector configurations
```shell
curl -H 'Content-Type: application/json' debezium:8083/connectors --data '
{
  "name": "test-connector",  
  "config": {
    "connector.class": "io.debezium.connector.postgresql.PostgresConnector", 
    "plugin.name": "pgoutput",
    "database.hostname": "postgres", 
    "database.port": "5432", 
    "database.user": "postgres", 
    "database.password": "postgres", 
    "database.dbname" : "exampledb", 
    "database.server.name": "postgres", 
    "table.include.list": "public.events, public.event" 
  }
}
}'

```
   

## Used case and definitions
 - https://www.google.com/url?q=https://rudderstacks.notion.site/Full-Stack-SDE-2-THA-25f2da47127944fd971296b126fad5de&source=gmail&ust=1692442055264000&usg=AOvVaw2H9ggQ45lYx9UHh2zl6lId
 - https://docs.google.com/document/d/1JiBWiwlvWMdIZtevwabhmAmkG8Zc33zLAxHMJpdOtzA/edit

## High Level Design
<img width="760" alt="Screenshot 2023-08-18 at 10 52 35 PM" src="https://github.com/siddharth0815/Event-Tracking-Service/assets/46227716/8f236bd8-c575-409c-ba30-53bbc81acf04">
<img width="645" alt="Screenshot 2023-08-28 at 4 04 10 AM" src="https://github.com/siddharth0815/Event-Tracking-Service/assets/46227716/42a51715-bbf8-4dac-8d50-feb63f506282">

## Proof of concept
https://www.loom.com/share/19c28804374d4039bd3138371da5a28e

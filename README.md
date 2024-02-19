
# Spring IoT Challenge :+1:


## Quick overview

Main features:
* Dockerized Spring Boot Java 17 REST Api for SIM management
* Docker compose
* A replicated SIM Data CRUD microservice
* A replicated ingest service which will push data to a Kafka topic for later consumption
* A mock IoT fog simulator. Use --devices=10 arg to simulate a fleet of SIM devices.
* Ran out of time for other stuff like using Spring Retry, Kafka and general error handling (sorry!)


## Trying it out


### Replicated with compose :airplane:
`docker compose up --build -d`


#### Start a sensor device simulator with

```docker run --rm -it --net spring-iot-challenge_default $(docker build -q mocksensor) --devices=10```

This will repeatedly push data to the data ingestion service.
By default each device will output data once every 5 seconds.

#### Monitor with Grafana + InfluxDb :chart_with_upwards_trend:

Open `localhost:3000` on your browser and login to grafana with the default admin:admin.


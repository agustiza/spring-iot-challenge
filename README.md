
# Spring IoT Challenge :+1:


## Quick overview

Main features:
* Dockerized Spring Boot Java 17 REST Api for SIM management
* Docker compose
* A SIM Data CRUD microservice
* An ingest service which will push data to a Kafka topic for later consumption
* A mock IoT fog simulator. Use --devices arg to launch a fleet of devices.
* Ran out of time for other stuff like using Spring Retry, Kafka and general error handling (sorry!)


## Trying it out


### Replicated with compose :airplane:
`docker compose up --build -d`


#### Optionally:  start a sensor device simulator with

`docker run --rm -it --net spring-iot-challenge_default $(docker build -q mocksensor) --devices=10`

Which will repeatedly push data to the data ingestion service.
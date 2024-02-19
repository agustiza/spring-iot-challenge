package com.iotchallenge.sensordata.config;

import com.influxdb.client.InfluxDBClient;
import com.iotchallenge.sensordata.services.InfluxDbPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDbConfiguration {

    @Bean
    public InfluxDbPublisher influxPublisher(InfluxDBClient client) {
        return new InfluxDbPublisher(client);
    }
}

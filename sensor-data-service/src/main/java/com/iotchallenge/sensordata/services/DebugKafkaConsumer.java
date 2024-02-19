package com.iotchallenge.sensordata.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iotchallenge.sensordata.config.KafkaConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;

import static com.iotchallenge.sensordata.config.KafkaConfiguration.TOPIC;

public class DebugKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConfiguration.class);

    private final ObjectMapper mapper;

    public DebugKafkaConsumer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @KafkaListener(topics = TOPIC)
    private void listener(@Payload SensorDataEvent ev) {
        try {
            LOGGER.debug("Received msg from kafka: " + mapper.writeValueAsString(ev));
        } catch (JsonProcessingException ignored) {
        }
    }
}

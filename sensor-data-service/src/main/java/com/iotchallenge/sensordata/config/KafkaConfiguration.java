package com.iotchallenge.sensordata.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iotchallenge.sensordata.services.KafkaPublisher;
import com.iotchallenge.sensordata.services.SensorDataEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
public class KafkaConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConfiguration.class);
    private static final String TOPIC = "sensordata";

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(TOPIC)
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public KafkaPublisher publisher(KafkaTemplate<String, SensorDataEvent> template) {
        return new KafkaPublisher(topic().name(), template);
    }

    @Autowired
    private ObjectMapper mapper;

    @KafkaListener(topics = TOPIC)
    private void listener(@Payload SensorDataEvent ev) {
        try {
            LOGGER.debug("Received msg from kafka: " + mapper.writeValueAsString(ev));
        } catch (JsonProcessingException ignored) {
        }
    }

}

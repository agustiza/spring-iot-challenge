package com.iotchallenge.sensordata.services;

import org.springframework.kafka.core.KafkaTemplate;

public class KafkaPublisher implements SensorDataPublisher {

    private final String topic;
    private final KafkaTemplate<String, SensorDataEvent> template;

    public KafkaPublisher(String topic, KafkaTemplate<String, SensorDataEvent> template) {
        this.topic = topic;
        this.template = template;
    }

    @Override
    public void publish(SensorDataEvent ev) {
        template.send(topic, ev);
    }
}

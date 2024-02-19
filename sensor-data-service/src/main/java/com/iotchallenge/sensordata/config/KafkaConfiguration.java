package com.iotchallenge.sensordata.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iotchallenge.sensordata.services.DebugKafkaConsumer;
import com.iotchallenge.sensordata.services.KafkaPublisher;
import com.iotchallenge.sensordata.services.SensorDataEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaConfiguration {

    public static final String TOPIC = "sensordata";

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

    @Bean
    public DebugKafkaConsumer debug(ObjectMapper mapper) {
        return new DebugKafkaConsumer(mapper);
    }

}

package com.iotchallenge.sensordata.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.iotchallenge.sensordata.dto.DataDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.iotchallenge.sensordata.config.KafkaConfiguration.TOPIC;
import static com.iotchallenge.sensordata.util.JsonUtils.flattenJson;

public class InfluxDbPublisher {

    private final WriteApiBlocking client;

    public InfluxDbPublisher(InfluxDBClient client) {
        this.client = client.getWriteApiBlocking();
    }

    @KafkaListener(topics = TOPIC)
    public void publish(@Payload SensorDataEvent ev) {
        DataDto sensorData = ev.payload();

        Point point = Point.measurement("sensordata")
                .time(sensorData.timestamp().toInstant(), WritePrecision.NS)
                .addTag("type", sensorData.type())
                .addTag("id", sensorData.id());

        flattenJson("", sensorData.payload())
                .forEach((key, node) -> {
                    if (node instanceof NumericNode val) {
                        point.addField(key, val.numberValue());
                    } else if (node instanceof TextNode val) {
                        point.addField(key, val.textValue());
                    }
                });

        client.writePoint(point);
    }

}

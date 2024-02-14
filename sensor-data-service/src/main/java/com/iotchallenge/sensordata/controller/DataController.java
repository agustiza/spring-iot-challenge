package com.iotchallenge.sensordata.controller;

import com.iotchallenge.sensordata.dto.DataDto;
import com.iotchallenge.sensordata.services.SensorDataEvent;
import com.iotchallenge.sensordata.services.SensorDataPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sensordata")
public class DataController {

    @Autowired
    SensorDataPublisher publisher;

    @PutMapping("")
    public ResponseEntity<?> ingest(@RequestBody DataDto dto) {
        publisher.publish(new SensorDataEvent(dto));
        return ResponseEntity.ok().build();
    }

}

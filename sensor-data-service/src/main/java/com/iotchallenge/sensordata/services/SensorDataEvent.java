package com.iotchallenge.sensordata.services;

import com.iotchallenge.sensordata.dto.DataDto;


public record SensorDataEvent(DataDto payload) {}

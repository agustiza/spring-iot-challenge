package com.iotchallenge.mocksensor.dto;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.ZonedDateTime;

public record DataDto(String id, String type, ZonedDateTime timestamp, JsonNode payload) {}

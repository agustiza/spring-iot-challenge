package com.iotchallenge.sensordata.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.iotchallenge.sensordata.util.JsonUtils.flattenJson;
import static org.junit.jupiter.api.Assertions.*;

class JsonUtilsTest {

    @Test
    void ok_flattenJson() throws Exception {
        String json = "{ \"a\": \"asd\", \"b\": {\"c\": \"nestedvalue\"} }";
        ObjectMapper mapper = new ObjectMapper();
        Map<String, JsonNode> flattened = flattenJson("", mapper.readTree(json));

        assertEquals( "nestedvalue", flattened.get("b_c").asText());
    }
}
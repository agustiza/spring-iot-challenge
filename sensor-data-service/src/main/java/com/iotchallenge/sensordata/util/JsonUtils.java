package com.iotchallenge.sensordata.util;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonUtils {

    private JsonUtils() {}

    /**
     * Recursively flattens a JSON object.
     * The resulting keys will be the concatenated json path using _ as delimiter.
     * @param prefix the path for the json key.
     * @param node the value node.
     * @return a flat map where the key is the flattened keys concatenated and the value is the leaf ValueNode
     */
    public static Map<String, JsonNode> flattenJson(String prefix, JsonNode node) {
        Map<String, JsonNode> flattenedKeys = new LinkedHashMap<>();
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                flattenedKeys.putAll(flattenJson(prefix.isEmpty()
                        ? entry.getKey()
                        : prefix + "_" + entry.getKey(), entry.getValue()));
            }
        } else {
            flattenedKeys.put(prefix, node.deepCopy());
        }
        return flattenedKeys;
    }
}

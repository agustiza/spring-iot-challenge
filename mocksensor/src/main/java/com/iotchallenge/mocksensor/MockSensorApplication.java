package com.iotchallenge.mocksensor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iotchallenge.mocksensor.dto.DataDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestClient;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * A simple application to simulate sensor data
 */
@SpringBootApplication
public class MockSensorApplication implements ApplicationRunner {

    public static final Logger LOGGER = LoggerFactory.getLogger(MockSensorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MockSensorApplication.class, args);
    }

    @Value("${iotchallenge.sensordata.baseUrl}")
    private String sensorDataBaseUrl;

    @Value("${iotchallenge.sensordata.port}")
    private int port;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        int numDevices = parseArg(args.getOptionValues("devices"));
        if (numDevices < 1) {
            numDevices = 4; // default
        }

        ScheduledExecutorService scheduler =
                new ScheduledThreadPoolExecutor(8);

        RestClient client = RestClient.builder()
                .baseUrl(sensorDataBaseUrl + ":" + port)
                .build();

        for (int i = 0; i < numDevices; i++) {
            final int simId = i;
            long initialDelayMs = (long) (Math.random() * 1000);

            scheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    try {
                        DataDto data = new DataDto(
                                Integer.toString(simId),
                                "humidity",
                                ZonedDateTime.now(),
                                mapper.createObjectNode()
                                        .put("value", (int) (Math.random() * 100)));

                        client.put()
                                .uri("/api/sensordata")
                                .body(data)
                                .retrieve();
                        LOGGER.debug("Submitted data Device id {}: {}", simId, mapper.writeValueAsString(data));
                    } catch (Exception e) {
                        LOGGER.error("Request failed", e);
                    }
                    scheduler.schedule(this, 5, TimeUnit.SECONDS);
                }
            }, initialDelayMs, TimeUnit.MILLISECONDS);
        }

    }

    private int parseArg(List<String> values) {
        if (values != null) {
            String d = values.get(0);
            if (!d.isBlank()) {
                return Integer.parseInt(d);
            }
        }
        return 0;
    }

}
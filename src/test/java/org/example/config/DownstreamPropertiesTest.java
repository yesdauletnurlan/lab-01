package org.example.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
class DownstreamPropertiesTest {

    @Autowired
    private DownstreamProperties properties;

    @Test
    void devProfileLoadsDownstreamConfig() {
        assertEquals("http://localhost:9090", properties.getBaseUrl());
        assertEquals(Duration.ofSeconds(2), properties.getTimeout());
    }
}
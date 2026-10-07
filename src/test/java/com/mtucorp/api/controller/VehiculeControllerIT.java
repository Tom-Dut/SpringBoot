package com.mtucorp.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VehiculeControllerIT {

    @LocalServerPort
    private int port;

    @Test
    void testEndpointVehicules() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        String reponse = restTemplate.getForObject(
                "http://localhost:" + port + "/api/vehicules", String.class);
        assertTrue(reponse.contains("AB-123-CD"));
    }
}
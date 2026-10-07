package com.mtucorp.api.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VehiculeServiceTest {

    @Test
    void testCompterCamions() {
        VehiculeService service = new VehiculeService();
        assertEquals(2, service.compterCamions()); //Modifier le 2 en 3 pour lerreur
    }

    @Test
    void testListerTailleTrois() {
        VehiculeService service = new VehiculeService();
        assertEquals(3, service.lister().size());
    }
}
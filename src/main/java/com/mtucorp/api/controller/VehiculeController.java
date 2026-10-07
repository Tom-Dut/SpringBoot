package com.mtucorp.api.controller;

import com.mtucorp.api.model.Vehicule;
import com.mtucorp.api.service.VehiculeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
public class VehiculeController {

    private final VehiculeService service;

    public VehiculeController(VehiculeService service) {
        this.service = service;
    }

    @GetMapping("/api/vehicules")
    public List<Vehicule> vehicules() {
        return service.lister();
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }
}
package com.mtucorp.api.service;

import com.mtucorp.api.model.Vehicule;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VehiculeService {

    private final List<Vehicule> vehicules = List.of(
            new Vehicule("AB-123-CD", "Camion"),
            new Vehicule("EF-456-GH", "Voiture"),
            new Vehicule("IJ-789-KL", "Camion")
    );

    public List<Vehicule> lister() {
        return vehicules;
    }

    public long compterCamions() {
        return vehicules.stream()
                .filter(v -> v.getType().equals("Camion"))
                .count();
    }
}
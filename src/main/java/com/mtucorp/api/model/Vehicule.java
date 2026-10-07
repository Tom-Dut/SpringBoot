package com.mtucorp.api.model;

public class Vehicule {
    private String immatriculation;
    private String type;

    public Vehicule(String immatriculation, String type) {
        this.immatriculation = immatriculation;
        this.type = type;
    }

    public String getImmatriculation() { return immatriculation; }
    public String getType() { return type; }
}
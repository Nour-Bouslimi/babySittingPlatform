package org.example.babysitting.entities;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Statut {
    PENDING, // En attente
    ACCEPTED, // Acceptée
    REJECTED; // Rejetée

    @JsonCreator
    public static Statut forValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("La valeur de Statut ne peut pas être nulle ou vide");
        }
        try {
            return Statut.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Valeur de Statut invalide : " + value);
        }
    }
}

package com.example.AutoVente.DTObjects;

import com.example.AutoVente.enums.Carburant;
import com.example.AutoVente.enums.Transmission;
import jakarta.validation.constraints.*;

public class VehiculeDTO {

    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100)
    private String nom;

    @NotNull(message = "L'année est obligatoire")
    @Min(1900)
    @Max(2025)
    private Integer annee;

    @NotNull(message = "Le prix est obligatoire")
    @Positive
    private Double prix;

    @NotNull(message = "Le kilométrage est obligatoire")
    @Min(0)
    private Integer kilometrage;

    private Carburant carburant;
    private Transmission transmission;
    private Double cylindree;
    private Integer puissance;
    private String couleur;
    private Integer nombrePortes;
    private Integer nombrePlaces;
    private String description;

    private Long marqueId;
    private String marqueNom;
    private Long modeleId;
    private String modeleNom;

    // Constructeurs, Getters et Setters
}
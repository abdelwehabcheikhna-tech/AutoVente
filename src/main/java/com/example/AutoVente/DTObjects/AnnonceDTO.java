package com.example.AutoVente.DTObjects;

import com.example.AutoVente.enums.Carburant;
import com.example.AutoVente.enums.Transmission;
import jakarta.validation.constraints.*;

public class AnnonceDTO {

    private Long id;

    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 5, max = 100)
    private String titre;

    @NotBlank(message = "La description est obligatoire")
    @Size(min = 20, max = 2000)
    private String description;

    @NotNull(message = "Le prix est obligatoire")
    @Positive
    private Double prix;

    private Long marqueId;
    private String marqueNom;

    @NotBlank(message = "Le modèle est obligatoire")
    private String modele;

    @NotNull(message = "L'année est obligatoire")
    @Min(1900)
    @Max(2025)
    private Integer annee;

    @NotNull(message = "Le kilométrage est obligatoire")
    @Min(0)
    private Integer kilometrage;

    private Carburant carburant;
    private Transmission transmission;
    private String couleur;
    private Integer puissance;

    // Getters et Setters (à générer)
}
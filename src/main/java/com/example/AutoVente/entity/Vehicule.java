package com.example.AutoVente.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.example.AutoVente.enums.Carburant;
import com.example.AutoVente.enums.Transmission;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicules")
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du véhicule est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;

    @NotNull(message = "L'année est obligatoire")
    @Min(value = 1900, message = "L'année doit être >= 1900")
    @Max(value = 2025, message = "L'année doit être <= 2025")
    private Integer annee;

    @NotNull(message = "Le prix est obligatoire")
    @Positive(message = "Le prix doit être positif")
    private Double prix;

    @NotNull(message = "Le kilométrage est obligatoire")
    @Min(value = 0, message = "Le kilométrage ne peut pas être négatif")
    private Integer kilometrage;

    @Enumerated(EnumType.STRING)
    private Carburant carburant;

    @Enumerated(EnumType.STRING)
    private Transmission transmission;

    private Double cylindree;

    private Integer puissance;

    private String couleur;

    private Integer nombrePortes;

    private Integer nombrePlaces;

    private String description;

    // ✅ RELATION MANY-TO-ONE AVEC MARQUE
    @ManyToOne
    @JoinColumn(name = "marque_id")
    private Marque marque;

    // ✅ RELATION MANY-TO-ONE AVEC MODELE
    @ManyToOne
    @JoinColumn(name = "modele_id")
    private Modele modele;

    // ✅ PROPRIÉTÉ AJOUTÉE POUR LA RELATION AVEC CATEGORIE
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @OneToOne(mappedBy = "vehicule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Annonce annonce;

    private LocalDateTime dateCreation;

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Integer getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(Integer kilometrage) {
        this.kilometrage = kilometrage;
    }

    public Carburant getCarburant() {
        return carburant;
    }

    public void setCarburant(Carburant carburant) {
        this.carburant = carburant;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public Double getCylindree() {
        return cylindree;
    }

    public void setCylindree(Double cylindree) {
        this.cylindree = cylindree;
    }

    public Integer getPuissance() {
        return puissance;
    }

    public void setPuissance(Integer puissance) {
        this.puissance = puissance;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Integer getNombrePortes() {
        return nombrePortes;
    }

    public void setNombrePortes(Integer nombrePortes) {
        this.nombrePortes = nombrePortes;
    }

    public Integer getNombrePlaces() {
        return nombrePlaces;
    }

    public void setNombrePlaces(Integer nombrePlaces) {
        this.nombrePlaces = nombrePlaces;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public Modele getModele() {
        return modele;
    }

    public void setModele(Modele modele) {
        this.modele = modele;
    }

    // ✅ GETTER ET SETTER POUR CATEGORIE
    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Annonce getAnnonce() {
        return annonce;
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
}
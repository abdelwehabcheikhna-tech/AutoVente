package com.example.AutoVente.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "marques")
public class Marque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la marque est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    @Column(unique = true, nullable = false)
    private String nom;

    private String logo;
    private String paysOrigine;

    @OneToMany(mappedBy = "marque", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Modele> modeles = new ArrayList<>();

    @OneToMany(mappedBy = "marque", fetch = FetchType.LAZY)
    private List<Vehicule> vehicules = new ArrayList<>();

    // Constructeurs
    public Marque() {}

    public Marque(String nom, String paysOrigine) {
        this.nom = nom;
        this.paysOrigine = paysOrigine;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPaysOrigine() {
        return paysOrigine;
    }

    public void setPaysOrigine(String paysOrigine) {
        this.paysOrigine = paysOrigine;
    }

    public List<Modele> getModeles() {
        return modeles;
    }

    public void setModeles(List<Modele> modeles) {
        this.modeles = modeles;
    }

    public List<Vehicule> getVehicules() {
        return vehicules;
    }

    public void setVehicules(List<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    // Méthodes utilitaires
    public void addModele(Modele modele) {
        modeles.add(modele);
        modele.setMarque(this);
    }

    public void removeModele(Modele modele) {
        modeles.remove(modele);
        modele.setMarque(null);
    }

    @Override
    public String toString() {
        return nom;
    }
}
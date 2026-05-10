package com.example.AutoVente.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "modeles")
public class Modele {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du modèle est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    @Column(nullable = false)
    private String nom;

    @ManyToOne
    @JoinColumn(name = "marque_id", nullable = false)
    private Marque marque;

    private Integer anneeDebut;
    private Integer anneeFin;

    @OneToMany(mappedBy = "modele", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vehicule> vehicules = new ArrayList<>();

    // Constructeurs
    public Modele() {}

    public Modele(String nom, Marque marque) {
        this.nom = nom;
        this.marque = marque;
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

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public Integer getAnneeDebut() {
        return anneeDebut;
    }

    public void setAnneeDebut(Integer anneeDebut) {
        this.anneeDebut = anneeDebut;
    }

    public Integer getAnneeFin() {
        return anneeFin;
    }

    public void setAnneeFin(Integer anneeFin) {
        this.anneeFin = anneeFin;
    }

    public List<Vehicule> getVehicules() {
        return vehicules;
    }

    public void setVehicules(List<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    // Méthodes utilitaires
    public void addVehicule(Vehicule vehicule) {
        vehicules.add(vehicule);
        vehicule.setModele(this);
    }

    public void removeVehicule(Vehicule vehicule) {
        vehicules.remove(vehicule);
        vehicule.setModele(null);
    }

    @Override
    public String toString() {
        return nom;
    }
}
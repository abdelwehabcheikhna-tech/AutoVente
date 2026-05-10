package com.example.AutoVente.entity;

import com.example.AutoVente.enums.StatutAnnonce;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "annonces")
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 5, max = 100)
    @Column(nullable = false)
    private String titre;

    @NotBlank(message = "La description est obligatoire")
    @Size(min = 20, max = 2000)
    @Column(length = 2000)
    private String description;

    @NotNull(message = "Le prix est obligatoire")
    @Positive
    private Double prix;

    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;

    @ManyToOne
    @JoinColumn(name = "vendeur_id")
    private User vendeur;

    @ElementCollection
    @CollectionTable(name = "annonce_images", joinColumns = @JoinColumn(name = "annonce_id"))
    @Column(name = "image_url")
    private List<String> images = new ArrayList<>();

    private String statut = "ACTIVE";
    private Integer nombreVues = 0;
    private LocalDateTime dateCreation;
    private LocalDateTime dateExpiration;

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        dateExpiration = LocalDateTime.now().plusMonths(3);
        if (nombreVues == null) nombreVues = 0;
    }

    // ========== GETTERS ==========
    public Long getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrix() {
        return prix;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public User getVendeur() {
        return vendeur;
    }

    public List<String> getImages() {
        return images;
    }

    public String getStatut() {
        return statut;
    }

    public Integer getNombreVues() {
        return nombreVues != null ? nombreVues : 0;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public LocalDateTime getDateExpiration() {
        return dateExpiration;
    }

    // ========== SETTERS ==========
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public void setVendeur(User vendeur) {
        this.vendeur = vendeur;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    // ✅ UN SEUL setStatut
    public void setStatut(String statut) {
        this.statut = statut;
    }

    // ✅ Méthode utilitaire pour StatutAnnonce enum (si nécessaire)
    public void setStatutEnum(StatutAnnonce statutEnum) {
        this.statut = statutEnum.toString();
    }

    public void setNombreVues(Integer nombreVues) {
        this.nombreVues = nombreVues;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setDateExpiration(LocalDateTime dateExpiration) {
        this.dateExpiration = dateExpiration;
    }
}
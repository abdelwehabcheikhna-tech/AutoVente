package com.example.AutoVente.DTObjects;

import com.example.AutoVente.entity.Annonce;

public class ComparaisonDTO {

    private Annonce meilleurPrix;
    private Annonce meilleurKilometrage;
    private Annonce plusRecent;
    private Annonce plusPuissant;

    private Long annonce1Id;
    private Long annonce2Id;

    public ComparaisonDTO() {}

    public ComparaisonDTO(Annonce annonce1, Annonce annonce2) {
        if (annonce1 == null || annonce2 == null) return;

        this.annonce1Id = (Long) annonce1.getId();
        this.annonce2Id = (Long) annonce2.getId();

        // ✅ CORRECTION: Utiliser compareTo() pour les Double
        Double prix1 = (Double) annonce1.getPrix();
        Double prix2 = (Double) annonce2.getPrix();

        if (prix1 != null && prix2 != null) {
            this.meilleurPrix = (prix1.compareTo(prix2) < 0) ? annonce1 : annonce2;
        } else {
            this.meilleurPrix = annonce1;
        }

        // Kilométrage
        Integer km1 = (annonce1.getVehicule() != null && annonce1.getVehicule().getKilometrage() != null)
                ? annonce1.getVehicule().getKilometrage() : Integer.MAX_VALUE;
        Integer km2 = (annonce2.getVehicule() != null && annonce2.getVehicule().getKilometrage() != null)
                ? annonce2.getVehicule().getKilometrage() : Integer.MAX_VALUE;
        this.meilleurKilometrage = (km1 < km2) ? annonce1 : annonce2;

        // Année
        Integer annee1 = (annonce1.getVehicule() != null && annonce1.getVehicule().getAnnee() != null)
                ? annonce1.getVehicule().getAnnee() : 0;
        Integer annee2 = (annonce2.getVehicule() != null && annonce2.getVehicule().getAnnee() != null)
                ? annonce2.getVehicule().getAnnee() : 0;
        this.plusRecent = (annee1 > annee2) ? annonce1 : annonce2;

        // Puissance
        Integer p1 = (annonce1.getVehicule() != null && annonce1.getVehicule().getPuissance() != null)
                ? annonce1.getVehicule().getPuissance() : 0;
        Integer p2 = (annonce2.getVehicule() != null && annonce2.getVehicule().getPuissance() != null)
                ? annonce2.getVehicule().getPuissance() : 0;
        this.plusPuissant = (p1 > p2) ? annonce1 : annonce2;
    }

    // Getters
    public Annonce getMeilleurPrix() { return meilleurPrix; }
    public Annonce getMeilleurKilometrage() { return meilleurKilometrage; }
    public Annonce getPlusRecent() { return plusRecent; }
    public Annonce getPlusPuissant() { return plusPuissant; }
    public Long getAnnonce1Id() { return annonce1Id; }
    public Long getAnnonce2Id() { return annonce2Id; }

    // Setters
    public void setMeilleurPrix(Annonce meilleurPrix) { this.meilleurPrix = meilleurPrix; }
    public void setMeilleurKilometrage(Annonce meilleurKilometrage) { this.meilleurKilometrage = meilleurKilometrage; }
    public void setPlusRecent(Annonce plusRecent) { this.plusRecent = plusRecent; }
    public void setPlusPuissant(Annonce plusPuissant) { this.plusPuissant = plusPuissant; }
    public void setAnnonce1Id(Long annonce1Id) { this.annonce1Id = annonce1Id; }
    public void setAnnonce2Id(Long annonce2Id) { this.annonce2Id = annonce2Id; }

    public String getVerdict() {
        int score1 = 0, score2 = 0;

        if (meilleurPrix != null && meilleurPrix.getId() != null) {
            if (meilleurPrix.getId().equals(annonce1Id)) score1++;
            else if (meilleurPrix.getId().equals(annonce2Id)) score2++;
        }

        if (meilleurKilometrage != null && meilleurKilometrage.getId() != null) {
            if (meilleurKilometrage.getId().equals(annonce1Id)) score1++;
            else if (meilleurKilometrage.getId().equals(annonce2Id)) score2++;
        }

        if (plusRecent != null && plusRecent.getId() != null) {
            if (plusRecent.getId().equals(annonce1Id)) score1++;
            else if (plusRecent.getId().equals(annonce2Id)) score2++;
        }

        if (plusPuissant != null && plusPuissant.getId() != null) {
            if (plusPuissant.getId().equals(annonce1Id)) score1++;
            else if (plusPuissant.getId().equals(annonce2Id)) score2++;
        }

        if (score1 > score2) return "✅ Le véhicule 1 est meilleur dans " + score1 + " critère(s)";
        if (score2 > score1) return "✅ Le véhicule 2 est meilleur dans " + score2 + " critère(s)";
        return "⚖️ Les deux véhicules sont équivalents";
    }
}
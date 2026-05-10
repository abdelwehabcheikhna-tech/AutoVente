package com.example.AutoVente.securityconfig;

import com.example.AutoVente.entity.*;
import com.example.AutoVente.enums.Carburant;
import com.example.AutoVente.enums.Role;
import com.example.AutoVente.enums.StatutAnnonce;
import com.example.AutoVente.enums.Transmission;
import com.example.AutoVente.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MarqueRepository marqueRepository;

    @Autowired
    private ModeleRepository modeleRepository;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // Vérifier si les données existent déjà
        if (marqueRepository.count() > 0) {
            System.out.println("⚠️ Les données existent déjà, initialisation ignorée.");
            return;
        }

        // ========== CRÉATION DES UTILISATEURS ==========
        createUser("admin@autovente.com", "admin", "Admin", "System", Role.ADMIN);
        createUser("vendeur@autovente.com", "vendeur", "Dupont", "Jean", Role.VENDEUR);
        createUser("acheteur@autovente.com", "acheteur", "Martin", "Sophie", Role.ACHETEUR);

        // ========== CRÉATION DES MARQUES ==========
        Marque toyota = createMarque("Toyota", "Japon");
        Marque renault = createMarque("Renault", "France");
        Marque vw = createMarque("Volkswagen", "Allemagne");
        Marque peugeot = createMarque("Peugeot", "France");
        Marque bmw = createMarque("BMW", "Allemagne");
        Marque mercedes = createMarque("Mercedes", "Allemagne");

        // ========== CRÉATION DES MODÈLES ==========
        createModele("Corolla", toyota, 1966, null);
        createModele("Camry", toyota, 1982, null);
        createModele("Clio", renault, 1990, null);
        createModele("Megane", renault, 1995, null);
        createModele("Golf", vw, 1974, null);
        createModele("208", peugeot, 2012, null);
        createModele("Serie 3", bmw, 1975, null);
        createModele("Classe C", mercedes, 1993, null);

        // ========== CRÉATION DES ANNONCES ==========
        User vendeur = userRepository.findByEmail("vendeur@autovente.com").orElse(null);

        if (vendeur != null) {
            createAnnonce(toyota, "Corolla", 2020, 45000, Carburant.ESSENCE, Transmission.MANUELLE,
                    "Rouge", 5500000, "Toyota Corolla 2020",
                    "Véhicule en excellent état, première main", vendeur);

            createAnnonce(renault, "Clio", 2021, 30000, Carburant.DIESEL, Transmission.MANUELLE,
                    "Bleue", 4800000, "Renault Clio 2021",
                    "Très économique, faible consommation", vendeur);

            createAnnonce(vw, "Golf", 2019, 68000, Carburant.ESSENCE, Transmission.AUTOMATIQUE,
                    "Grise", 6200000, "Volkswagen Golf 2019",
                    "Finition Confort, très bon état", vendeur);
        }

        System.out.println("✅ Initialisation des données terminée !");
    }

    private void createUser(String email, String username, String nom, String prenom, Role role) {
        if (userRepository.findByEmail(email).isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode("admin123"));
            user.setNom(nom);
            user.setPrenom(prenom);
            user.setRole(role);
            user.setEnabled(true);
            userRepository.save(user);
            System.out.println("✅ Utilisateur créé: " + email);
        }
    }

    private Marque createMarque(String nom, String paysOrigine) {
        Marque marque = new Marque();
        marque.setNom(nom);
        marque.setPaysOrigine(paysOrigine);
        return marqueRepository.save(marque);
    }

    private void createModele(String nom, Marque marque, Integer anneeDebut, Integer anneeFin) {
        Modele modele = new Modele();
        modele.setNom(nom);
        modele.setMarque(marque);
        modele.setAnneeDebut(anneeDebut);
        modele.setAnneeFin(anneeFin);
        modeleRepository.save(modele);
    }

    private void createAnnonce(Marque marque, String modeleNom, int annee, int kilometrage,
                               Carburant carburant, Transmission transmission, String couleur,
                               double prix, String titre, String description, User vendeur) {

        Modele modele = (Modele) modeleRepository.findByNomAndMarqueNom(modeleNom, marque.getNom()).orElse(null);

        if (modele != null && vendeur != null) {
            Vehicule vehicule = new Vehicule();
            vehicule.setMarque(marque);
            vehicule.setModele(modele);
            vehicule.setAnnee(annee);
            vehicule.setKilometrage(kilometrage);
            vehicule.setCarburant(carburant);
            vehicule.setTransmission(transmission);
            vehicule.setCouleur(couleur);
            vehicule = vehiculeRepository.save(vehicule);

            Annonce annonce = new Annonce();
            annonce.setTitre(titre);
            annonce.setDescription(description);
            annonce.setPrix(prix);
            annonce.setVehicule(vehicule);
            annonce.setVendeur(vendeur);
            annonce.setStatut(String.valueOf(StatutAnnonce.ACTIVE));
            annonceRepository.save(annonce);
        }
    }
}
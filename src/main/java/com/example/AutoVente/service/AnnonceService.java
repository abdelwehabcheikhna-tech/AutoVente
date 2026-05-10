package com.example.AutoVente.service;

import com.example.AutoVente.entity.Annonce;
import com.example.AutoVente.entity.User;
import com.example.AutoVente.entity.Vehicule;
import com.example.AutoVente.repository.AnnonceRepository;
import com.example.AutoVente.repository.VehiculeRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AnnonceService {

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    public Annonce creerAnnonce(Annonce annonce, Vehicule vehicule, User vendeur) {
        vehicule = vehiculeRepository.save(vehicule);
        annonce.setVehicule(vehicule);
        annonce.setVendeur(vendeur);
        return annonceRepository.save(annonce);
    }

    public Annonce getAnnonceById(Long id) {
        return (Annonce) annonceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Annonce non trouvée"));
    }

    public void incrementerVues(Long id) {
        Annonce annonce = getAnnonceById(id);
//        annonce.setNombreVues(1 + annonce.getNombreVues());
        annonceRepository.save(annonce);
    }

    public void supprimerAnnonce(Long id) {
        annonceRepository.deleteById(id);
    }

    public Page<Annonce> getAnnoncesByVendeur(User vendeur, Pageable pageable) {
        return annonceRepository.findById(vendeur, pageable);
    }

    public long count() {
        return annonceRepository.count();
    }

    public @Nullable Object getAnnonceById(PageRequest of) {
            return null;
    }

    public @Nullable Object rechercheAvancee(Long marqueId, Long modeleId, Double prixMin, Double prixMax, Integer anneeMin, Integer anneeMax, Integer kilometrageMax, String carburant, String transmission, PageRequest of) {
            return null;
    }

    public @Nullable Object getAllAnnonces() {
        return annonceRepository.findAll();
    }
}
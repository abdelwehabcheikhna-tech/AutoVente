package com.example.AutoVente.service;

import com.example.AutoVente.entity.Modele;
import com.example.AutoVente.repository.ModeleRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModeleService {

    @Autowired
    private ModeleRepository modeleRepository;


    // Ajouter cette méthode
        public List<Modele> getModelesByMarque(Long marqueId) {
        return modeleRepository.findByMarqueId(marqueId);
    }
    public Modele getModeleById(Long id) {
        return modeleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Modèle non trouvé"));
    }

    public Modele saveModele(Modele modele) {
        return modeleRepository.save(modele);
    }

    public @Nullable Object getAllModeles() {
        return null;
    }

    public void deleteModele(Long id) {

    }
}
package com.example.AutoVente.service;

import com.example.AutoVente.entity.Marque;
import com.example.AutoVente.repository.MarqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarqueService {

    @Autowired
    private MarqueRepository marqueRepository;

    public List<Marque> getAllMarques() {
        return marqueRepository.findAllByOrderByNomAsc();
    }
    // Ajouter cette méthode
    public Page<Marque> getMarquesPopulaires(PageRequest of) {
        return marqueRepository.findAll(of);
    }
    public Marque getMarqueById(Long id) {
        return marqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marque non trouvée"));
    }

    public Marque saveMarque(Marque marque) {
        return marqueRepository.save(marque);
    }

    public void deleteMarque(Long id) {
        marqueRepository.deleteById(id);
    }
}
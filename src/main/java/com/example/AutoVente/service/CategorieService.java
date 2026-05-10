package com.example.AutoVente.service;

import com.example.AutoVente.entity.Categorie;
import com.example.AutoVente.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    public List<Categorie> getAllCategories() {
        return categorieRepository.findByActifTrueOrderByOrdreAffichageAsc();
    }

    public Categorie getCategorieById(Long id) {
        return categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
    }

    public Categorie saveCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public void deleteCategorie(Long id) {
        categorieRepository.deleteById(id);
    }
}
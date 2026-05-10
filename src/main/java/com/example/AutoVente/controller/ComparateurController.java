package com.example.AutoVente.controller;

import com.example.AutoVente.DTObjects.ComparaisonDTO;
import com.example.AutoVente.entity.Annonce;
import com.example.AutoVente.service.AnnonceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comparateur")
public class ComparateurController {

    @Autowired
    private AnnonceService annonceService;

    @GetMapping
    public String afficherComparateur(Model model) {
        return "vehicules/comparateur";
    }

    @PostMapping
    public String comparerVehicules(
            @RequestParam Long vehicule1Id,
            @RequestParam Long vehicule2Id,
            Model model) {

        Annonce annonce1 = annonceService.getAnnonceById(vehicule1Id);
        Annonce annonce2 = annonceService.getAnnonceById(vehicule2Id);

        // ✅ Utiliser le DTO pour la comparaison
        ComparaisonDTO comparaison = new ComparaisonDTO(annonce1, annonce2);

        model.addAttribute("annonce1", annonce1);
        model.addAttribute("annonce2", annonce2);
        model.addAttribute("comparaison", comparaison);

        return "vehicules/comparateur-resultat";
    }
}
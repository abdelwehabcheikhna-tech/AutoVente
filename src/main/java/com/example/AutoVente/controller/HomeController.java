package com.example.AutoVente.controller;

import com.example.AutoVente.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private AnnonceService annonceService;

    // ✅ CORRECTION: Changer MarqueController en MarqueService
    @Autowired
    private MarqueService marqueService;  // ← Correction ici

    @Autowired
    private CategorieService categorieService;

    @Autowired
    private StatistiqueService statistiqueService;

    @GetMapping("/")
    public String accueil(Model model) {
        model.addAttribute("dernieresAnnonces",
                annonceService.getAnnonceById(PageRequest.of(0, 6)));
        model.addAttribute("marquesPopulaires",
                marqueService.getMarquesPopulaires(PageRequest.of(0, 8)));
        model.addAttribute("categories",
                categorieService.getAllCategories());
        model.addAttribute("stats",
                statistiqueService.getDashboardStats());
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}
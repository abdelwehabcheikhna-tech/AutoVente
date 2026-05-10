package com.example.AutoVente.controller;

import com.example.AutoVente.enums.Carburant;
import com.example.AutoVente.enums.Transmission;
import com.example.AutoVente.service.AnnonceService;
import com.example.AutoVente.service.MarqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recherche")
public class RechercheController {

    @Autowired
    private AnnonceService annonceService;

    @Autowired
    private MarqueService marqueService;

    @GetMapping
    public String rechercheAvancee(
            @RequestParam(required = false) Long marqueId,
            @RequestParam(required = false) Long modeleId,
            @RequestParam(required = false) Double prixMin,
            @RequestParam(required = false) Double prixMax,
            @RequestParam(required = false) Integer anneeMin,
            @RequestParam(required = false) Integer anneeMax,
            @RequestParam(required = false) Integer kilometrageMax,
            @RequestParam(required = false) String carburant,
            @RequestParam(required = false) String transmission,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        model.addAttribute("annonces",
                annonceService.rechercheAvancee(marqueId, modeleId, prixMin, prixMax,
                        anneeMin, anneeMax, kilometrageMax, carburant, transmission,
                        PageRequest.of(page, 12)));
        model.addAttribute("marques", marqueService.getAllMarques());
        model.addAttribute("carburants", Carburant.values());
        model.addAttribute("transmissions", Transmission.values());

        // Conserver les filtres
        model.addAttribute("marqueId", marqueId);
        model.addAttribute("prixMin", prixMin);
        model.addAttribute("prixMax", prixMax);
        model.addAttribute("anneeMin", anneeMin);
        model.addAttribute("anneeMax", anneeMax);
        model.addAttribute("kilometrageMax", kilometrageMax);

        return "annonces/liste";
    }
}
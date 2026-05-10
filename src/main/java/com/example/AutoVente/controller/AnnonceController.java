package com.example.AutoVente.controller;

import com.example.AutoVente.entity.Annonce;
import com.example.AutoVente.entity.User;
import com.example.AutoVente.entity.Vehicule;
import com.example.AutoVente.repository.MarqueRepository;
import com.example.AutoVente.repository.ModeleRepository;
import com.example.AutoVente.service.AnnonceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/annonces")
public class AnnonceController {

    @Autowired
    private AnnonceService annonceService;

    @Autowired
    private MarqueRepository marqueRepository;

    @Autowired
    private ModeleRepository modeleRepository;

    @GetMapping
    public String listerAnnonces(Model model) {
        model.addAttribute("annonces", annonceService.getAllAnnonces());
        model.addAttribute("marques", marqueRepository.findAll());
        model.addAttribute("modeles", modeleRepository.findAll());
        return "annonces/liste";
    }

    @GetMapping("/{id}")
    public String detailAnnonce(@PathVariable Long id, Model model) {
        Annonce annonce = annonceService.getAnnonceById(id);
        annonceService.incrementerVues(id);
        model.addAttribute("annonce", annonce);
        return "annonces/detail";
    }

    @GetMapping("/nouvelle")
    public String formulaireAnnonce(Model model) {
        model.addAttribute("annonce", new Annonce());
        model.addAttribute("vehicule", new Vehicule());
        model.addAttribute("marques", marqueRepository.findAll());
        model.addAttribute("modeles", modeleRepository.findAll());
        return "annonces/formulaire";
    }

    @PostMapping("/save")
    public String sauvegarderAnnonce(@Valid @ModelAttribute Annonce annonce,
                                     @Valid @ModelAttribute Vehicule vehicule,
                                     BindingResult result,
                                     Authentication auth,
                                     RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "annonces/formulaire";
        }

        try {
            User vendeur = (User) auth.getPrincipal();
            annonceService.creerAnnonce(annonce, vehicule, vendeur);
            redirectAttributes.addFlashAttribute("success", "Annonce créée avec succès !");
            return "redirect:/annonces/mes-annonces";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création");
            return "redirect:/annonces/nouvelle";
        }
    }

    @GetMapping("/mes-annonces")
    public String mesAnnonces(Authentication auth, Model model) {
        User vendeur = (User) auth.getPrincipal();
        model.addAttribute("annonces", annonceService.getAnnoncesByVendeur(vendeur, PageRequest.of(0, 10)));
        return "annonces/mes-annonces";
    }


    @GetMapping("/delete/{id}")
    public String supprimerAnnonce(@PathVariable Long id, Authentication auth, RedirectAttributes redirectAttributes) {
        Annonce annonce = annonceService.getAnnonceById(id);
        User currentUser = (User) auth.getPrincipal();

        if (annonce.getVendeur().getId().equals(currentUser.getId())) {
            annonceService.supprimerAnnonce(id);
            redirectAttributes.addFlashAttribute("success", "Annonce supprimée avec succès");
        } else {
            redirectAttributes.addFlashAttribute("error", "Vous n'avez pas les droits");
        }
        return "redirect:/annonces/mes-annonces";
    }
}
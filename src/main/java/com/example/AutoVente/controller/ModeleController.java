package com.example.AutoVente.controller;

import com.example.AutoVente.entity.Modele;
import com.example.AutoVente.service.MarqueService;
import com.example.AutoVente.service.ModeleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/modeles")
public class ModeleController {

    @Autowired
    private ModeleService modeleService;

    @Autowired
    private MarqueService marqueService;

    @GetMapping
    public String listerModeles(Model model) {
        // ✅ Correction: getAllModeles() sans paramètre
        model.addAttribute("modeles", modeleService.getAllModeles());
        model.addAttribute("marques", marqueService.getAllMarques());
        return "admin/modeles/liste";
    }

    @GetMapping("/ajouter")
    public String ajouterModele(Model model) {
        model.addAttribute("modele", new Modele());
        model.addAttribute("marques", marqueService.getAllMarques());
        return "admin/modeles/form";
    }

    @GetMapping("/modifier/{id}")
    public String modifierModele(@PathVariable Long id, Model model) {
        model.addAttribute("modele", modeleService.getModeleById(id));
        model.addAttribute("marques", marqueService.getAllMarques());
        return "admin/modeles/form";
    }

    @PostMapping("/save")
    public String sauvegarderModele(@Valid @ModelAttribute Modele modele,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "admin/modeles/form";
        }

        modeleService.saveModele(modele);
        redirectAttributes.addFlashAttribute("success",
                "Modèle " + (modele.getId() == null ? "ajouté" : "modifié") + " avec succès");
        return "redirect:/admin/modeles";
    }

    @GetMapping("/delete/{id}")
    public String supprimerModele(@PathVariable Long id,
                                  RedirectAttributes redirectAttributes) {
        try {
            modeleService.deleteModele(id);
            redirectAttributes.addFlashAttribute("success", "Modèle supprimé avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Impossible de supprimer ce modèle");
        }
        return "redirect:/admin/modeles";
    }
}
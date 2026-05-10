package com.example.AutoVente.controller;

import com.example.AutoVente.entity.Marque;
import com.example.AutoVente.service.MarqueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/marques")
public class MarqueController {

    @Autowired
    private MarqueService marqueService;

    @GetMapping
    public String listerMarques(Model model) {
        model.addAttribute("marques", marqueService.getAllMarques());
        return "admin/marques/liste";
    }

    @GetMapping("/ajouter")
    public String ajouterMarque(Model model) {
        model.addAttribute("marque", new Marque());
        model.addAttribute("titre", "Ajouter une marque");
        return "admin/marques/form";
    }

    @GetMapping("/modifier/{id}")
    public String modifierMarque(@PathVariable Long id, Model model) {
        Marque marque = marqueService.getMarqueById(id);
        model.addAttribute("marque", marque);
        model.addAttribute("titre", "Modifier la marque");
        return "admin/marques/form";
    }

    @PostMapping("/save")
    public String sauvegarderMarque(@Valid @ModelAttribute Marque marque,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "admin/marques/form";
        }

        boolean isNew = (marque.getId() == null);
        marqueService.saveMarque(marque);

        redirectAttributes.addFlashAttribute("success",
                "Marque " + (isNew ? "ajoutée" : "modifiée") + " avec succès");
        return "redirect:/admin/marques";
    }

    @GetMapping("/supprimer/{id}")
    public String supprimerMarque(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            marqueService.deleteMarque(id);
            redirectAttributes.addFlashAttribute("success", "Marque supprimée avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Impossible de supprimer cette marque");
        }
        return "redirect:/admin/marques";
    }
}
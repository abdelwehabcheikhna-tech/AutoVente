package com.example.AutoVente.controller;

import com.example.AutoVente.entity.Categorie;
import com.example.AutoVente.service.CategorieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
public class CategorieController {

    @Autowired
    private CategorieService categorieService;

    @GetMapping
    public String listerCategories(Model model) {
        model.addAttribute("categories", categorieService.getAllCategories());
        return "admin/categories/liste";
    }

    @GetMapping("/ajouter")
    public String ajouterCategorie(Model model) {
        model.addAttribute("categorie", new Categorie());
        return "admin/categories/form";
    }

    @GetMapping("/modifier/{id}")
    public String modifierCategorie(@PathVariable Long id, Model model) {
        model.addAttribute("categorie", categorieService.getCategorieById(id));
        return "admin/categories/form";
    }

    @PostMapping("/save")
    public String sauvegarderCategorie(@Valid @ModelAttribute Categorie categorie,
                                       BindingResult result,
                                       RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "admin/categories/form";
        }

        categorieService.saveCategorie(categorie);
        redirectAttributes.addFlashAttribute("success",
                "Catégorie " + (categorie.getId() == null ? "ajoutée" : "modifiée") + " avec succès");
        return "redirect:/admin/categories";
    }

    @GetMapping("/delete/{id}")
    public String supprimerCategorie(@PathVariable Long id,
                                     RedirectAttributes redirectAttributes) {

        try {
            categorieService.deleteCategorie(id);
            redirectAttributes.addFlashAttribute("success",
                    "Catégorie supprimée avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Impossible de supprimer cette catégorie");
        }

        return "redirect:/admin/categories";
    }

    @GetMapping("/toggle/{id}")
    public String toggleCategorie(@PathVariable Long id,
                                  RedirectAttributes redirectAttributes) {
        Categorie categorie = categorieService.getCategorieById(id);
        categorie.setActif(!categorie.isActif());
        categorieService.saveCategorie(categorie);

        redirectAttributes.addFlashAttribute("success",
                "Catégorie " + (categorie.isActif() ? "activée" : "désactivée"));
        return "redirect:/admin/categories";
    }
}
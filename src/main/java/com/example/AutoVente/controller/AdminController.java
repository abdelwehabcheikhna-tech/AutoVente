package com.example.AutoVente.controller;

import com.example.AutoVente.service.AnnonceService;
import com.example.AutoVente.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AnnonceService annonceService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("totalAnnonces",annonceService.count());
        model.addAttribute("totalUsers", userService.count());
        return "admin/dashboard";
    }
}
package com.example.AutoVente.controller;

import com.example.AutoVente.entity.User;
import com.example.AutoVente.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("error", "Identifiants invalides");
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute User user,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {

        if (userService.existsByEmail(user.getEmail())) {
            result.rejectValue("email", "error.user", "Email déjà utilisé");
        }

        if (userService.existsByUsername(user.getUsername())) {
            result.rejectValue("username", "error.user", "Nom d'utilisateur déjà utilisé");
        }

        if (result.hasErrors()) {
            return "auth/register";
        }

        userService.registerNewUser(user);
        redirectAttributes.addFlashAttribute("success",
                "Inscription réussie ! Veuillez vous connecter.");
        return "redirect:/login";
    }

    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "redirect:/login?logout";
    }
}
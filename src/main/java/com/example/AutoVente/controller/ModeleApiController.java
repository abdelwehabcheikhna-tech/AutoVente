package com.example.AutoVente.controller;

import com.example.AutoVente.entity.Modele;
import com.example.AutoVente.repository.ModeleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/modeles")
public class ModeleApiController {

    @Autowired
    private ModeleRepository modeleRepository;

    @GetMapping("/by-marque/{marqueId}")
    public List<Map<String, Object>> getModelesByMarque(@PathVariable Long marqueId) {
        List<Modele> modeles = modeleRepository.findByMarqueId(marqueId);
        return modeles.stream()
                .map(modele -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", modele.getId());
                    map.put("nom", modele.getNom());
                    map.put("marqueId", modele.getMarque().getId());
                    map.put("marqueNom", modele.getMarque().getNom());
                    return map;
                })
                .collect(Collectors.toList());
    }
}
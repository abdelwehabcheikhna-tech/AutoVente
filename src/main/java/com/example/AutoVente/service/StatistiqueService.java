package com.example.AutoVente.service;

import com.example.AutoVente.repository.AnnonceRepository;
import com.example.AutoVente.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatistiqueService {

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalAnnonces", annonceRepository.count());
        stats.put("totalUsers", userRepository.count());
        return stats;
    }
}
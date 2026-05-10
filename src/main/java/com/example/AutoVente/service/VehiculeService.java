package com.example.AutoVente.service;

import com.example.AutoVente.entity.Vehicule;
import com.example.AutoVente.repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VehiculeService {

    @Autowired
    private VehiculeRepository vehiculeRepository;

    public Vehicule saveVehicule(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    public Vehicule getVehiculeById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé"));
    }

    public Page<Vehicule> getAllVehicules(Pageable pageable) {
        return vehiculeRepository.findAll(pageable);
    }
}
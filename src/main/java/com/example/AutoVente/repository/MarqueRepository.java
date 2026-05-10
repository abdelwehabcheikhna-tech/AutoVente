package com.example.AutoVente.repository;

import com.example.AutoVente.entity.Marque;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarqueRepository extends JpaRepository<Marque, Long> {

    // Recherche par nom exact
    Optional<Marque> findByNom(String nom);

    // Vérifier si une marque existe
    boolean existsByNom(String nom);

    // Recherche par nom contenant (insensible à la casse)
    List<Marque> findByNomContainingIgnoreCase(String nom);

    // Pagination des marques
    Page<Marque> findAll(Pageable pageable);

    // Toutes les marques triées par nom
    List<Marque> findAllByOrderByNomAsc();

    // Recherche multi-critères
    @Query("SELECT m FROM Marque m WHERE " +
            "(:nom IS NULL OR LOWER(m.nom) LIKE LOWER(CONCAT('%', :nom, '%'))) AND " +
            "(:pays IS NULL OR LOWER(m.paysOrigine) LIKE LOWER(CONCAT('%', :pays, '%')))")
    Page<Marque> rechercheAvancee(@Param("nom") String nom,
                                  @Param("pays") String pays,
                                  Pageable pageable);

    // Compter le nombre de modèles par marque
    @Query("SELECT m.nom, COUNT(md) FROM Marque m LEFT JOIN m.modeles md GROUP BY m.id")
    List<Object[]> countModelesByMarque();

    // Marques populaires (avec le plus d'annonces)
    @Query("SELECT m, COUNT(a) FROM Marque m " +
            "LEFT JOIN m.vehicules v " +
            "LEFT JOIN v.annonce a " +
            "GROUP BY m.id " +
            "ORDER BY COUNT(a) DESC")
    List<Object[]> findMarquesPopulaires(Pageable pageable);

    // Marques avec au moins X véhicules
    @Query("SELECT m FROM Marque m WHERE SIZE(m.vehicules) >= :minVehicules")
    List<Marque> findMarquesAvecMinimumVehicules(@Param("minVehicules") int minVehicules);
}
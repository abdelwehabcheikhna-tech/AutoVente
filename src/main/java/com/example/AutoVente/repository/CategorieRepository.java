package com.example.AutoVente.repository;

import com.example.AutoVente.entity.Categorie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {

    // Recherche par nom exact
    Optional<Categorie> findByNom(String nom);

    // Vérifie si une catégorie existe par nom
    boolean existsByNom(String nom);

    // Recherche les catégories actives triées par ordre d'affichage
    List<Categorie> findByActifTrueOrderByOrdreAffichageAsc();

    // Recherche par nom contenant (insensible à la casse)
    List<Categorie> findByNomContainingIgnoreCase(String nom);

    // Pagination des catégories actives
    Page<Categorie> findByActifTrue(Pageable pageable);

    // Pagination des catégories inactives
    Page<Categorie> findByActifFalse(Pageable pageable);

    // Compte le nombre de véhicules par catégorie
    @Query("SELECT c.nom, COUNT(v) FROM Categorie c LEFT JOIN c.vehicules v GROUP BY c.id")
    List<Object[]> countVehiculesByCategorie();

    // Recherche de catégories avec au moins X véhicules
    @Query("SELECT c FROM Categorie c WHERE SIZE(c.vehicules) >= :minVehicules")
    List<Categorie> findCategoriesAvecMinimumVehicules(@Param("minVehicules") int minVehicules);

    // Recherche par mot-clé dans le nom ou la description
    @Query("SELECT c FROM Categorie c WHERE " +
            "LOWER(c.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Categorie> rechercherParMotCle(@Param("keyword") String keyword, Pageable pageable);

    // Catégories populaires (celles avec le plus de véhicules en vente)
    @Query("SELECT c, COUNT(a) FROM Categorie c " +
            "LEFT JOIN c.vehicules v " +
            "LEFT JOIN v.annonce a " +
            "WHERE a.statut = 'ACTIVE' " +
            "GROUP BY c.id " +
            "ORDER BY COUNT(a) DESC")
    List<Object[]> findCategoriesPopulaires(Pageable pageable);
}
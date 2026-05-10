package com.example.AutoVente.repository;

import com.example.AutoVente.entity.Marque;
import com.example.AutoVente.entity.Modele;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModeleRepository extends JpaRepository<Modele, Long> {

    // Recherche par nom
    List<Modele> findByNomContainingIgnoreCase(String nom);

    // Modèles d'une marque spécifique
    List<Modele> findByMarqueId(Long marqueId);

    // Modèles d'une marque triés par nom
    List<Modele> findByMarqueOrderByNomAsc(Marque marque);

    // Vérifier si un modèle existe pour une marque
    boolean existsByNomAndMarqueId(String nom, Long marqueId);

    // Pagination des modèles par marque
    Page<Modele> findByMarqueId(Long marqueId, Pageable pageable);

    // Recherche multi-critères
    @Query("SELECT m FROM Modele m WHERE " +
            "(:marqueId IS NULL OR m.marque.id = :marqueId) AND " +
            "(:nom IS NULL OR LOWER(m.nom) LIKE LOWER(CONCAT('%', :nom, '%'))) AND " +
            "(:anneeMin IS NULL OR m.anneeDebut >= :anneeMin) AND " +
            "(:anneeMax IS NULL OR m.anneeFin <= :anneeMax)")
    Page<Modele> rechercheAvancee(@Param("marqueId") Long marqueId,
                                  @Param("nom") String nom,
                                  @Param("anneeMin") Integer anneeMin,
                                  @Param("anneeMax") Integer anneeMax,
                                  Pageable pageable);

    // Trouver les modèles par année de début
    List<Modele> findByAnneeDebutLessThanEqual(Integer annee);

    // Modèles les plus récents (max 10)
    @Query(value = "SELECT * FROM modeles ORDER BY annee_debut DESC LIMIT :limit", nativeQuery = true)
    List<Modele> findModelesRecents(@Param("limit") int limit);

    // Compter le nombre de véhicules par modèle
    @Query("SELECT m.nom, COUNT(v) FROM Modele m LEFT JOIN m.vehicules v GROUP BY m.id")
    List<Object[]> countVehiculesByModele();

    ScopedValue<Object> findByNomAndMarqueNom(String modeleNom, String marqueNom);
}
package com.example.AutoVente.repository;

import com.example.AutoVente.entity.Vehicule;
import com.example.AutoVente.enums.Carburant;
import com.example.AutoVente.enums.Transmission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {

    // Recherche par marque
    Page<Vehicule> findByMarqueId(Long marqueId, Pageable pageable);

    // Recherche par modèle
    Page<Vehicule> findByModeleId(Long modeleId, Pageable pageable);

    // Recherche par année
    Page<Vehicule> findByAnneeBetween(Integer anneeMin, Integer anneeMax, Pageable pageable);

    // Recherche par carburant
    Page<Vehicule> findByCarburant(Carburant carburant, Pageable pageable);

    // Recherche par transmission
    Page<Vehicule> findByTransmission(Transmission transmission, Pageable pageable);

    // Recherche multi-critères avancée
    @Query("SELECT v FROM Vehicule v WHERE " +
            "(:marqueId IS NULL OR v.marque.id = :marqueId) AND " +
            "(:modeleId IS NULL OR v.modele.id = :modeleId) AND " +
            "(:anneeMin IS NULL OR v.annee >= :anneeMin) AND " +
            "(:anneeMax IS NULL OR v.annee <= :anneeMax) AND " +
            "(:kilometrageMax IS NULL OR v.kilometrage <= :kilometrageMax) AND " +
            "(:carburant IS NULL OR v.carburant = :carburant) AND " +
            "(:transmission IS NULL OR v.transmission = :transmission) AND " +
            "(:prixMin IS NULL OR v.annonce.prix >= :prixMin) AND " +
            "(:prixMax IS NULL OR v.annonce.prix <= :prixMax)")
    Page<Vehicule> rechercheAvancee(@Param("marqueId") Long marqueId,
                                    @Param("modeleId") Long modeleId,
                                    @Param("anneeMin") Integer anneeMin,
                                    @Param("anneeMax") Integer anneeMax,
                                    @Param("kilometrageMax") Integer kilometrageMax,
                                    @Param("carburant") Carburant carburant,
                                    @Param("transmission") Transmission transmission,
                                    @Param("prixMin") Double prixMin,
                                    @Param("prixMax") Double prixMax,
                                    Pageable pageable);

    // Trouver les véhicules par catégorie
    @Query("SELECT v FROM Vehicule v WHERE v.categorie.id = :categorieId")
    Page<Vehicule> findByCategorieId(@Param("categorieId") Long categorieId, Pageable pageable);

    // Véhicules récemment ajoutés
    @Query("SELECT v FROM Vehicule v ORDER BY v.dateCreation DESC")
    Page<Vehicule> findVehiculesRecents(Pageable pageable);

    // Statistiques par marque
    @Query("SELECT v.marque.nom, COUNT(v), AVG(v.annonce.prix), AVG(v.kilometrage) " +
            "FROM Vehicule v GROUP BY v.marque.id")
    List<Object[]> getStatistiquesParMarque();

    // Véhicules avec le meilleur rapport prix/kilométrage
    @Query("SELECT v FROM Vehicule v WHERE v.annonce.statut = 'ACTIVE' " +
            "ORDER BY (v.annonce.prix / NULLIF(v.kilometrage, 0)) ASC")
    Page<Vehicule> findMeilleurRapportPrixKm(Pageable pageable);
}
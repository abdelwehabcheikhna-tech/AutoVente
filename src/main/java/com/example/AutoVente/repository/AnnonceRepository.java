package com.example.AutoVente.repository;

import com.example.AutoVente.entity.Annonce;
import com.example.AutoVente.entity.User;
import com.example.AutoVente.enums.StatutAnnonce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SearchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {

    // Recherche avancée avec @Query
    @Query("SELECT a FROM Annonce a WHERE " +
            "(:marqueId IS NULL OR a.vehicule.marque.id = :marqueId) AND " +
            "(:modeleId IS NULL OR a.vehicule.modele.id = :modeleId) AND " +
            "(:prixMin IS NULL OR a.prix >= :prixMin) AND " +
            "(:prixMax IS NULL OR a.prix <= :prixMax) AND " +
            "(:anneeMin IS NULL OR a.vehicule.annee >= :anneeMin) AND " +
            "(:anneeMax IS NULL OR a.vehicule.annee <= :anneeMax) AND " +
            "(:kilometrageMax IS NULL OR a.vehicule.kilometrage <= :kilometrageMax) AND " +
            "(:carburant IS NULL OR a.vehicule.carburant = :carburant)")
    Page<Annonce> rechercheAvancee(@Param("marqueId") Long marqueId,
                                   @Param("modeleId") Long modeleId,
                                   @Param("prixMin") Double prixMin,
                                   @Param("prixMax") Double prixMax,
                                   @Param("anneeMin") Integer anneeMin,
                                   @Param("anneeMax") Integer anneeMax,
                                   @Param("kilometrageMax") Integer kilometrageMax,
                                   @Param("carburant") String carburant,
                                   Pageable pageable);


    Page<Annonce> findById(User vendeur, Pageable pageable);


}
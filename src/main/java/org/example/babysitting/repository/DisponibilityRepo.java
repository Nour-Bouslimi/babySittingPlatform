package org.example.babysitting.repository;

import org.example.babysitting.entities.Disponibilite;
import org.springframework.data.jpa.repository.JpaRepository;


import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface DisponibilityRepo extends JpaRepository<Disponibilite, Long> {

    //named methods
    List<Disponibilite> findByIdUser(Long IdUser);
    List<Disponibilite> findByDate(LocalDate date);

    List<Disponibilite> findByHeureDebutAndHeureFin(int heureDebut, int heureFin);
    boolean existsByIdDispo(Long idDispo);

}

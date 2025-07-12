package org.example.babysitting.repository;

import org.example.babysitting.entities.Reservation;
import org.example.babysitting.entities.Statut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {

    //Named methods
    List<Reservation> findByParent_IdUser(Long user_idUser);

    List<Reservation> findByDate(LocalDate date);

    List<Reservation> findByHeureDebutBetween(int heureDebut, int heureFin);
    boolean existsByIdReserv(Long idReserv);
    List<Reservation> findByStatut(Statut statut);
    List<Reservation> findByNounou_IdUser(Long user_idUser);

}

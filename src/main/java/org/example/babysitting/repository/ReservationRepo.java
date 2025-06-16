package org.example.babysitting.repository;

import org.example.babysitting.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {

    //Named methods
    List<Reservation> findByIdUser(Long idUser);

    List<Reservation> findByDate(Date date);

    List<Reservation> findByHeureDebutBetween(int heureDebut, int heureFin);
    boolean existsByIdReserv(Long idReserv);
}

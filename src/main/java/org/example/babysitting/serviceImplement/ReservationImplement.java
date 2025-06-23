package org.example.babysitting.serviceImplement;

import org.example.babysitting.entities.Reservation;
import org.example.babysitting.entities.Statut;
import org.example.babysitting.repository.ReservationRepo;
import org.example.babysitting.service.ReservationInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Service
public class ReservationImplement implements ReservationInterface {

    @Autowired
    ReservationRepo reservationRepo;
    @Override
    public Reservation addReservation(Reservation reservation) {
        return reservationRepo.save(reservation);

    }

    @Override
    public List<Reservation> addListReservations(List<Reservation> reservations) {
        return reservationRepo.saveAll(reservations);
    }

    @Override
    public void deleteReservation(Long id) {
        if(!reservationRepo.existsByIdReserv(id)) {
            throw new RuntimeException("Reservation with id " + id + " does not exist");
        }
        reservationRepo.deleteById(id);

    }



    @Override
    public Reservation updateReservation(Long id, Reservation reservation) {
        Reservation r = getReservationById(id);
        if (r != null) {
            r.setDate(reservation.getDate());
            r.setHeureDebut(reservation.getHeureDebut());
            r.setHeureFin(reservation.getHeureFin());
            r.setIdUser(reservation.getIdUser());
            return reservationRepo.save(r);
        } else {
            return null;
        }

    }

    @Override
    public Reservation updateStatusReservation(Long id, String status) {
        Reservation r = getReservationById(id);
        if (r != null) {
            r.setStatut(Statut.valueOf(status));
            return reservationRepo.save(r);
        } else {
            return null;
        }
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepo.findAll();
    }

    @Override
    public Reservation getReservationById(Long id) {
        return reservationRepo.findById(id).orElse(null);
    }

    @Override
    public List<Reservation> getReservationByIdUser(Long idUser) {
        return reservationRepo.findByIdUser(idUser);
    }

    @Override
    public List<Reservation> getReservationByDate(LocalDate date) {
        return reservationRepo.findByDate(date);
    }

    @Override
    public List<Reservation> getReservationByTimeRange(int heureDebut, int heureFin) {
        return reservationRepo.findByHeureDebutBetween(heureDebut, heureFin);
    }

    @Override
    public List<Reservation> getReservationByStatut(Statut statut) {
       return reservationRepo.findByStatut(statut);
    }
}

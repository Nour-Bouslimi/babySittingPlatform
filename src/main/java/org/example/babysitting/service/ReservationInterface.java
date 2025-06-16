package org.example.babysitting.service;

import org.example.babysitting.entities.Disponibilite;
import org.example.babysitting.entities.Reservation;

import java.sql.Date;
import java.util.List;

public interface ReservationInterface {

    Reservation addReservation(Reservation reservation);
    void deleteReservation(Long id);
    List<Reservation> addListReservations(List<Reservation> reservations); //ajout de la méthode pour ajouter une liste de réservations
    Reservation updateReservation(long id, Reservation reservation); //ajout de la méthode pour mettre à jour une réservation
    Reservation updateStatusReservation(long id, String status); //ajout de la méthode pour mettre à jour le statut d'une réservation
    List<Reservation> getAllReservations(); //ajout de la méthode pour récupérer toutes les réservations
    Reservation getReservationById(Long id); //ajout de la méthode pour récupérer une réservation par son ID
    List<Reservation> getReservationByIdUser(Long idUser); //ajout de la méthode pour récupérer les réservations par l'ID de l'utilisateur
    List<Reservation> getReservationByDate(Date date); //ajout de la méthode pour récupérer les réservations par date
    List<Reservation> getReservationByTimeRange(int heureDebut, int heureFin); //ajout de la méthode pour récupérer les réservations par plage horaire



}

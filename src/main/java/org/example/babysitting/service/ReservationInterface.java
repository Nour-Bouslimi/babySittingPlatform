package org.example.babysitting.service;

import org.example.babysitting.entities.Disponibilite;
import org.example.babysitting.entities.Reservation;
import org.example.babysitting.entities.Statut;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ReservationInterface {

    Reservation addReservation(Reservation reservation);
    void deleteReservation(Long id);
    List<Reservation> addListReservations(List<Reservation> reservations); //ajout de la méthode pour ajouter une liste de réservations
    Reservation updateReservation(Long id, Reservation reservation); //ajout de la méthode pour mettre à jour une réservation
    Reservation updateStatusReservation(Long id, String status); //ajout de la méthode pour mettre à jour le statut d'une réservation
    List<Reservation> getAllReservations(); //ajout de la méthode pour récupérer toutes les réservations
    Reservation getReservationById(Long id); //ajout de la méthode pour récupérer une réservation par son ID
    List<Reservation> getReservationByParent_idUser(Long user_idUser); //ajout de la méthode pour récupérer les réservations par l'ID de l'utilisateur
    List<Reservation> getReservationByDate(Date date); //ajout de la méthode pour récupérer les réservations par date
    List<Reservation> getReservationByTimeRange(int heureDebut, int heureFin); //ajout de la méthode pour récupérer les réservations par plage horaire
    List<Reservation> getReservationByStatut(Statut statut); //ajout de la méthode pour récupérer les réservations par statut
   List<Reservation> getReservationByNounou_idUser(Long user_idUser); //ajout de la méthode pour récupérer les réservations par l'ID de la nounou
    public void markReservationAsAccepted(Long id); //ajout de la méthode pour marquer une réservation comme acceptée
    public void markReservationAsRejected(Long id); //ajout de la méthode pour marquer une réservation comme rejetée

}

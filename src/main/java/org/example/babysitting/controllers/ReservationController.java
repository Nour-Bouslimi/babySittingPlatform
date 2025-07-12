package org.example.babysitting.controllers;

import org.example.babysitting.entities.Reservation;
import org.example.babysitting.entities.Statut;
import org.example.babysitting.entities.User;
import org.example.babysitting.service.ReservationInterface;
import org.example.babysitting.serviceImplement.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@CrossOrigin(origins= "*",allowedHeaders = "*") // Allows all origins, you can specify a specific origin if needed
public class ReservationController {
    @Autowired
     ReservationInterface reservationInterface;
    @Autowired
    private NotificationService notificationService;
    @PostMapping("/addReservation/{idParent}/{idNounou}")
    public Reservation addReservation(@PathVariable Long idParent,@PathVariable Long idNounou,@RequestBody Reservation reservation) {


            User parent = new User();
            parent.setIdUser(idParent);

            reservation.setParent(parent);

            User nounou = new User();
            nounou.setIdUser(idNounou);
            reservation.setNounou(nounou);

        Reservation saved= reservationInterface.addReservation(reservation);
        Long parentId = reservation.getParent().getIdUser();
        Long nounouId = reservation.getNounou().getIdUser();
        // Envoi de la notification
        notificationService.sendNotification(parentId, "Your New Reservation made successfully !");
        notificationService.sendNotification(nounouId, "A New Reservation made on you !");
        return saved;

    }

    @PostMapping("/addListReservations")
    public List<Reservation> addListReservations(@RequestBody List<Reservation> reservations) {
        return reservationInterface.addListReservations(reservations);
    }
    @DeleteMapping("/deleteReservation/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationInterface.deleteReservation(id);
    }
    @PutMapping("/updateReservation/{id}")
    public Reservation updateReservation(@PathVariable Long id,@RequestBody Reservation reservation) {
        return reservationInterface.updateReservation(id, reservation);
    }
    @PutMapping("/updateStatusReservation/{id}")
    public Reservation updateStatusReservation(@PathVariable Long id, @RequestParam String status) {
        return reservationInterface.updateStatusReservation(id, status);
    }
    @PutMapping("/markReservationAsAccepted/{id}")
    public void markReservationAsAccepted(@PathVariable Long id) {
        reservationInterface.markReservationAsAccepted(id);
    }
    @PutMapping("/markReservationAsRejected/{id}")
    public void markReservationAsRejected(@PathVariable Long id) {
        reservationInterface.markReservationAsRejected(id);
    }
    @GetMapping("/getAllReservations")
    public List<Reservation> getAllReservations() {
        return reservationInterface.getAllReservations();
    }
    @GetMapping("/getReservationById/{id}")
    public Reservation getReservationById(@PathVariable Long id) {
        return reservationInterface.getReservationById(id);
    }
    @GetMapping("/getReservationByUserId/{user_idUser}")
    public List<Reservation> getReservationByUserId(@PathVariable Long user_idUser) {
        return reservationInterface.getReservationByParent_idUser(user_idUser);
    }
    @GetMapping("/getReservationByDate/{date}")
    public List<Reservation> getReservationByDate(@PathVariable String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date); // Conversion de la chaîne en LocalDate
            return reservationInterface.getReservationByDate(parsedDate);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Format de date invalide. Utilisez 'yyyy-MM-dd'.");
        }
    }
    @GetMapping("/getReservationByTimeRange")
    public List<Reservation> getReservationByTimeRange(@RequestParam int heureDebut, @RequestParam int heureFin) {
        return reservationInterface.getReservationByTimeRange(heureDebut, heureFin);
    }
    @GetMapping("/getReservationByStatut/{statut}")
    public List<Reservation> getReservationByStatut(@PathVariable String statut) {
        return reservationInterface.getReservationByStatut(Statut.valueOf(statut.toUpperCase()));
    }
    @GetMapping("/getReservationByNounouId/{idNounou}")
    public List<Reservation> getReservationByNounouId(@PathVariable Long idNounou) {
        return reservationInterface.getReservationByNounou_idUser(idNounou);
    }

}

package org.example.babysitting.controllers;

import org.example.babysitting.entities.Reservation;
import org.example.babysitting.service.ReservationInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
     ReservationInterface reservationInterface;

    @PostMapping("/addReservation")
    public Reservation addReservation(@RequestBody Reservation reservation) {
        return reservationInterface.addReservation(reservation);

    }

    @PostMapping("/addListReservations")
    public List<Reservation> addListReservations(@RequestBody List<Reservation> reservations) {
        return reservationInterface.addListReservations(reservations);
    }
    @DeleteMapping("/deleteReservation/{id}")
    public void deleteReservation(@PathVariable long id) {
        reservationInterface.deleteReservation(id);
    }
    @PutMapping("/updateReservation/{id}")
    public Reservation updateReservation(@PathVariable long id,@RequestBody Reservation reservation) {
        return reservationInterface.updateReservation(id, reservation);
    }
    @PutMapping("/updateStatusReservation/{id}")
    public Reservation updateStatusReservation(@PathVariable long id, @RequestParam String status) {
        return reservationInterface.updateStatusReservation(id, status);
    }
    @GetMapping("/getAllReservations")
    public List<Reservation> getAllReservations() {
        return reservationInterface.getAllReservations();
    }
    @GetMapping("/getReservationById/{id}")
    public Reservation getReservationById(@PathVariable long id) {
        return reservationInterface.getReservationById(id);
    }
    @GetMapping("/getReservationByUserId/{idUser}")
    public List<Reservation> getReservationByUserId(@PathVariable long idUser) {
        return reservationInterface.getReservationByIdUser(idUser);
    }
    @GetMapping("/getReservationByDate/{date}")
    public List<Reservation> getReservationByDate(@PathVariable String date) {
        return reservationInterface.getReservationByDate(Date.valueOf(date));
    }
    @GetMapping("/getReservationByTimeRange")
    public List<Reservation> getReservationByTimeRange(@RequestParam int heureDebut, @RequestParam int heureFin) {
        return reservationInterface.getReservationByTimeRange(heureDebut, heureFin);
    }

}

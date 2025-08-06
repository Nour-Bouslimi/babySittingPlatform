package org.example.babysitting.controllers;

import org.example.babysitting.entities.Disponibilite;
import org.example.babysitting.service.DisponibilityInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/disponibility") // This will map all requests starting with /disponibility to this controller
@CrossOrigin(origins = "http://localhost:4200")  // autorise uniquement le front Angular
public class DisponibilityController {
    @Autowired
     DisponibilityInterface disponibilityInterface;
    @PostMapping("/addDisponibility")
    public Disponibilite addDisponibility(@RequestBody Disponibilite disponibilite) {
        return disponibilityInterface.addDisponibilite(disponibilite);

    }
    @PostMapping("/addListDisponibilities")
    public List<Disponibilite> addListDisponibilities(@RequestBody List<Disponibilite> disponibilites) {
        return disponibilityInterface.addListDisponibilites(disponibilites);
    }
    @DeleteMapping("/deleteDisponibility/{id}")
    public void deleteDisponibility(@PathVariable Long id) {
        disponibilityInterface.deleteDisponibilite(id);
    }
    @PutMapping("/updateDisponibility/{id}")
    public Disponibilite updateDisponibility(@PathVariable Long id, @RequestBody Disponibilite disponibilite) {
        return disponibilityInterface.updateDisponibilite(id, disponibilite);
    }
    @GetMapping("/getAllDisponibilities")
    public List<Disponibilite> getAllDisponibilities() {
        return disponibilityInterface.getAllDisponibilites();
    }
    @GetMapping("/getDisponibilityById/{id}")
    public Disponibilite getDisponibilityById(@PathVariable Long id) {
        return disponibilityInterface.getDisponibiliteById(id);
    }
    @GetMapping("/getDisponibilityByUserId/{idUser}")
    public List<Disponibilite> getDisponibilityByUser_idUser(@PathVariable Long idUser) {
        return disponibilityInterface.getDisponibiliteByUser_idUser(idUser);
    }
    @GetMapping("/getDisponibilityByDate/{date}")
    public List<Disponibilite> getDisponibilityByDate(@PathVariable String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, formatter);
            return disponibilityInterface.getDisponibiliteByDate(localDate);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Format de date invalide. Utilisez 'yyyy-MM-dd'.");
        }

    }
    @GetMapping("/getDisponibilityByTimeRange/{date}/{heureDebut}/{heureFin}")
    public List<Disponibilite> getDisponibilityByTimeRange(@PathVariable String date,@PathVariable int heureDebut, @PathVariable int heureFin) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, formatter);
            return disponibilityInterface.getDisponibiliteByTimeRange(localDate, heureDebut, heureFin);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Format de date invalide. Utilisez 'yyyy-MM-dd'.");
        }

    }
    @GetMapping("/getDisponibilityByUserIdAndDate/{userId}/{date}")
    public List<Disponibilite> getDisponibilityByUser_idUserAndDate(@PathVariable Long userId, @PathVariable String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, formatter);
            return disponibilityInterface.getDisponibiliteByUser_idUserAndDate(userId, localDate);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Format de date invalide. Utilisez 'yyyy-MM-dd'.");
        }
    }
}

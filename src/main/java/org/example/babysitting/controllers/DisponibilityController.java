package org.example.babysitting.controllers;

import org.example.babysitting.entities.Disponibilite;
import org.example.babysitting.service.DisponibilityInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/disponibility") // This will map all requests starting with /disponibility to this controller
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
    public Disponibilite updateDisponibility(@PathVariable long id, @RequestBody Disponibilite disponibilite) {
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
    @GetMapping("/getDisponibilityByUserId/{userId}")
    public List<Disponibilite> getDisponibilityByUserId(@PathVariable Long IdUser) {
        return disponibilityInterface.getDisponibiliteByIdUser(IdUser);
    }
    @GetMapping("/getDisponibilityByDate/{date}")
    public List<Disponibilite> getDisponibilityByDate(@PathVariable String date) {
        return disponibilityInterface.getDisponibiliteByDate(Date.valueOf(date));
    }
    @GetMapping("/getDisponibilityByTimeRange")
    public List<Disponibilite> getDisponibilityByTimeRange(@RequestParam int heureDebut, @RequestParam int heureFin) {
        return disponibilityInterface.getDisponibiliteByTimeRange(heureDebut, heureFin);
    }
}

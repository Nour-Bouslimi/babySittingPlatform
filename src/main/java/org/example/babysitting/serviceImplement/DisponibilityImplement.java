package org.example.babysitting.serviceImplement;

import org.example.babysitting.entities.Disponibilite;
import org.example.babysitting.repository.DisponibilityRepo;
import org.example.babysitting.service.DisponibilityInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Service
public class DisponibilityImplement implements DisponibilityInterface {

    @Autowired
    private DisponibilityRepo disponibilityRepo;

    @Override
    public Disponibilite addDisponibilite(Disponibilite disponibilite) {
        return disponibilityRepo.save(disponibilite);

    }
    @Override
    public List<Disponibilite> addListDisponibilites(List<Disponibilite> disponibilites) {
        return disponibilityRepo.saveAll(disponibilites);
    }

    @Override
    public void deleteDisponibilite(Long id) {
        if(!disponibilityRepo.existsByIdDispo(id)) {
            throw new RuntimeException("Disponibilite with id " + id + " does not exist");
        }
        disponibilityRepo.deleteById(id);

    }

    @Override
    public Disponibilite updateDisponibilite(Long id, Disponibilite disponibilite) {
        Disponibilite d = getDisponibiliteById(id);
        if (d != null) {
            d.setDate(disponibilite.getDate());
            d.setHeureDebut(disponibilite.getHeureDebut());
            d.setHeureFin(disponibilite.getHeureFin());
            d.setUser_idUser(disponibilite.getUser_idUser());

            return disponibilityRepo.save(d);
        } else {
            return null;


        }
    }
    @Override
    public Disponibilite getDisponibiliteById(Long id) {
        return disponibilityRepo.findById(id).orElse(null);
    }

    @Override
    public List<Disponibilite> getAllDisponibilites() {
        return disponibilityRepo.findAll();
    }
    @Override
    public List<Disponibilite> getDisponibiliteByDate(LocalDate date) {
        return disponibilityRepo.findByDate(date);

    }



    @Override
    public List<Disponibilite> getDisponibiliteByUser_idUser(Long IdUser) {
        return disponibilityRepo.findByUser_idUser(IdUser);
    }


    @Override
    public List<Disponibilite> getDisponibiliteByTimeRange(LocalDate date,int heureDebut, int heureFin) {
        return disponibilityRepo.findByDateAndHeureDebutAndHeureFin(date,heureDebut, heureFin);
    }

    @Override
    public List<Disponibilite> getDisponibiliteByUser_idUserAndDate(Long idUser, LocalDate date) {
        return disponibilityRepo.findByUser_idUserAndDate(idUser, date);
    }

}

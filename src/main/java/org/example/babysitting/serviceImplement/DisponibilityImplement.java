package org.example.babysitting.serviceImplement;

import org.example.babysitting.entities.Disponibilite;
import org.example.babysitting.repository.DisponibilityRepo;
import org.example.babysitting.service.DisponibilityInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
@Service
public class DisponibilityImplement implements DisponibilityInterface {
    @Autowired
    DisponibilityRepo disponibiliteRepo;
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
    public Disponibilite updateDisponibilite(long id, Disponibilite disponibilite) {
        Disponibilite d = getDisponibiliteById(id);
        if (d != null) {
            d.setDate(disponibilite.getDate());
            d.setHeureDebut(disponibilite.getHeureDebut());
            d.setHeureFin(disponibilite.getHeureFin());
            d.setIdUser(disponibilite.getIdUser());
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
    public List<Disponibilite> getDisponibiliteByDate(Date date) {
        return disponibilityRepo.findByDate(date);

    }



    @Override
    public List<Disponibilite> getDisponibiliteByUserId(Long userId) {
        return disponibilityRepo.findByIdUser(userId);
    }


    @Override
    public List<Disponibilite> getDisponibiliteByTimeRange(int heureDebut, int heureFin) {
        return disponibilityRepo.findByHeureDebutAndHeureFin(heureDebut, heureFin);
    }

}

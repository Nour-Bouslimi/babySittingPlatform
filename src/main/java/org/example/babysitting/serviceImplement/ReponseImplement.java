package org.example.babysitting.serviceImplement;

import org.example.babysitting.entities.Reponse;
import org.example.babysitting.repository.ReponseRepo;
import org.example.babysitting.service.ReponseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
@Service
public class ReponseImplement implements ReponseInterface {
    @Autowired
    ReponseRepo reponseRepo;

    @Override
    public Reponse addReponse(Reponse reponse) {
        return reponseRepo.save(reponse);
    }

    @Override
    public void deleteReponse(Long id) {
        if (reponseRepo.existsByIdReponse(id)) {
            reponseRepo.deleteById(id);
        } else {
            throw new IllegalArgumentException("Reponse with id " + id + " does not exist.");
        }

    }

    @Override
    public List<Reponse> addListReponses(List<Reponse> reponses) {
        return reponseRepo.saveAll(reponses);
    }

    @Override
    public Reponse updateReponse(Long id, Reponse reponse) {
        Reponse r= getReponseById(id);
        if (r != null) {
            r.setContent(reponse.getContent());
            r.setDate(reponse.getDate());
            r.setIdMsg(reponse.getIdMsg());
            r.setIdUser(reponse.getIdUser());
            return reponseRepo.save(r);
        } else {
            throw new IllegalArgumentException("Reponse with id " + id + " does not exist.");
        }
    }

    @Override
    public List<Reponse> getAllReponses() {
        return reponseRepo.findAll();
    }

    @Override
    public Reponse getReponseById(Long id) {
        return reponseRepo.findById(id).orElse(null);
    }

    @Override
    public List<Reponse> getReponsesByIdMsg(Long idMsg) {
        return reponseRepo.findByIdMsg(idMsg);
    }

    @Override
    public List<Reponse> getReponsesByIdUser(Long idUser) {
        return reponseRepo.findByIdUser(idUser);
    }

    @Override
    public List<Reponse> getReponsesByDate(LocalDate date) {
        return reponseRepo.findByDate(date);
    }

    @Override
    public List<Reponse> getReponsesByIdMsgAndIdUser(Long idMsg, Long idUser) {
        return reponseRepo.findByIdMsgAndIdUser(idMsg, idUser);
    }

    @Override
    public List<Reponse> getReponsesByIdMsgAndDate(Long idMsg, LocalDate date) {
        return reponseRepo.findByIdMsgAndDate(idMsg, date);
    }

    @Override
    public List<Reponse> getReponsesByIdUserAndDate(Long idUser, LocalDate date) {
        return reponseRepo.findByIdUserAndDate(idUser, date);
    }
}

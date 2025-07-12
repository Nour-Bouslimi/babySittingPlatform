package org.example.babysitting.serviceImplement;

import org.example.babysitting.entities.Message;
import org.example.babysitting.entities.Reponse;
import org.example.babysitting.entities.User;
import org.example.babysitting.repository.MessageRepo;
import org.example.babysitting.repository.ReponseRepo;
import org.example.babysitting.repository.UserRepo;
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
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MessageRepo messageRepo;

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
            r.setMessage_idMsg(reponse.getMessage_idMsg());
            r.setSender_idUser(reponse.getSender_idUser());

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
    public List<Reponse> getReponsesByMessage_idMsg(Long idMsg) {
        return reponseRepo.findByMessage_idMsg(idMsg);
    }

    @Override
    public List<Reponse> getReponsesBySender_idUser(Long idUser) {
        return reponseRepo.findBySender_idUser(idUser);
    }

    @Override
    public List<Reponse> getReponsesByDate(LocalDate date) {
        return reponseRepo.findByDate(date);
    }

    @Override
    public List<Reponse> getReponsesByMessage_idMsgAndSender_idUser(Long idMsg, Long idUser) {
        return reponseRepo.findByMessage_idMsgAndSender_idUser(idMsg, idUser);
    }

    @Override
    public List<Reponse> getReponsesByMessage_idMsgAndDate(Long idMsg, LocalDate date) {
        return reponseRepo.findByMessage_idMsgAndDate(idMsg, date);
    }

    @Override
    public List<Reponse> getReponsesBySender_idUserAndDate(Long idUser, LocalDate date) {
        return reponseRepo.findBySender_idUserAndDate(idUser, date);
    }

    @Override
    public Reponse sendMessageAsResponse(Long senderId,Long receiverId, Long messageId, String content) {
        User sender = userRepo.findById(senderId).orElse(null);
        User receiver = userRepo.findById(receiverId).orElse(null);
        Message msg = messageRepo.findByIdMsg(messageId);

        Reponse  reponse = new Reponse();

        reponse.setSender(sender);
        reponse.setReceiver(receiver);
        reponse.setContent(content);
        reponse.setMessage_idMsg(msg);
        reponse.setDate(LocalDate.now());
        return reponseRepo.save(reponse);

    }
}

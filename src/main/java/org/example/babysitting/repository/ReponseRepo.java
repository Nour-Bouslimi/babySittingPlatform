package org.example.babysitting.repository;

import org.example.babysitting.entities.Message;
import org.example.babysitting.entities.Reponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface ReponseRepo extends JpaRepository<Reponse, Long> {
    //Named methods
    List<Reponse> findByMessage_idMsg(Long idMsg);
    List<Reponse> findBySender_idUser(Long idUser);
    List<Reponse> findByDate(LocalDate date);
    Reponse findByIdReponse(Long idReponse); //ajout de la méthode pour récupérer une réponse par son ID
    boolean existsByIdReponse(Long idReponse);
    List<Reponse> findByMessage_idMsgAndSender_idUser(Long idMsg, Long idUser); //ajout de la méthode pour récupérer les réponses par l'ID du message et l'ID de l'utilisateur
    List<Reponse> findByMessage_idMsgAndDate(Long idMsg, LocalDate date); //ajout de la méthode pour récupérer les réponses par l'ID du message et la date
    List<Reponse> findBySender_idUserAndDate(Long idUser, LocalDate date); //ajout de la méthode pour récupérer les réponses par l'ID de l'utilisateur et la date





}

package org.example.babysitting.repository;

import org.example.babysitting.entities.Message;
import org.example.babysitting.entities.Reponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface ReponseRepo extends JpaRepository<Reponse, Long> {
    //Named methods
    List<Reponse> findByIdMsg(Long idMsg);
    List<Reponse> findByIdUser(Long idUser);
    List<Reponse> findByDate(LocalDate date);
    boolean existsByIdReponse(Long idReponse);
    List<Reponse> findByIdMsgAndIdUser(Long idMsg, Long idUser); //ajout de la méthode pour récupérer les réponses par l'ID du message et l'ID de l'utilisateur
    List<Reponse> findByIdMsgAndDate(Long idMsg, LocalDate date); //ajout de la méthode pour récupérer les réponses par l'ID du message et la date
    List<Reponse> findByIdUserAndDate(Long idUser, LocalDate date); //ajout de la méthode pour récupérer les réponses par l'ID de l'utilisateur et la date





}

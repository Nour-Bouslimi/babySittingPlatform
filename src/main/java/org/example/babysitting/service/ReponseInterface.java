package org.example.babysitting.service;

import org.example.babysitting.entities.Message;
import org.example.babysitting.entities.Reponse;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface ReponseInterface {

    Reponse addReponse(Reponse reponse);
    void deleteReponse(Long id);
    List<Reponse> addListReponses(List<Reponse> reponses); //ajout de la méthode pour ajouter une liste de réponses
    Reponse updateReponse(Long id, Reponse reponse); //ajout de la méthode pour mettre à jour une réponse
    List<Reponse> getAllReponses(); //ajout de la méthode pour récupérer toutes les réponses
    Reponse getReponseById(Long id); //ajout de la méthode pour récupérer une réponse par son ID
    List<Reponse> getReponsesByMessage_idMsg(Long idMsg); //ajout de la méthode pour récupérer les réponses par l'ID du message
    List<Reponse> getReponsesBySender_idUser(Long idUser); //ajout de la méthode pour récupérer les réponses par l'ID de l'utilisateur
    List<Reponse> getReponsesByDate(LocalDate date); //ajout de la méthode pour récupérer les réponses par date
    List<Reponse> getReponsesByMessage_idMsgAndSender_idUser(Long idMsg, Long idUser); //ajout de la méthode pour récupérer les réponses par l'ID du message et l'ID de l'utilisateur
    List<Reponse> getReponsesByMessage_idMsgAndDate(Long idMsg, LocalDate date); //ajout de la méthode pour récupérer les réponses par l'ID du message et la date
    List<Reponse> getReponsesBySender_idUserAndDate(Long idUser, LocalDate date); //ajout de la méthode pour récupérer les réponses par l'ID de l'utilisateur et la date
    Reponse sendMessageAsResponse(Long senderId,Long receiverId, Long messageId, String content);
}

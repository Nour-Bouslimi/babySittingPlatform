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
    List<Reponse> getReponsesByIdMsg(Long idMsg); //ajout de la méthode pour récupérer les réponses par l'ID du message
    List<Reponse> getReponsesByIdUser(Long idUser); //ajout de la méthode pour récupérer les réponses par l'ID de l'utilisateur
    List<Reponse> getReponsesByDate(LocalDate date); //ajout de la méthode pour récupérer les réponses par date
    List<Reponse> getReponsesByIdMsgAndIdUser(Long idMsg, Long idUser); //ajout de la méthode pour récupérer les réponses par l'ID du message et l'ID de l'utilisateur
    List<Reponse> getReponsesByIdMsgAndDate(Long idMsg, LocalDate date); //ajout de la méthode pour récupérer les réponses par l'ID du message et la date
    List<Reponse> getReponsesByIdUserAndDate(Long idUser, LocalDate date); //ajout de la méthode pour récupérer les réponses par l'ID de l'utilisateur et la date

}

package org.example.babysitting.service;

import org.example.babysitting.entities.Message;
import org.example.babysitting.entities.Reservation;

import java.sql.Date;
import java.util.List;

public interface MessageInterface {

    Message addMessage(Message message);
    void deleteMessage(Long id);
    List<Message> addListMessages(List<Message> messages); //ajout de la méthode pour ajouter une liste de messages
    Message updateMessage(long id, Message message); //ajout de la méthode pour mettre à jour un message
    List<Message> getAllMessages(); //ajout de la méthode pour récupérer tous les messages
    Message getMessageById(Long id); //ajout de la méthode pour récupérer un message par son ID
    List<Message> getMessagesByIdUSer(Long idUser); //ajout de la méthode pour récupérer les messages par l'ID de l'expéditeur
    List<Message> getMessagesByDate(Date date); //ajout de la méthode pour récupérer les messages par date




}

package org.example.babysitting.service;

import org.example.babysitting.entities.Message;
import org.example.babysitting.entities.Notification;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface NotificationInterface {

    Notification addNotification(Notification notification);
    void deleteNotification(Long id);
    void deleteAllNotifications(Long user_idUser); //ajout de la méthode pour supprimer toutes les notifications
    List<Notification> addListNotifications(List<Notification> notifications); //ajout de la méthode pour ajouter une liste de notifications
    List<Notification> getAllNotifications(); //ajout de la méthode pour récupérer toutes les notifications
    Notification getNotificationById(Long id); //ajout de la méthode pour récupérer une notification par son ID
    List<Notification> getNotificationsByUser_idUser(Long user_idUser); //ajout de la méthode pour récupérer les notifications par l'ID de l'utilisateur
    List<Notification> getUnreadNotificationsByUser_idUser(Long user_idUser); //ajout de la méthode pour récupérer les notifications non lues par l'ID de l'utilisateur
    void markNotificationAsRead(Long id); //ajout de la méthode pour marquer une notification comme lue
    void markAllNotificationsAsRead(Long user_idUser); //ajout de la méthode pour marquer toutes les notifications comme lues
    List<Notification> getNotificationsByDate(LocalDate date); //ajout de la méthode pour récupérer les notifications par date


}

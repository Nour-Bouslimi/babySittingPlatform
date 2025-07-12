package org.example.babysitting.serviceImplement;

import org.example.babysitting.entities.Notification;
import org.example.babysitting.service.NotificationInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
@Autowired
    NotificationInterface notificationInterface;
    public void sendNotification(Long userId, String message) {
       //enregistrement de la notification dans la base de données
        org.example.babysitting.entities.Notification notification = new Notification();
       notification.setUser_idUser(userId);

        notification.setMessage(message);
        notification.setRead(false); // Par défaut, la notification est marquée comme non lue
        notificationInterface.addNotification(notification);

        // Envoi de la notification via WebSocket
        messagingTemplate.convertAndSend("/user/" + userId + "/queue/notifications", message);
    }
}

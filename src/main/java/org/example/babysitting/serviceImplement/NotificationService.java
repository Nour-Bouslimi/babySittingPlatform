package org.example.babysitting.serviceImplement;

import org.example.babysitting.entities.Notification;
import org.example.babysitting.entities.User;
import org.example.babysitting.repository.UserRepo;
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
    @Autowired
    private UserRepo userRepo;

    public void sendNotification(Long userId, String message) {
        System.out.println("📩 Envoi de notification pour l'utilisateur " + userId);

        //enregistrement de la notification dans la base de données
        org.example.babysitting.entities.Notification notification = new Notification();
        // ✅ charger le user existant depuis la BDD
       /* User user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            System.out.println("❌ Utilisateur introuvable, pas de notification envoyée");
            return;
        }*/
       notification.setUser_idUser(userId);
        //notification.setUser(user); // Associer l'utilisateur à la notification
        notification.setMessage(message);
        notification.setRead(false); // Par défaut, la notification est marquée comme non lue
        notificationInterface.addNotification(notification);

        // Envoi de la notification via WebSocket
        messagingTemplate.convertAndSend("/user/" + userId + "/queue/notifications", message);
        System.out.println("📨 Notification WebSocket envoyée !");
    }
}

package org.example.babysitting.serviceImplement;

import org.example.babysitting.entities.Notification;
import org.example.babysitting.repository.NotificationRepo;
import org.example.babysitting.service.NotificationInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationImplement implements NotificationInterface {
    @Autowired
    NotificationRepo notificationRepo;

    @Override
    public Notification addNotification(Notification notification) {
        System.out.println("✅ Notification enregistrée en base pour user_idUser = " + notification.getUser_idUser());
        return notificationRepo.save(notification);
    }

    @Override
    public void deleteNotification(Long id) {
        if (notificationRepo.existsByIdNotif(id)) {
            notificationRepo.deleteById(id);
        } else {
            throw new IllegalArgumentException("Notification with id " + id + " does not exist.");
        }

    }

    @Override
    public void deleteAllNotifications(Long idUser) {
        List<Notification> notifications = notificationRepo.findByUser_idUser(idUser);
        if (!notifications.isEmpty()) {
            notificationRepo.deleteAll(notifications);
        } else {
            throw new IllegalArgumentException("No notifications found for user with id " + idUser);
        }

    }

    @Override
    public List<Notification> addListNotifications(List<Notification> notifications) {
        return notificationRepo.saveAll(notifications);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepo.findAll();
    }

    @Override
    public Notification getNotificationById(Long id) {
        return notificationRepo.findById(id)
                .orElse(null); // Return null if notification not found
    }

    @Override
    public List<Notification> getNotificationsByUser_idUser(Long idUser) {
        return notificationRepo.findByUser_idUser(idUser);
    }

    @Override
    public List<Notification> getUnreadNotificationsByUser_idUser(Long idUser) {
        return notificationRepo.findByUser_idUserAndIsRead(idUser, false);
    }

    @Override
    public void markNotificationAsRead(Long id) {
        Notification notification = notificationRepo.findById(id)
                .orElse(null); // Return null if notification not found
        notification.setRead(true);
        notificationRepo.save(notification);

    }

    @Override
    public void markAllNotificationsAsRead(Long idUser) {
        List<Notification> notifications = notificationRepo.findByUser_idUserAndIsRead(idUser, false);
        for (Notification notification : notifications) {
            notification.setRead(true);
        }
        notificationRepo.saveAll(notifications);

    }

    @Override
    public List<Notification> getNotificationsByDate(LocalDate date) {
        return notificationRepo.findByDate(date);
    }
}

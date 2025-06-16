package org.example.babysitting.controllers;

import org.example.babysitting.entities.Notification;
import org.example.babysitting.service.NotificationInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
     NotificationInterface notificationInterface;

    @PostMapping("/addNotification")
    public Notification addNotification(@RequestBody Notification notification) {
        return notificationInterface.addNotification(notification);
    }
    @PostMapping("/addListNotifications")
    public List<Notification> addListNotifications(@RequestBody List<Notification> notifications) {
        return notificationInterface.addListNotifications(notifications);
    }
    @DeleteMapping("/deleteNotification/{id}")
    public void deleteNotification(@PathVariable Long id) {
        notificationInterface.deleteNotification(id);
    }
    @DeleteMapping("/deleteAllNotifications/{idUser}")
    public void deleteAllNotifications(@PathVariable Long idUser) {
        notificationInterface.deleteAllNotifications(idUser);
    }
    @GetMapping("/getAllNotifications")
    public List<Notification> getAllNotifications() {
        return notificationInterface.getAllNotifications();
    }
    @GetMapping("/getNotificationById/{id}")
    public Notification getNotificationById(@PathVariable Long id) {
        return notificationInterface.getNotificationById(id);
    }
    @GetMapping("/getNotificationsByIdUser/{idUser}")
    public List<Notification> getNotificationsByIdUser(@PathVariable Long idUser) {
        return notificationInterface.getNotificationsByIdUser(idUser);
    }
    @GetMapping("/getUnreadNotificationsByIdUser/{idUser}")
    public List<Notification> getUnreadNotificationsByIdUser(@PathVariable Long idUser) {
        return notificationInterface.getUnreadNotificationsByIdUser(idUser);
    }
    @PutMapping("/markNotificationAsRead/{id}")
    public void markNotificationAsRead(@PathVariable Long id) {
        notificationInterface.markNotificationAsRead(id);
    }
    @PutMapping("/markAllNotificationsAsRead/{idUser}")
    public void markAllNotificationsAsRead(@PathVariable Long idUser) {
        notificationInterface.markAllNotificationsAsRead(idUser);
    }
    @GetMapping("/getNotificationsByDate/{date}")
    public List<Notification> getNotificationsByDate(@PathVariable String date) {
        return notificationInterface.getNotificationsByDate(java.sql.Date.valueOf(date));
    }



}

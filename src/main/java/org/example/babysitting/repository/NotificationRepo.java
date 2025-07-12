package org.example.babysitting.repository;

import org.example.babysitting.entities.Message;
import org.example.babysitting.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification, Long> {

    List<Notification> findByUser_idUser(Long user_idUser);
    boolean existsByIdNotif(Long idNotif);
    List<Notification> findByDate(LocalDate date);
    List<Notification> findByUser_idUserAndIsRead(Long idUser, boolean isRead);





}

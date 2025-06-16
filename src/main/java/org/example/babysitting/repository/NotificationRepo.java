package org.example.babysitting.repository;

import org.example.babysitting.entities.Message;
import org.example.babysitting.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification, Long> {

    List<Notification> findByIdUser(Long idUser);
    boolean existsByIdNotif(Long idNotif);
    List<Notification> findByDate(Date date);
    List<Notification> findByIdUserAndIsRead(Long idUser, boolean isRead);





}

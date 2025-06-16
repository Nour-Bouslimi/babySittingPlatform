package org.example.babysitting.repository;

import org.example.babysitting.entities.Message;
import org.example.babysitting.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {

    //Named methods
    List<Message> findByIdUser(Long idUser);
    List<Message> findByDate(Date date);
    boolean existsByIdMsg(Long idMsg);




}

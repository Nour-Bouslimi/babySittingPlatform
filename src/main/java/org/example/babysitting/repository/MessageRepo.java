package org.example.babysitting.repository;

import org.example.babysitting.entities.Message;
import org.example.babysitting.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {

    //Named methods
    //List<Message> findByIdUser(Long idUser);
    List<Message> findByDate(LocalDate date);
    boolean existsByIdMsg(Long idMsg);
    Message findByIdMsg(Long idMsg); //ajout de la méthode pour récupérer un message par son ID
    //récupérer les msg entre 2 utilisateurs
    @Query("SELECT m from Message m WHERE " +
            "(m.sender.idUser =:userId1 AND m.receiver.idUser =:userId2) OR " +
            "(m.sender.idUser =:userId2 AND m.receiver.idUser =:userId1) " +
            "ORDER BY m.date Asc"
    )
    List<Message> getConversation(Long userId1, Long userId2);



}

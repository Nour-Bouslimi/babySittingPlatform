package org.example.babysitting.repository;

import org.example.babysitting.entities.Message;
import org.example.babysitting.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {

    //Named methods
    //List<Message> findByIdUser(Long idUser);
    List<Message> findByDate(LocalDateTime date);
    boolean existsByIdMsg(Long idMsg);
    Message findByIdMsg(Long idMsg); //ajout de la méthode pour récupérer un message par son ID
    //récupérer les msg entre 2 utilisateurs
    @Query("SELECT m from Message m WHERE " +
            "(m.sender.idUser =:userId1 AND m.receiver.idUser =:userId2) OR " +
            "(m.sender.idUser =:userId2 AND m.receiver.idUser =:userId1) " +
            "ORDER BY m.date Asc"
    )
    List<Message> getConversation(Long userId1, Long userId2);
    @Query(value = """
    SELECT 
      CASE WHEN sender_id = :userId THEN receiver_id ELSE sender_id END AS otherUserId,
      m.content AS lastMessage,
      MAX(m.date) AS lastDate,
      u.firstName AS firstName,
      u.lastName AS lastName,
      u.photo AS photo
    FROM message m
    JOIN user u ON u.idUser = CASE WHEN m.sender_id = :userId THEN m.receiver_id ELSE m.sender_id END
    WHERE m.sender_id = :userId OR m.receiver_id = :userId
    GROUP BY otherUserId, m.content, u.firstName, u.lastName, u.photo
    ORDER BY lastDate DESC
    """, nativeQuery = true)
    List<Object[]> findAllConversationsRaw(@Param("userId") Long userId);


}

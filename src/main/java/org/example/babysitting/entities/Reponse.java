package org.example.babysitting.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Data
public class Reponse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idReponse;
    @Column(name = "content", nullable = false, length = 500)
    @JsonProperty("content")
    private String content;


    @CreationTimestamp
    @Column(name = "date", nullable = false, length = 30)
    @JsonProperty("date")
    private LocalDate date; // Date of the response, can be formatted as needed

    // association with User
    @ManyToOne
    @JoinColumn(name="sender_id", nullable = false)
    private User sender;
    @ManyToOne
    @JoinColumn(name="receiver_id", nullable = false)
    private User receiver;
    // association with Message
    @ManyToOne
    private Message message;

    public Reponse() {
    }
    @PrePersist
    @PreUpdate
    public void setSystemDate() {
        //this.date = new Date(System.currentTimeMillis());
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Long getIdReponse() {
        return idReponse;
    }

    public void setIdReponse(Long idReponse) {
        this.idReponse = idReponse;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public User getSender() {
        return sender;
    }
    public void setSender(User sender) {
        this.sender = sender;
    }
    public Long getSender_idUser() {
        return sender.getIdUser();
    }
    public void setSender_idUser(Long sender_idUser) {
        if (this.sender == null) {
            this.sender = new User();
        }
        this.sender.setIdUser(sender_idUser);
    }



    public Message getMsg() {
        return message;
    }
    public void setMsg(Message message) {
        this.message = message;
    }
    public Message getMessage_idMsg() {
        return message;
    }
    public void setMessage_idMsg(Message message) {
        if (this.message == null) {
            this.message = new Message();
        }
        this.message.setIdMsg(message.getIdMsg());
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

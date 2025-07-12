package org.example.babysitting.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data

public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idMsg;

    @Column(name = "content", nullable = false, length = 500)
    @JsonProperty("content")
    private String content;

    @CreationTimestamp
    @Column(name = "date", nullable = false, length = 30)
    @JsonProperty("date")
    private LocalDate date;


    // Association avec User
    @ManyToOne
    @JoinColumn(name="sender_id", nullable = false)
    private User sender;
    @ManyToOne
    @JoinColumn(name="receiver_id", nullable = false)
    private User receiver;
    // Association avec Reponse
    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reponse> reponses;


    public Message() {
    }
    @PrePersist
    @PreUpdate
    public void setSystemDate() {
        //this.date = new Date(System.currentTimeMillis());
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public void setReponses(List<Reponse> reponses) {
        this.reponses = reponses;
    }

    public User getReceiver() {
        return receiver;
    }

    public List<Reponse> getReponses() {
        return reponses;
    }

    public Long getIdMsg() {
        return idMsg;
    }

    public void setIdMsg(Long idMsg) {
        this.idMsg = idMsg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}

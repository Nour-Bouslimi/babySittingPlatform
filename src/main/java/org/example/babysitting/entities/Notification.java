package org.example.babysitting.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idNotif;
    @Column(name = "message", nullable = false, length = 500)
    @JsonProperty("message")
    private String message;
    @Column(name = "isRead", nullable = false,columnDefinition = "boolean default false")
    @JsonProperty("isRead")
    private boolean isRead ;
    @CreationTimestamp
    @Column(name = "date", nullable = false, length = 30)
    @JsonProperty("date")
    private Date date; // Date of the notification, automatically set to current date
    @Column(name = "idUser", nullable = false)
    @JsonProperty("idUser")
    private long idUser; // ID of the user who receives the notification

    public Notification() {
    }
    @PrePersist
    @PreUpdate
    public void setSystemDate() {
        this.date = new Date(System.currentTimeMillis());
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public long getIdNotif() {
        return idNotif;
    }

    public void setIdNotif(long idNotif) {
        this.idNotif = idNotif;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }
    public boolean isRead() {
        return isRead;
    }
    public void setRead(boolean read) {
        isRead = read;
    }
}

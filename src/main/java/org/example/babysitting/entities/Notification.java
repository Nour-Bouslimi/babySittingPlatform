package org.example.babysitting.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idNotif;
    @Column(name = "message", nullable = false, length = 500)
    @JsonProperty("message")
    private String message;
    @Column(name = "isRead", nullable = false,columnDefinition = "boolean default false")
    @JsonProperty("isRead")
    private boolean isRead ;
    @CreationTimestamp
    @Column(name = "date", nullable = false, length = 30)
    @JsonProperty("date")
    private LocalDate date; // Date of the notification, automatically set to current date
    /*@Column(name = "idUser", nullable = false)
    @JsonProperty("idUser")
    private Long idUser; // ID of the user who receives the notification
*/
    // Association with User
    @ManyToOne
    private User user;

    public Notification() {

    }
    @PrePersist
    @PreUpdate
    public void setSystemDate() {
        //this.date = new Date(System.currentTimeMillis());
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
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

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Long getUser_idUser() {
        return user != null ? user.getIdUser() : null;
    }
    public void setUser_idUser(Long user_idUser) {
        if (this.user == null) {
            this.user = new User();
        }
        this.user.setIdUser(user_idUser);
    }

    public boolean isRead() {
        return isRead;
    }
    public void setRead(boolean read) {
        isRead = read;
    }
}

package org.example.babysitting.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Data
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idAnnonce;
    @Column(name = "titre", nullable = false, length = 100)
    @JsonProperty("titre")
    private String titre;
    @Column(name = "description", nullable = false, length = 500)
    @JsonProperty("description")
    private String description;
    @CreationTimestamp
    @Column(name = "date", nullable = false, length = 30)
    @JsonProperty("date")
    private LocalDate date;

    /*@Column(name = "idUser", nullable = false)
    @JsonProperty("idUser")
    private Long idUser; // ID of the user who created the announcement
    */
    // Association with User

    @ManyToOne
    @JoinColumn(name = "user_idUser")
    @JsonIgnore
    private User user;


    public Annonce() {
    }
    @PrePersist
    @PreUpdate
    public void setSystemDate() {
        //this.date = new Date(System.currentTimeMillis());
    }

    public long getIdAnnonce() {
        return idAnnonce;
    }

    public void setIdAnnonce(long idAnnonce) {
        this.idAnnonce = idAnnonce;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}

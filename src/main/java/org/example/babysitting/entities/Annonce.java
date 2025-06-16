package org.example.babysitting.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Entity
@Data
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idAnnonce;
    @Column(name = "titre", nullable = false, length = 100)
    @JsonProperty("titre")
    private String titre;
    @Column(name = "description", nullable = false, length = 500)
    @JsonProperty("description")
    private String description;
    @CreationTimestamp
    @Column(name = "date", nullable = false, length = 30)
    @JsonProperty("date")
    private Date date;
    @Column(name = "img")
    @JsonProperty("img")
    private String img;
    @Column(name = "idUser", nullable = false)
    @JsonProperty("idUser")
    private long idUser; // ID of the user who created the announcement
    public Annonce() {
    }
    @PrePersist
    @PreUpdate
    public void setSystemDate() {
        this.date = new Date(System.currentTimeMillis());
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }
}

package org.example.babysitting.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;


@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idReserv;
    //@CreationTimestamp
    @Column(name = "date", nullable = false, length = 30)
    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name = "heureDebut", nullable = false)
    @JsonProperty("heureDebut")
    private int heureDebut;
    @Column(name = "heureFin", nullable = false)
    @JsonProperty("heureFin")
    private int heureFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false,columnDefinition = "VARCHAR(10) DEFAULT 'PENDING'")
    @JsonProperty("statut")
    @NotNull(message = "Statut cannot be null")
    private Statut statut;

    /*@Column(name = "idUser", nullable = false)
    @JsonProperty("idUser")
    private Long idUser;*/

    //association avec User
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="parent", nullable = false)
    private User parent;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="nounou", nullable = false)
    private User nounou;

    public Reservation() {
        this.statut = Statut.PENDING;

    }

    public void setIdReserv(Long idReserv) {
        this.idReserv = idReserv;
    }

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }

    public User getNounou() {
        return nounou;
    }

    public void setNounou(User nounou) {
        this.nounou = nounou;
    }



    public long getIdReserv() {
        return idReserv;
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(int heureDebut) {
        this.heureDebut = heureDebut;
    }

    public int getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(int heureFin) {
        this.heureFin = heureFin;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }


    public Long getParent_idUser() {
        if (parent != null) {
            return parent.getIdUser();
        } else {
            return null;
        }
    }
    public void setParent_idUser(Long user_idUser) {
        if (parent == null) {
            parent = new User();
        }
        parent.setIdUser(user_idUser);
    }
    public Long getNounou_idUser() {
        if (nounou != null) {
            return nounou.getIdUser();
        } else {
            return null;
        }
    }
    public void setNounou_idUser(Long user_idUser) {
        if (nounou == null) {
            nounou = new User();
        }
        nounou.setIdUser(user_idUser);
    }
}

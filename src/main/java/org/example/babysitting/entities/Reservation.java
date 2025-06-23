package org.example.babysitting.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idReserv;
    @CreationTimestamp
    @Column(name = "date", nullable = false, length = 30)
    @JsonProperty("date")
    private LocalDate date;

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

    @Column(name = "idUser", nullable = false)
    @JsonProperty("idUser")
    private Long idUser;

    //association avec User
    @ManyToOne
    private User user;

    public Reservation() {
        this.statut = Statut.PENDING;

    }

    public Reservation(long idReserv, LocalDate date, int heureDebut, int heureFin, Statut statut, long idUser) {
        this.idReserv = idReserv;
        this.date = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.statut = statut;
        this.idUser = idUser;
    }

    public long getIdReserv() {
        return idReserv;
    }

    public void setIdReserv(long idReserv) {
        this.idReserv = idReserv;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }


}

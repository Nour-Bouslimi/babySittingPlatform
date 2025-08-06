package org.example.babysitting.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.Data;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Data
public class Disponibilite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long idDispo;
    @Column(name = "date", nullable = false, length = 30)
    @JsonProperty("date")
    private LocalDate date;
    @Column(name = "heureDebut", nullable = false)
    @JsonProperty("heureDebut")
    private int heureDebut;
    @Column(name = "heureFin", nullable = false)
    @JsonProperty("heureFin")
    private int heureFin;


    // Association avec l'entité User
    @ManyToOne
    @JsonIgnore
    private User user; // Association avec l'entité User

    public Disponibilite() {
    }

    public long getIdDispo() {
        return idDispo;
    }

    public void setIdDispo(long idDispo) {
        this.idDispo = idDispo;
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

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Long getUser_idUser() {
        return user != null ? user.getIdUser() : null; // Retourne l'ID de l'utilisateur ou null si l'utilisateur est null
    }
    public void setUser_idUser(Long user_idUser) {
        if (this.user == null) {
            this.user = new User(); // Crée un nouvel objet User si user est null
        }
        this.user.setIdUser(user_idUser); // Définit l'ID de l'utilisateur
    }

    //controle de saisie sur la date, heureDebut et heureFin
    //@AssertTrue(message = "The date must be greater than the current date or comply with the time constraints.")



  /*  public boolean isDateAndTimeValid() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        LocalDate inputDate = date; // Pas besoin de conversion, car date est déjà un LocalDate
        LocalTime inputStartTime = LocalTime.of(heureDebut, 0);
        LocalTime inputEndTime = LocalTime.of(heureFin, 0);

        if (inputDate.isAfter(currentDate)) {
            return true;
        } else if (inputDate.isEqual(currentDate)) {
            if (heureDebut < heureFin) {
                return inputStartTime.isAfter(currentTime) &&
                        inputEndTime.isAfter(currentTime) &&
                        inputStartTime.isBefore(inputEndTime);
            } else {
                return inputStartTime.isAfter(currentTime) ||
                        inputEndTime.isAfter(currentTime);
            }
        }
        return false;
    }*/

}

package org.example.babysitting.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class Disponibilite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long idDispo;
    @Column(name = "date", nullable = false, length = 30)
    @JsonProperty("date")
    private Date date;
    @Column(name = "heureDebut", nullable = false)
    @JsonProperty("heureDebut")
    private int heureDebut;
    @Column(name = "heureFin", nullable = false)
    @JsonProperty("heureFin")
    private int heureFin;
    @Column(name = "idUser", nullable = false)
    @JsonProperty("idUser")
    private long idUser;

    public Disponibilite() {
    }

    public long getIdDispo() {
        return idDispo;
    }

    public void setIdDispo(long idDispo) {
        this.idDispo = idDispo;
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

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    //controle de saisie sur la date, heureDebut et heureFin
    @AssertTrue(message = "The date must be greater than the current date or comply with the time constraints.")


    public boolean isDateAndTimeValid() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        LocalDate inputDate = date.toLocalDate();
        LocalTime inputStartTime = LocalTime.of(heureDebut, 0);
        LocalTime inputEndTime = LocalTime.of(heureFin, 0);

        // Vérification de la date
        if (inputDate.isAfter(currentDate)) {
            return true; // La date est valide si elle est après la date actuelle
        } else if (inputDate.isEqual(currentDate)) {
            // Vérification des heures
            if (heureDebut < heureFin) {
                // Plage horaire normale
                return inputStartTime.isAfter(currentTime) &&
                        inputEndTime.isAfter(currentTime) &&
                        inputStartTime.isBefore(inputEndTime);
            } else {
                // Plage horaire traversant minuit
                return inputStartTime.isAfter(currentTime) ||
                        inputEndTime.isAfter(currentTime);
            }
        }
        return false; // La date est invalide si elle est avant la date actuelle
    }
}

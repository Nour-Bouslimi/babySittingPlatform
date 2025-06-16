package org.example.babysitting.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Entity
@Data
public class Reponse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idReponse;
    @Column(name = "content", nullable = false, length = 500)
    @JsonProperty("content")
    private String content;
    @Column(name = "idMsg", nullable = false)
@JsonProperty("idMsg")
    private Long idMsg; // ID of the message this response is associated with
    @Column(name = "idUser", nullable = false)
    @JsonProperty("idUser")
    private Long idUser; // ID of the user who made the response
    @CreationTimestamp
    @Column(name = "date", nullable = false, length = 30)
    @JsonProperty("date")
    private Date date; // Date of the response, can be formatted as needed

    public Reponse() {
    }
    @PrePersist
    @PreUpdate
    public void setSystemDate() {
        this.date = new Date(System.currentTimeMillis());
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

    public Long getIdMsg() {
        return idMsg;
    }

    public void setIdMsg(Long idMsg) {
        this.idMsg = idMsg;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

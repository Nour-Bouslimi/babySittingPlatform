package org.example.babysitting.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

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
    private Date date;

    @Column(name = "idUser", nullable = false)
    @JsonProperty("idUser")
    private long idUser;

    public Message() {
    }
    @PrePersist
    @PreUpdate
    public void setSystemDate() {
        this.date = new Date(System.currentTimeMillis());
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }
}

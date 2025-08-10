package org.example.babysitting.DTO;

import java.time.LocalDateTime;

public class ConversationDTO {
    private Long otherUserId;
    private String lastMessage;
    private LocalDateTime date;
    private String firstName;
    private String lastName;
    private String photo;

    // constructeur complet
    public ConversationDTO(Long otherUserId, String lastMessage, LocalDateTime date, String firstName, String lastName, String photo) {
        this.otherUserId = otherUserId;
        this.lastMessage = lastMessage;
        this.date = date;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
    }

    public Long getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(Long otherUserId) {
        this.otherUserId = otherUserId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public String getNom() {
        return (firstName == null ? "" : firstName) + " " + (lastName == null ? "" : lastName);
    }
}

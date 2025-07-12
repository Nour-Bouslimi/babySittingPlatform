package org.example.babysitting.DTO;

import java.time.LocalDate;

public class ResponseRequest {
    private String content;
    private Long senderId;
    private Long receiverId;
    private Long messageId;
    private LocalDate date;
    public ResponseRequest() {
    }
    public ResponseRequest(String content, Long senderId, Long messageId, LocalDate date, Long receiverId) {
        this.content = content;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageId = messageId;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSenderId() {
        return senderId;
    }
    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }
    public Long getReceiverId() {
        return receiverId;
    }
    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

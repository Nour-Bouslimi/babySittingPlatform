package org.example.babysitting.controllers;

import org.example.babysitting.entities.Message;
import org.example.babysitting.entities.Reservation;
import org.example.babysitting.service.MessageInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
     MessageInterface messageInterface;
    // Define endpoints for message operations here
    @PostMapping("/addMessage")
    public Message addMessage(@RequestBody Message message) {
       return messageInterface.addMessage(message);
    }

    @PostMapping("/addListMessages")
    public List<Message> addListMessages(@RequestBody List<Message> messages) {
        return messageInterface.addListMessages(messages);
    }

    @DeleteMapping("/deleteMessage/{id}")
    public void deleteMessage(@PathVariable long id) {
        messageInterface.deleteMessage(id);
    }



    @PutMapping("/updateMessage/{id}")
    public Message updateMessage(@PathVariable long id, @RequestBody Message message) {
        return messageInterface.updateMessage(id, message);
    }
    @GetMapping("/getAllMessages")
    public List<Message> getAllMessages() {
        return messageInterface.getAllMessages();
    }
    @GetMapping("/getMessageById/{id}")
    public Message getMessageById(@PathVariable long id) {
        return messageInterface.getMessageById(id);
    }
    @GetMapping("/getMessagesByIdUser/{idUser}")
    public List<Message> getMessagesByIdUser(@PathVariable long idUser) {
        return messageInterface.getMessagesByIdUSer(idUser);
    }
    @GetMapping("/getMessagesByDate/{date}")
    public List<Message> getMessagesByDate(@PathVariable String date) {
        return messageInterface.getMessagesByDate(java.sql.Date.valueOf(date));
    }



}

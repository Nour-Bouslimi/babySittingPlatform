package org.example.babysitting.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.babysitting.DTO.MessageRequest;
import org.example.babysitting.entities.Message;
import org.example.babysitting.entities.Reservation;
import org.example.babysitting.entities.User;
import org.example.babysitting.repository.UserRepo;
import org.example.babysitting.service.MessageInterface;
import org.example.babysitting.service.UserInterface;
import org.example.babysitting.serviceImplement.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/messages")
@CrossOrigin(origins= "*",allowedHeaders = "*") // Allows all origins, you can specify a specific origin if needed
public class MessageController {
    @Autowired
     MessageInterface messageInterface;

    @Autowired
    UserInterface userInterface;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private NotificationService notificationService;

    // Define endpoints for message operations here
    @PostMapping("/addMessage")
    public Message addMessage(@RequestBody Message message) {
       return messageInterface.addMessage(message);
    }

    // methode send message
    @PostMapping("/sendMessage")
    public ResponseEntity<Message> sendMessage(@RequestBody MessageRequest messageRequest) {
        if (messageRequest.getSenderId() == null || messageRequest.getReceiverId() == null) {
            throw new IllegalArgumentException("Les IDs de l'expéditeur et du destinataire ne doivent pas être null.");
        }
        User sender = userInterface.getUserById(messageRequest.getSenderId());
        User receiver = userInterface.getUserById(messageRequest.getReceiverId());
        if (sender == null || receiver == null) {
            throw new RuntimeException("Expéditeur ou destinataire introuvable.");
        }
        Message message = messageInterface.sendMessage(sender.getIdUser(),
                receiver.getIdUser(),
                messageRequest.getContent());
        notificationService.sendNotification(receiver.getIdUser(),
                "Vous avez reçu un nouveau message de " + sender.getFirstName() + " " + sender.getLastName());
        return ResponseEntity.ok(message);
    }
    // methode get conversation
    @GetMapping("/getConversation/{userId1}/{userId2}")
    public ResponseEntity<List<Message>> getConversation(@PathVariable Long userId1, @PathVariable Long userId2) {
        List<Message> conversation = messageInterface.getConversation(userId1, userId2);
        return ResponseEntity.ok(conversation);
    }

    @PostMapping("/addListMessages")
    public List<Message> addListMessages(@RequestBody List<Message> messages) {
        return messageInterface.addListMessages(messages);
    }

    @DeleteMapping("/deleteMessage/{id}")
    public void deleteMessage(@PathVariable Long id) {
        messageInterface.deleteMessage(id);
    }



    @PutMapping("/updateMessage/{id}")
    public Message updateMessage(@PathVariable Long id, @RequestBody Message message) {
        return messageInterface.updateMessage(id, message);
    }
    @GetMapping("/getAllMessages")
    public List<Message> getAllMessages() {
        return messageInterface.getAllMessages();
    }
    @GetMapping("/getMessageById/{id}")
    public Message getMessageById(@PathVariable Long id) {
        return messageInterface.getMessageById(id);
    }
    /*@GetMapping("/getMessagesByIdUser/{idUser}")
    public List<Message> getMessagesByIdUser(@PathVariable Long idUser) {
        return messageInterface.getMessagesByIdUSer(idUser);
    }*/
    @GetMapping("/getMessagesByDate/{date}")
    public List<Message> getMessagesByDate(@PathVariable String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date); // Conversion de la chaîne en LocalDate
            return messageInterface.getMessagesByDate(parsedDate);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Format de date invalide. Utilisez 'yyyy-MM-dd'.");
        }

    }

    //chat
   /* @MessageMapping("/chat.sendMessage") //   /app/chat.sendMessage
    @SendTo("/topic/public")
    public Message sendMessage(Message message) throws JsonProcessingException {
        System.out.println("Message reçu : " + new ObjectMapper().writeValueAsString(message));
        if (message.getSender() == null || message.getSender().getIdUser() == null ||
                message.getReceiver() == null || message.getReceiver().getIdUser() == null) {
            throw new IllegalArgumentException("Sender and receiver IDs must not be null.");
        }

        Long senderId = message.getSender().getIdUser();
        System.out.println("Sender ID: " + senderId);
        Long receiverId = message.getReceiver().getIdUser();
        System.out.println("Receiver ID: " + receiverId);
        User sender = userInterface.getUserById(senderId);
        User receiver = userInterface.getUserById(receiverId);
        System.out.println("Sender: " + sender);
        System.out.println("Receiver: " + receiver);
        if(sender == null || receiver == null) {
            System.out.println("Sender or receiver not found");
            throw new RuntimeException("Sender or receiver not found");
        }
        message.setDate(LocalDate.now());
        message.setSender(sender);
        message.setReceiver(receiver);
        messageInterface.addMessage(message);
        System.out.println("Message à retourner : " + new ObjectMapper().writeValueAsString(message));

        return message;
    }*/

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload Message message) {
        System.out.println("Sending from: " + message.getSender().getIdUser());
        System.out.println("Sending to: " + message.getReceiver().getIdUser());
        System.out.println("Message content: " + message.getContent());
        if (message.getSender() == null || message.getReceiver() == null) {
            throw new IllegalArgumentException("Sender and receiver must not be null.");
        }
        String receiverId = String.valueOf(message.getReceiver().getIdUser().toString());
        System.out.println("convertAndSendToUser: " + receiverId.toString());
        messagingTemplate.convertAndSendToUser(
                receiverId.toString(), "/queue/messages", message
        );

    }


}

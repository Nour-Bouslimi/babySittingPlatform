package org.example.babysitting.controllers;

import org.example.babysitting.DTO.MessageRequest;
import org.example.babysitting.DTO.ResponseRequest;
import org.example.babysitting.entities.Message;
import org.example.babysitting.entities.Reponse;
import org.example.babysitting.entities.User;
import org.example.babysitting.service.ReponseInterface;
import org.example.babysitting.service.UserInterface;
import org.example.babysitting.serviceImplement.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/reponses")
@CrossOrigin(origins= "*",allowedHeaders = "*") // Allows all origins, you can specify a specific origin if needed
public class ReponseController {
    @Autowired
     ReponseInterface reponseInterface;
    @Autowired
    UserInterface userInterface;
    @Autowired
    private NotificationService notificationService;
    @PostMapping("/addReponse")
    public Reponse addReponse(@RequestBody Reponse reponse){
        return reponseInterface.addReponse(reponse);

    }

    // methode send message
    @PostMapping("/sendMessageAsResponse")
    public ResponseEntity<Reponse> sendMessageAsResponse(@RequestBody ResponseRequest responseRequest) {
        if (responseRequest.getSenderId() == null || responseRequest.getReceiverId()==null || responseRequest.getMessageId() == null) {
            throw new IllegalArgumentException("Les IDs des utilisateurs et l'ID du message ne doivent pas être null.");

        }
        User sender = userInterface.getUserById(responseRequest.getSenderId());
        User receiver = userInterface.getUserById(responseRequest.getReceiverId());

        if (sender == null || receiver == null) {
            throw new RuntimeException("Expéditeur ou destinataire introuvable.");

        }
        Reponse reponse = reponseInterface.sendMessageAsResponse(sender.getIdUser(),
                receiver.getIdUser(),
                responseRequest.getMessageId(),
                responseRequest.getContent());
        notificationService.sendNotification(receiver.getIdUser(),
                "You received a response from " + sender.getFirstName() + " " + sender.getLastName());
        return ResponseEntity.ok(reponse);
    }

    @PostMapping("/addListReponses")
    public List<Reponse> addListReponses(@RequestBody List<Reponse> reponses) {
        return reponseInterface.addListReponses(reponses);
    }
    @PutMapping("/updateReponse/{id}")
    public Reponse updateReponse(@PathVariable Long id, @RequestBody Reponse reponse) {
        return reponseInterface.updateReponse(id, reponse);
    }
    @DeleteMapping("/deleteReponse/{id}")
    public void deleteReponse(@PathVariable Long id) {
        reponseInterface.deleteReponse(id);
    }
    @GetMapping("/getAllReponses")
    public List<Reponse> getAllReponses() {
        return reponseInterface.getAllReponses();
    }
    @GetMapping("/getReponseById/{id}")
    public Reponse getReponseById(@PathVariable Long id) {
        return reponseInterface.getReponseById(id);
    }
    @GetMapping("/getReponsesByIdMsg/{idMsg}")
    public List<Reponse> getReponsesByIdMsg(@PathVariable Long idMsg) {
        return reponseInterface.getReponsesByMessage_idMsg(idMsg);
    }
    @GetMapping("/getReponsesByIdUser/{idUser}")
    public List<Reponse> getReponsesByIdUser(@PathVariable Long idUser) {
        return reponseInterface.getReponsesBySender_idUser(idUser);
    }
    @GetMapping("/getReponsesByIdMsgAndIdUser/{idMsg}/{idUser}")
    public List<Reponse> getReponsesByIdMsgAndIdUser(@PathVariable Long idMsg, @PathVariable Long idUser) {
        return reponseInterface.getReponsesByMessage_idMsgAndSender_idUser(idMsg, idUser);
    }
    @GetMapping("/getReponsesByIdMsgAndDate/{idMsg}/{date}")
    public List<Reponse> getReponsesByIdMsgAndDate(@PathVariable Long idMsg, @PathVariable String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date); // Conversion de la chaîne en LocalDate
            return reponseInterface.getReponsesByMessage_idMsgAndDate(idMsg, parsedDate);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Format de date invalide. Utilisez 'yyyy-MM-dd'.");
        }

    }
    @GetMapping("/getReponsesByIdUserAndDate/{idUser}/{date}")
    public List<Reponse> getReponsesByIdUserAndDate(@PathVariable Long idUser, @PathVariable String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date); // Conversion de la chaîne en LocalDate
            return reponseInterface.getReponsesBySender_idUserAndDate(idUser, parsedDate);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Format de date invalide. Utilisez 'yyyy-MM-dd'.");
        }

    }
    @GetMapping("/getReponsesByDate/{date}")
    public List<Reponse> getReponsesByDate(@PathVariable String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date); // Conversion de la chaîne en LocalDate
            return reponseInterface.getReponsesByDate(parsedDate);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Format de date invalide. Utilisez 'yyyy-MM-dd'.");
        }

    }

}

package org.example.babysitting.controllers;

import org.example.babysitting.entities.Reponse;
import org.example.babysitting.service.ReponseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reponses")
public class ReponseController {
    @Autowired
     ReponseInterface reponseInterface;
    @PostMapping("/addReponse")
    public Reponse addReponse(@RequestBody Reponse reponse){
        return reponseInterface.addReponse(reponse);

    }

    @PostMapping("/addListReponses")
    public List<Reponse> addListReponses(@RequestBody List<Reponse> reponses) {
        return reponseInterface.addListReponses(reponses);
    }
    @PutMapping("/updateReponse/{id}")
    public Reponse updateReponse(@PathVariable long id, @RequestBody Reponse reponse) {
        return reponseInterface.updateReponse(id, reponse);
    }
    @DeleteMapping("/deleteReponse/{id}")
    public void deleteReponse(@PathVariable long id) {
        reponseInterface.deleteReponse(id);
    }
    @GetMapping("/getAllReponses")
    public List<Reponse> getAllReponses() {
        return reponseInterface.getAllReponses();
    }
    @GetMapping("/getReponseById/{id}")
    public Reponse getReponseById(@PathVariable long id) {
        return reponseInterface.getReponseById(id);
    }
    @GetMapping("/getReponsesByIdMsg/{idMsg}")
    public List<Reponse> getReponsesByIdMsg(@PathVariable long idMsg) {
        return reponseInterface.getReponsesByIdMsg(idMsg);
    }
    @GetMapping("/getReponsesByIdUser/{idUser}")
    public List<Reponse> getReponsesByIdUser(@PathVariable long idUser) {
        return reponseInterface.getReponsesByIdUser(idUser);
    }
    @GetMapping("/getReponsesByIdMsgAndIdUser/{idMsg}/{idUser}")
    public List<Reponse> getReponsesByIdMsgAndIdUser(@PathVariable long idMsg, @PathVariable long idUser) {
        return reponseInterface.getReponsesByIdMsgAndIdUser(idMsg, idUser);
    }
    @GetMapping("/getReponsesByIdMsgAndDate/{idMsg}/{date}")
    public List<Reponse> getReponsesByIdMsgAndDate(@PathVariable long idMsg, @PathVariable String date) {
        return reponseInterface.getReponsesByIdMsgAndDate(idMsg, java.sql.Date.valueOf(date));
    }
    @GetMapping("/getReponsesByIdUserAndDate/{idUser}/{date}")
    public List<Reponse> getReponsesByIdUserAndDate(@PathVariable long idUser, @PathVariable String date) {
        return reponseInterface.getReponsesByIdUserAndDate(idUser, java.sql.Date.valueOf(date));
    }
    @GetMapping("/getReponsesByDate/{date}")
    public List<Reponse> getReponsesByDate(@PathVariable String date) {
        return reponseInterface.getReponsesByDate(java.sql.Date.valueOf(date));
    }

}

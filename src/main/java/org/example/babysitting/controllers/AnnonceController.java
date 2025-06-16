package org.example.babysitting.controllers;

import org.example.babysitting.entities.Annonce;
import org.example.babysitting.service.AnnonceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/annonces")
public class AnnonceController {
    @Autowired
    AnnonceInterface annonceInterface;

    @PostMapping("/addAnnonce")
    public Annonce addAnnonce(@RequestBody Annonce annonce){
        return annonceInterface.addAnnonce(annonce);

    }
    @PostMapping("/addListAnnonces")
    public List<Annonce> addListAnnonces(@RequestBody List<Annonce> annonces) {
        return annonceInterface.addListAnnonces(annonces);
    }
    @PutMapping("/updateAnnonce/{id}")
    public Annonce updateAnnonce(@PathVariable long id, @RequestBody Annonce annonce) {
        return annonceInterface.updateAnnonce(id, annonce);
    }
    @DeleteMapping("/deleteAnnonce/{id}")
    public void deleteAnnonce(@PathVariable long id) {
        annonceInterface.deleteAnnonce(id);
    }
    @GetMapping("/getAllAnnonces")
    public List<Annonce> getAllAnnonces() {
        return annonceInterface.getAllAnnonces();
    }
    @GetMapping("/getAnnonceById/{id}")
    public Annonce getAnnonceById(@PathVariable long id) {
        return annonceInterface.getAnnonceById(id);
    }
    @GetMapping("/getAnnoncesByIdUser/{idUser}")
    public List<Annonce> getAnnoncesByIdUser(@PathVariable long idUser) {
        return annonceInterface.getAnnoncesByIdUser(idUser);
    }
    @GetMapping("/getAnnoncesByDate/{date}")
    public List<Annonce> getAnnoncesByDate(@PathVariable String date) {
        return annonceInterface.getAnnoncesByDate(java.sql.Date.valueOf(date));
    }
    @GetMapping("/getAnnoncesByTitreContaining/{titre}")
    public List<Annonce> getAnnoncesByTitreContaining(@PathVariable String titre) {
        return annonceInterface.getAnnoncesByTitreContaining(titre);
    }
    @GetMapping("/getAnnoncesByDescriptionContaining/{description}")
    public List<Annonce> getAnnoncesByDescriptionContaining(@PathVariable String description) {
        return annonceInterface.getAnnoncesByDescriptionContaining(description);
    }


}

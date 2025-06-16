package org.example.babysitting.service;

import org.example.babysitting.entities.Annonce;
import org.example.babysitting.entities.Message;

import java.sql.Date;
import java.util.List;

public interface AnnonceInterface {

    Annonce addAnnonce(Annonce annonce);
    void deleteAnnonce(Long id);
    List<Annonce> addListAnnonces(List<Annonce> annonces); //ajout de la méthode pour ajouter une liste d'annonces
    Annonce updateAnnonce(long id, Annonce annonce); //ajout de la méthode pour mettre à jour une annonce
    List<Annonce> getAllAnnonces(); //ajout de la méthode pour récupérer toutes les annonces
    Annonce getAnnonceById(Long id); //ajout de la méthode pour récupérer une annonce par son ID
    List<Annonce> getAnnoncesByIdUser(Long idUser); //ajout de la méthode pour récupérer les annonces par l'ID de l'utilisateur
    List<Annonce> getAnnoncesByDate(Date date); //ajout de la méthode pour récupérer les annonces par date
    List<Annonce> getAnnoncesByTitreContaining(String titre); // Method to find annonces by title containing a specific string
    List<Annonce> getAnnoncesByDescriptionContaining(String description); // Method to find annonces by description containing a specific string



}

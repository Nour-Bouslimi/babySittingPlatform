package org.example.babysitting.service;

import org.example.babysitting.entities.Disponibilite;
import org.example.babysitting.entities.User;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface DisponibilityInterface {

    Disponibilite addDisponibilite(Disponibilite disponibilite);
    void deleteDisponibilite(Long id);
    List<Disponibilite> addListDisponibilites(List<Disponibilite> disponibilites); //ajout de la méthode pour ajouter une liste de disponibilités
    Disponibilite updateDisponibilite(Long id, Disponibilite disponibilite); //ajout de la méthode pour mettre à jour une disponibilité
    List<Disponibilite> getAllDisponibilites(); //ajout de la méthode pour récupérer toutes les disponibilités
    Disponibilite getDisponibiliteById(Long id); //ajout de la méthode pour récupérer une disponibilité par son ID
    List<Disponibilite> getDisponibiliteByUser_idUser(Long IdUser); //ajout de la méthode pour récupérer les disponibilités par l'ID de l'utilisateur
    List<Disponibilite> getDisponibiliteByDate(LocalDate date); //ajout de la méthode pour récupérer les disponibilités par date
    List<Disponibilite> getDisponibiliteByTimeRange(LocalDate date,int heureDebut, int heureFin); //ajout de la méthode pour récupérer les disponibilités par plage horaire
    List<Disponibilite> getDisponibiliteByUser_idUserAndDate(Long idUser, LocalDate date); //ajout de la méthode pour récupérer les disponibilités par l'ID de l'utilisateur et la date




}

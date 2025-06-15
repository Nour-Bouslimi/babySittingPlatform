package org.example.babysitting.service;

import org.example.babysitting.entities.Disponibilite;
import org.example.babysitting.entities.User;

import java.sql.Date;
import java.util.List;

public interface DisponibilityInterface {

    Disponibilite addDisponibilite(Disponibilite disponibilite);
    void deleteDisponibilite(Long id);
    List<Disponibilite> addListDisponibilites(List<Disponibilite> disponibilites); //ajout de la méthode pour ajouter une liste de disponibilités
    Disponibilite updateDisponibilite(long id, Disponibilite disponibilite); //ajout de la méthode pour mettre à jour une disponibilité
    List<Disponibilite> getAllDisponibilites(); //ajout de la méthode pour récupérer toutes les disponibilités
    Disponibilite getDisponibiliteById(Long id); //ajout de la méthode pour récupérer une disponibilité par son ID
    List<Disponibilite> getDisponibiliteByUserId(Long userId); //ajout de la méthode pour récupérer les disponibilités par l'ID de l'utilisateur
    List<Disponibilite> getDisponibiliteByDate(Date date); //ajout de la méthode pour récupérer les disponibilités par date
    List<Disponibilite> getDisponibiliteByTimeRange(int heureDebut, int heureFin); //ajout de la méthode pour récupérer les disponibilités par plage horaire





}

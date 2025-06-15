package org.example.babysitting.service;

import org.example.babysitting.entities.User;

import java.util.List;

public interface UserInterface {
    User addUser(User user);
    void deleteUser(Long id);
    List<User> addListUsers(List<User> users); //ajout de la méthode pour ajouter une liste d'utilisateurs

    //verifier email avant ajout
    String addUserWEmail(User user); //ajout de la méthode pour ajouter un utilisateur avec vérification de l'email
    User updateUser(long id, User user); //ajout de la méthode pour mettre à jour un utilisateur
    List<User> getAllUser(); //ajout de la méthode pour récupérer tous les utilisateurs
    User getUserById(Long id); //ajout de la méthode pour récupérer un utilisateur par son ID
   User getUserByfname(String firstName); //ajout de la méthode pour récupérer un utilisateur par son email
    List<User> getUserSWT(String firstName); //ajout de la méthode pour récupérer les utilisateurs dont le prénom commence par une certaine chaîne de caractères
    List<User> getUserByEmailDomain(String domaine); //ajout de la méthode pour récupérer les utilisateurs dont l'email contient un certain domaine









}

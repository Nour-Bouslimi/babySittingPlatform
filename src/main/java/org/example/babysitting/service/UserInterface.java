package org.example.babysitting.service;

import org.example.babysitting.entities.User;
import org.example.babysitting.entities.UserRole;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserInterface {
    User addUser(User user);
    void deleteUser(Long id);
    List<User> addListUsers(List<User> users); //ajout de la méthode pour ajouter une liste d'utilisateurs

    //verifier email avant ajout
    String addUserWEmail(User user); //ajout de la méthode pour ajouter un utilisateur avec vérification de l'email
    User updateUser(Long id, User user); //ajout de la méthode pour mettre à jour un utilisateur
    List<User> getAllUser(); //ajout de la méthode pour récupérer tous les utilisateurs
    User getUserById(Long id); //ajout de la méthode pour récupérer un utilisateur par son ID
   User getUserByfname(String firstName); //ajout de la méthode pour récupérer un utilisateur par son email
    boolean getUserByEmail(String email); //ajout de la méthode pour récupérer un utilisateur par son email
   User getUserByEmailUser(String email); //ajout de la méthode pour récupérer un utilisateur par son email
    User getUserByCin(String cin); //ajout de la méthode pour récupérer un utilisateur par son numéro de carte d'identité nationale
    List<User> getUserSWT(String firstName); //ajout de la méthode pour récupérer les utilisateurs dont le prénom commence par une certaine chaîne de caractères
    List<User> getUserByEmailDomain(String domaine); //ajout de la méthode pour récupérer les utilisateurs dont l'email contient un certain domaine
    List<User> getUsersByRole(UserRole role); //ajout de la méthode pour récupérer les utilisateurs par leur rôle
    String saveImage(MultipartFile file); //ajout de la méthode pour enregistrer une image
    byte[] afficherImage(String filename); //ajout de la méthode pour afficher une image
    //forgot password
    String forgotPassword(String email); //ajout de la méthode pour récupérer le mot de passe oublié
    // update user password
    User updateUserPassword(Long id,String currentPassword, String newPassword); //ajout de la méthode pour mettre à jour le mot de passe d'un utilisateur
    //pour la geolocalisation des nourrices
    public List<User> findNearbyNannies(double lat, double lon, double radiusKm);



}

package org.example.babysitting.serviceImplement;

import org.example.babysitting.entities.User;
import org.example.babysitting.entities.UserRole;
import org.example.babysitting.repository.UserRepo;
import org.example.babysitting.service.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserImplement implements UserInterface {
    @Autowired //najem nesta3mel @Ressource elly hya injection par valeur
    UserRepo userRepo;

    @Override
    public User addUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public List<User> addListUsers(List<User> users) {
        return userRepo.saveAll(users);

    }

    @Override
    public String addUserWEmail(User user) {
        String ch = "";
        if (userRepo.existsByEmail(user.getEmail())) {
            ch = "Email already exists";
        } else {
            userRepo.save(user);
            ch = "User added successfully";
        }
        return ch;


    }

    @Override
    public User updateUser(Long id, User user) {
       User u= getUserById(id);
        if (u != null) {
            u.setFirstName(user.getFirstName());
            u.setLastName(user.getLastName());
            u.setEmail(user.getEmail());
            u.setPassword(user.getPassword());
            u.setAddress(user.getAddress());
            u.setPhoneNumber(user.getPhoneNumber());
            u.setAgeChildren(user.getAgeChildren());
            u.setCentreInteret(user.getCentreInteret());
            u.setDateOfBirth(user.getDateOfBirth());
            u.setDomaineEtude(user.getDomaineEtude());
            u.setExperience(user.getExperience());
            u.setEtatCivil(user.getEtatCivil());
            u.setFumer(user.getFumer());
            u.setGenre(user.getGenre());
            u.setImgEtude(user.getImgEtude());
            u.setImgIdent1(user.getImgIdent1());
            u.setImgIdent2(user.getImgIdent2());
            u.setPhoto(user.getPhoto());
            u.setNiveau(user.getNiveau());
            u.setNbChildren(user.getNbChildren());
            u.setLangue(user.getLangue());
            u.setMotorise(user.getMotorise());
            u.setNiveauEtude(user.getNiveauEtude());

            return userRepo.save(u); // on sauvegarde l'utilisateur mis à jour
        }
        return null; // retourne null si l'utilisateur n'existe pas

    }

    @Override
    public List<User> getAllUser() {
        return userRepo.findAll(); // retourne la liste de tous les utilisateurs
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id).orElse(null); // retourne l'utilisateur si trouvé, sinon null
        //ou bien type de retour te3 lmethode n7otou Optional<User> w na3mel  isPresent
    }

    @Override
    public User getUserByfname(String firstName) {
        return userRepo.findByFirstName(firstName); // retourne l'utilisateur dont le prénom correspond à la chaîne de caractères donnée
        //on peut aussi utiliser findByFirstNamejpql(String firstName) ou findByFirstNamesql(String firstName) si on veut utiliser les méthodes nommées ou JPQL/SQL
    }

    @Override
    public boolean getUserByEmail(String email) {
        return userRepo.existsByEmail(email);
    }


    @Override
    public List<User> getUserSWT(String firstName) {
        return userRepo.findBycle(firstName); // retourne la liste des utilisateurs dont le prénom commence par la chaîne de caractères donnée
        //on peut aussi utiliser findByFirstNameStartingWith(String firstName) si on veut utiliser les méthodes nommées
    }

    @Override
    public List<User> getUserByEmailDomain(String domaine) {
        return userRepo.findByEmailDomain(domaine); // retourne la liste des utilisateurs dont l'email contient le domaine donné
    }

    @Override
    public List<User> getUsersByRole(UserRole role) {
        return userRepo.findByRole(role);

    }


}

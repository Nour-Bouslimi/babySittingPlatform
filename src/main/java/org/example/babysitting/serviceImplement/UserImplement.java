package org.example.babysitting.serviceImplement;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.babysitting.entities.User;
import org.example.babysitting.entities.UserRole;
import org.example.babysitting.repository.UserRepo;
import org.example.babysitting.service.UserInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
@Slf4j //n7otha bch nasen3ou logger
@Service
public class UserImplement implements UserInterface {
    @Autowired //najem nesta3mel @Ressource elly hya injection par valeur
    UserRepo userRepo;
    @Autowired
    private EmailSenderService emailSenderService;

    //nasen3ou logger
    private static final Logger LOGGER = LoggerFactory.getLogger(UserImplement.class.getName());
 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public User addUser(User user) {
        // Hash the password before saving
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        // verify if the user already exists by email
        if (userRepo.existsByEmail(user.getEmail())) {
            LOGGER.warn("User with email {} already exists", user.getEmail());
            throw new RuntimeException("User with this email already exists");
        }

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
            u.setPassword(passwordEncoder.encode(user.getPassword())); // Hash le mot de passe avant de le sauvegarder
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
    public User getUserByEmailUser(String email) {
        return userRepo.findByEmail(email); // retourne l'utilisateur dont l'email correspond à la chaîne de caractères donnée
    }

    @Override
    public User getUserByCin(String cin) {
        return userRepo.findByCin(cin); // retourne l'utilisateur dont le numéro de carte d'identité nationale correspond à la chaîne de caractères donnée

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
    Path imagePath = Paths.get("uploads/img");

    @Override
    public String saveImage(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName != null ? originalFileName.substring(originalFileName.lastIndexOf('.')) : "";
        String randomName = RandomStringUtils.randomAlphanumeric(10) + extension;
        try{
            Files.copy(file.getInputStream(),
                    imagePath.resolve(randomName));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return randomName; // retourne le nom de l'image enregistrée
    }

    @Override
    public byte[] afficherImage(String filename) {
        try{
            Path filePath =imagePath.resolve(filename);
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            LOGGER.error("Error reading image file: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    //send email method
    public void sendEmail(String email, String subject, String message) {
        emailSenderService.sendEmail(email, subject, message);
    }
    @Override
    public String forgotPassword(String email) {
        User user = userRepo.findByEmail(email);
        if(user == null){
            LOGGER.warn("User with email {} not found", email);
            return "User not found";
        }
        String newPassword = RandomStringUtils.randomAlphanumeric(8); // Générer un mot de passe aléatoire de 8 caractères
        user.setPassword(passwordEncoder.encode(newPassword)); // Hash le nouveau mot de passe
        userRepo.save(user); // Sauvegarder l'utilisateur avec le nouveau mot de passe
        // Envoyer le nouveau mot de passe par email
        String subject = "Password Reset";
        // check the gender of the user to customize the message
        String salutation = user.getGenre().equals("Male") ? "Mr." : "Mrs.";
        String name = user.getFirstName() != null ? user.getFirstName() : "User";
        String message = "Hello "+salutation +name + "Your new password is: " + newPassword + "\nPlease change it after logging in.";
        sendEmail(email, subject, message);

        LOGGER.info("Password for user with email {} has been reset", email);
        return "New password has been set and sent to your email"; // Retourner un message de succès

    }

    @Override
    public User updateUserPassword(Long id,String currentPassword, String newPassword) {
        User user = getUserById(id);
        if (user == null) {
            LOGGER.warn("User with id {} not found", id);
            return null; // Retourner null si l'utilisateur n'existe pas

        }
        // Vérifier l'ancien mot de passe
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            LOGGER.warn("Old Password incorrect for user {}", id);
            throw new IllegalArgumentException("Old Password incorrect.");
        }
        if(!newPassword.matches("^(?=.*[A-Za-z])(?=.*\\d).{8,}$")) {
            LOGGER.warn("New password does not meet the requirements");
            throw new IllegalArgumentException("Password must contain at least 8 characters, one letter and one digit.");
        }

        user.setPassword(passwordEncoder.encode(newPassword)); // Hash le nouveau mot de passe
        return userRepo.save(user); // Sauvegarder l'utilisateur avec le nouveau mot de passe
    }


}

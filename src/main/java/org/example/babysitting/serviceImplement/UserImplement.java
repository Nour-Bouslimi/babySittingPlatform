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
import java.util.stream.Collectors;

@Slf4j //n7otha bch nasen3ou logger
@Service
public class UserImplement implements UserInterface {
    @Autowired //najem nesta3mel @Ressource elly hya injection par valeur
    UserRepo userRepo;
    @Autowired
    private GeoService geolocationService;
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
        // Enrich user with coordinates if address is provided
        try {
            geolocationService.enrichUserWithCoordinates(user);
        } catch (Exception e) {
            e.printStackTrace();
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
            // Enrich user with coordinates if address is provided
            try {
                geolocationService.enrichUserWithCoordinates(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
            userRepo.save(user);
            ch = "User added successfully";
        }
        return ch;


    }

    @Override
    public User updateUser(Long id, User user) {
        User u = getUserById(id);
        if (u != null) {
            // Gestion des champs selon le rôle
            if (u.getRole() == UserRole.NOUNOU) {
                u.setNbChildren(null);
                u.setAgeChildren(null);
            }
            if (u.getRole() == UserRole.PARENT) {
               // u.setZoneDeDispo(null);
                u.setMotorise(null);
                u.setNiveauEtude(null);
                u.setFumer(null);
                u.setNiveau(null);
                u.setImgIdent1(null);
                u.setImgIdent2(null);
                u.setImgEtude(null);
                u.setExperience(null);
                u.setCentreInteret(null);
                u.setLangue(null);
                //u.setTarifHoraire(null);
            }
            // Mise à jour partielle des champs
            if (user.getFirstName() != null) u.setFirstName(user.getFirstName());
            if (user.getLastName() != null) u.setLastName(user.getLastName());
            if (user.getEmail() != null && !user.getEmail().isEmpty()) u.setEmail(user.getEmail());
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                u.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            if (user.getAddress() != null) u.setAddress(user.getAddress());
            //if (user.getZoneDeDispo() != null) u.setZoneDeDispo(user.getZoneDeDispo());
            if (user.getPhoneNumber() != null) u.setPhoneNumber(user.getPhoneNumber());
            if (user.getAgeChildren() != null) u.setAgeChildren(user.getAgeChildren());
            if (user.getCentreInteret() != null) u.setCentreInteret(user.getCentreInteret());
            if (user.getDateOfBirth() != null) u.setDateOfBirth(user.getDateOfBirth());
            if (user.getDomaineEtude() != null) u.setDomaineEtude(user.getDomaineEtude());
            if (user.getExperience() != null) u.setExperience(user.getExperience());
            if (user.getEtatCivil() != null) u.setEtatCivil(user.getEtatCivil());
            if (user.getFumer() != null) u.setFumer(user.getFumer());
            if (user.getGenre() != null) u.setGenre(user.getGenre());
            if (user.getImgEtude() != null) u.setImgEtude(user.getImgEtude());
            if (user.getImgIdent1() != null) u.setImgIdent1(user.getImgIdent1());
            if (user.getImgIdent2() != null) u.setImgIdent2(user.getImgIdent2());
            if (user.getPhoto() != null) u.setPhoto(user.getPhoto());
            if (user.getNiveau() != null) u.setNiveau(user.getNiveau());
            if (user.getNbChildren() != null) u.setNbChildren(user.getNbChildren());
            if (user.getLangue() != null) u.setLangue(user.getLangue());
            if (user.getMotorise() != null) u.setMotorise(user.getMotorise());
            if (user.getNiveauEtude() != null) u.setNiveauEtude(user.getNiveauEtude());

            // Mise à jour des coordonnées si adresse modifiée
            try {
                geolocationService.enrichUserWithCoordinates(u);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return userRepo.save(u);
        }
        return null;
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
    /*@Override
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
       // String message = "Hello "+salutation + " " +name + "Your new password is: " + newPassword + "\nPlease change it after logging in.\n AlloNouNou Platform";
        String message = "Dear " + salutation + " " + name + ",<br><br>" +
                "We hope this message finds you well.<br><br>" +
                "Following your password reset request, we have generated a new temporary password for your AlloNouNou account:<br><br>" +
                "New Password: <span style='color: red; font-weight: bold;'>" + newPassword + "</span><br><br>" +
                "For security reasons, we strongly recommend that you:<br>" +
                "• Log in as soon as possible using this temporary password<br>" +
                "• Immediately change your password in your account settings<br>" +
                "• Choose a strong and unique password<br><br>" +
                "If you did not request this password reset, please contact us immediately at <a href='mailto:bousliminour70@gmail.com'>allonounou@gmail.com</a><br><br>" +
                "Thank you for your trust in alloNounou.<br><br>" +
                "Best regards,<br>" +
                "The alloNounou Team<br><br>" +
                "---<br>" +
                "This is an automated email, please do not reply.<br>" +
                "For any questions: <a href='mailto:bousliminour70@gmail.com'>allonounou@gmail.com</a>";
        sendEmail(email, subject, message);

        LOGGER.info("Password for user with email {} has been reset", email);
        return "New password has been set and sent to your email"; // Retourner un message de succès

    }*/
    @Override
    public String forgotPassword(String email) {
        User user = userRepo.findByEmail(email);
        if(user == null){
            LOGGER.warn("User with email {} not found", email);
            return "User not found";
        }

        // Générer un mot de passe aléatoire plus sécurisé (12 caractères)
        String newPassword = RandomStringUtils.randomAlphanumeric(12);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        // Préparer l'email
        String subject = "AlloNounou - Password Reset Request";

        // Déterminer la salutation selon le genre
        String salutation = "Male".equalsIgnoreCase(user.getGenre()) ? "Mr." : "Ms.";
        String name = user.getFirstName() != null && !user.getFirstName().trim().isEmpty()
                ? user.getFirstName() : "User";

        String message = "Dear " + salutation + " " + name + ",\n\n" +
                "We hope this message finds you well.\n\n" +
                "Following your password reset request, we have generated a new temporary password for your AlloNouNou account:\n\n" +
                "New Password: " + newPassword + "\n\n" +
                "For security reasons, we strongly recommend that you:\n" +
                "• Log in as soon as possible using this temporary password\n" +
                "• Immediately change your password in your account settings\n" +
                "• Choose a strong and unique password\n\n" +
                "If you did not request this password reset, please contact us immediately at allonounou@gmail.com\n\n" +
                "Thank you for your trust in AlloNouNou.\n\n" +
                "Best regards,\n" +
                "The AlloNouNou Team\n\n" +
                "---\n" +
                "This is an automated email, please do not reply.\n" +
                "For any questions: allonounou@gmail.com";

        try {
            sendEmail(email, subject, message);
            LOGGER.info("Password reset email sent successfully to user: {}", email);
            return "A new temporary password has been sent to your email address. Please check your inbox and follow the instructions.";
        } catch (Exception e) {
            LOGGER.error("Failed to send password reset email to user: {}", email, e);
            return "Failed to send reset email. Please try again later or contact support.";
        }
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
// Pour la géolocalisation des nourrices
    @Override
    public List<User> findNearbyNannies(double lat, double lon, double radiusKm) {
        List<User> nannies = userRepo.findByRole(UserRole.NOUNOU); // ou un champ booléen
        return nannies.stream()
                .filter(nanny -> {
                    if (nanny.getLatitude() == null || nanny.getLongitude() == null) return false;
                    double distance = calculateDistance(lat, lon, nanny.getLatitude(), nanny.getLongitude());
                    return distance <= radiusKm;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void blockUser(Long id) {
        User user = getUserById(id);
        if (user != null) {
            user.setIsBlocked(1); // Mettre à jour le statut de blocage
            userRepo.save(user); // Sauvegarder les modifications
            LOGGER.info("User with id {} has been blocked", id);
        } else {
            LOGGER.warn("User with id {} not found", id);
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void unblockUser(Long id) {
        User user = getUserById(id);
        if (user != null) {
            user.setIsBlocked(0); // Mettre à jour le statut de blocage
            userRepo.save(user); // Sauvegarder les modifications
            LOGGER.info("User with id {} has been unblocked", id);
        } else {
            LOGGER.warn("User with id {} not found", id);
            throw new RuntimeException("User not found");
        }
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Rayon de la Terre en km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }


}

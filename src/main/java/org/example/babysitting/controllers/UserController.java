package org.example.babysitting.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.babysitting.DTO.PasswordUpdateRequest;
import org.example.babysitting.entities.User;
import org.example.babysitting.entities.UserRole;
import org.example.babysitting.repository.UserRepo;
import org.example.babysitting.service.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user") //tetsama global api: ma3neha bch ye5ou nafs les api te3 les controller lkol ama yabdew b /user/esm l'api exp hnee /user/afficher
@CrossOrigin(origins = "http://localhost:4200")  // autorise uniquement le front Angular
public class UserController {

        @Autowired
        UserInterface userInterface;
        UserRepo userRepo; //pour la géolocalisation des nourrices

        @PostMapping("addUser")
        public User addUser(@RequestBody User user){ // @RequestBody y5ou el objet te3 user mil body fyl postman

        return userInterface.addUser(user);
    }

    @DeleteMapping("deleteUser/{id}")
    public void deleteUser(@PathVariable Long id) { // @PathVariable y5ou el id mil url fyl postman w najem nesta3mel @RequestParam ken bch na5ouh mil query parametres
        userInterface.deleteUser(id);
    }

    //delete avec @RequestParam
    @DeleteMapping("delete")
    public void deleteUsers(@RequestParam("a") Long id) { // @PathVariable y5ou el id mil url fyl postman w najem nesta3mel @RequestParam ken bch na5ouh mil query parametres
            userInterface.deleteUser(id);
    }

    //addListUsers
    @PostMapping("addListUsers")
       public List<User> addListUsers(@RequestBody List<User> users){
                return userInterface.addListUsers(users);
    }


    @PostMapping("addUserWEmail")
    public String addUserWEmail(@RequestBody User user){
            return userInterface.addUserWEmail(user);
    }
    @PutMapping("updateUser/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userInterface.updateUser(id, user);
    }

    //affichage
    @GetMapping("getAllUser")
    public List<User> getAllUser() {
        return userInterface.getAllUser();
    }
    @GetMapping("getUserById/{id}")
   /* public User getUserById(@PathVariable Long id) {
        return userInterface.getUserById(id);
    }*/
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userInterface.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
    @GetMapping("getUserByfname/{firstName}")
    public User getUserByfname(@PathVariable String firstName) {
        return userInterface.getUserByfname(firstName);
    }
    @GetMapping("getUserSWT/{firstName}")
    public List<User> getUserSWT(@PathVariable String firstName) {
        return userInterface.getUserSWT(firstName);
    }
    @GetMapping("getUserByEmailDomain/{domaine}")
    public List<User> getUserByEmailDomain(@PathVariable String domaine) {
        return userInterface.getUserByEmailDomain(domaine);
    }
    @GetMapping("getUserByEmailUser/{email}")
    public User getUserByEmailUser(@PathVariable String email) {
        return userInterface.getUserByEmailUser(email);
    }
    @GetMapping("getUsersByRole/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
        try {
            UserRole roleEnum = UserRole.valueOf(role.toUpperCase());
            List<User> users = userInterface.getUsersByRole(roleEnum);
            return ResponseEntity.ok(users);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("getUserByEmail/{email}")
    public boolean getUserByEmail(@PathVariable String email) {
        return userInterface.getUserByEmail(email);
    }
    @GetMapping("getUserByCin/{cin}")
    public User getUserByCin(@PathVariable String cin) {
        return userInterface.getUserByCin(cin);
    }
    //ajout de la méthode pour enregistrer une image dans le dossier uploads/img
    @PostMapping("saveImage")
    public ResponseEntity<String> saveImageToUploads(@RequestParam("file")MultipartFile file){
        String imageName = userInterface.saveImage(file);
        if(imageName == null || imageName.isEmpty()) {
            return ResponseEntity.badRequest().body("Failed to save image");
        }
        return ResponseEntity.ok("Image saved with name: " + imageName);
    }
    //ajout de la méthode pour modifier un profil user pour enregistrer une image d'un user a la fois dans le dossier uploads/img et dans la base de données
    @PutMapping("saveImageForUser/{idUser}")
    public ResponseEntity<?> saveImageForUser(@PathVariable Long idUser, @RequestParam("file") MultipartFile file){
            String imageName= userInterface.saveImage(file);
            if(imageName == null || imageName.isEmpty()){
                return ResponseEntity.badRequest().body("Failed to save image");
            }
            //chercher user avec l'id donné et mettre à jour son image
            User user = userInterface.getUserById(idUser);
                if(user == null){
                    return ResponseEntity.notFound().build();
                }
                user.setPhoto( imageName); //mettre à jour l'image de l'utilisateur //"uploads/img/" +
                userInterface.updateUser(idUser, user); //mettre à jour l'utilisateur dans la base de données
        return ResponseEntity.ok(Map.of(
                "message", "Image saved successfully",
                "imageName", imageName
        ));
    }

//ajout de la méthode pour ajouter un utilisateur avec une image
    @PostMapping(value = "addUserWithImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> addUserWithImage(
            @RequestPart("user") String userJson,
            @RequestPart("file") MultipartFile file) {

        // Désérialisation du JSON en objet User
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());
        User user;
        try {
            user = objectMapper.readValue(userJson, User.class);
        } catch (JsonProcessingException e) {
            System.out.println("Error parsing user JSON: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        String imageName = userInterface.saveImage(file);
        if (imageName == null || imageName.isEmpty()) {
            System.out.println("Failed to save image");
            return ResponseEntity.badRequest().body(null);
        }

        user.setPhoto(imageName);
        User savedUser = userInterface.addUser(user);
        return ResponseEntity.ok(savedUser);
    }

    //ajout de la méthode pour ajouter un user (nounou) avec ces 4 images
    @PostMapping(value = "addNounouWithImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> addNounouWithImages(
            @RequestPart("user") String userJson,
            @RequestPart("photo") MultipartFile photo,
            @RequestPart("imgIdent1") MultipartFile imgIdent1,
            @RequestPart("imgIdent2") MultipartFile imgIdent2,
            @RequestPart("imgEtude") MultipartFile imgEtude) {
        System.out.println("userJson: " + userJson);
        System.out.println("photo: " + photo.getOriginalFilename());
        System.out.println("imgIdent1: " + imgIdent1.getOriginalFilename());
        System.out.println("imgIdent2: " + imgIdent2.getOriginalFilename());
        System.out.println("imgEtude: " + imgEtude.getOriginalFilename());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // Ignore extra fields
        objectMapper.configure(com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_SELF_REFERENCES, false); // Avoid reference issues
        // Disable annotation processing for deserialization to avoid reference issues
        objectMapper.configure(com.fasterxml.jackson.databind.MapperFeature.USE_ANNOTATIONS, false);
        User user;
        try {
            user = objectMapper.readValue(userJson, User.class);
            System.out.println("Deserialized user: " + user);
        } catch (JsonProcessingException e) {
            System.out.println("JSON parsing error: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        String photoName = userInterface.saveImage(photo);
        String imgIdent1Name = userInterface.saveImage(imgIdent1);
        String imgIdent2Name = userInterface.saveImage(imgIdent2);
        String imgEtudeName = userInterface.saveImage(imgEtude);

        user.setPhoto( photoName);
        user.setImgIdent1(imgIdent1Name);
        user.setImgIdent2(imgIdent2Name);
        user.setImgEtude(imgEtudeName);



        User savedUser = userInterface.addUser(user);
        return ResponseEntity.ok(savedUser);
    }



    //ajout de la méthode pour afficher une image
    @GetMapping("displayImage/{filename}")
   /* public ResponseEntity<byte[]> displayImage(@PathVariable String filename){
            byte[] imageData = userInterface.afficherImage(filename);
            String mimeType = URLConnection.guessContentTypeFromName(filename);
            if(mimeType == null){
                mimeType = MediaType.APPLICATION_OCTET_STREAM.toString();
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .body(imageData);
    }*/
    public ResponseEntity<byte[]> displayImage(@PathVariable String filename) {
        try {
            // Assume images are stored in uploads/img/ directory
            Path filePath = Paths.get("uploads/img/" + filename); // Full path
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                byte[] imageData = Files.readAllBytes(filePath);
                String mimeType = URLConnection.guessContentTypeFromName(filename);
                if (mimeType == null) {
                    mimeType = MediaType.APPLICATION_OCTET_STREAM.toString();
                }
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(mimeType))
                        .body(imageData);
            } else {
                System.out.println("Image not found: " + filename);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("Error loading image " + filename + ": " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    //ajout de la méthode pour récupérer le mot de passe oublié
    @GetMapping("forgotPassword/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable String email) {
        String response = userInterface.forgotPassword(email);
        if (response.equals("User not found")) {
            return ResponseEntity.status(404).body(response);
        }
        return ResponseEntity.ok(response);
    }
    //ajout de la méthode pour mettre à jour le mot de passe d'un utilisateur
    @PutMapping("updateUserPassword/{id}")
    public ResponseEntity<?> updateUserPassword(@PathVariable Long id, @RequestBody PasswordUpdateRequest request) {
        String currentPassword = request.getCurrentPassword();
        String newPassword = request.getNewPassword();
        User updatedUser = userInterface.updateUserPassword(id, currentPassword,newPassword);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of("message", "Mot de passe mis à jour"));

    }






    //block user by id
    @PutMapping("blockUser/{id}")
    public ResponseEntity<String> blockUser(@PathVariable Long id) {
        userInterface.blockUser(id);
        return ResponseEntity.ok("User with ID " + id + " has been blocked.");
    }

    //unblock user by id
    @PutMapping("unblockUser/{id}")
    public ResponseEntity<String> unblockUser(@PathVariable Long id) {
        userInterface.unblockUser(id);
        return ResponseEntity.ok("User with ID " + id + " has been unblocked.");
    }


    //ajout de la méthode pour la géolocalisation des nourrices
    @GetMapping("/nearby-nannies/{parentCin}")
    public ResponseEntity<?> getNearbyNannies(@PathVariable String parentCin) {
        User parent = userInterface.getUserByCin(parentCin);
        if (parent == null || parent.getLatitude() == null || parent.getLongitude() == null) {
            return ResponseEntity.badRequest().body("Parent non trouvé ou géolocalisation absente.");
        }
        List<User> nearby = userInterface.findNearbyNannies(parent.getLatitude(), parent.getLongitude(), 25.0); // 20.0 hya distance max elli lezem ykoun fyha nounou b3yd 3al parent Akther m distance heki ma yjibli 7atta nounou
        return ResponseEntity.ok(nearby);
    }

}

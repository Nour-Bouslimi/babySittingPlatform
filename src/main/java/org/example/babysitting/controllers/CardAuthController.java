package org.example.babysitting.controllers;

import org.example.babysitting.entities.User;
import org.example.babysitting.security.JWTGenerator;
import org.example.babysitting.service.OCRService;
import org.example.babysitting.service.UserInterface;
import org.example.babysitting.serviceImplement.FaceService;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class CardAuthController {



        @Autowired
        private OCRService ocrService;

        @Autowired
        private UserInterface userService;

        @Autowired
        private JWTGenerator jwtService;
    @Autowired
    private FaceService faceService;
    // Le chemin absolu de ton dossier "uploads/img"
    private static final String IMAGE_UPLOAD_DIR = "uploads/img/";


    @PostMapping("/card-login")
    public ResponseEntity<?> loginWithCard(@RequestParam("image") MultipartFile image) {
        try {
            // 1. Extraire le CIN à partir de l'image
            String cin = ocrService.extractCinFromImage(image); // Méthode existante
            if (cin == null || "Impossible d'extraire le CIN".equals(cin)) {
                return ResponseEntity.badRequest().body("Impossible d'extraire le CIN de l'image.");
            }

            // 2. Rechercher l'utilisateur par CIN
            User user = userService.getUserByCin(cin);
            if (user == null) {
                return ResponseEntity.badRequest().body("Aucun utilisateur trouvé avec ce CIN.");
            }

            // 3. Convertir MultipartFile en java.io.File temporaire
            File tempFile = File.createTempFile("cin_upload_", ".jpg");
            image.transferTo(tempFile);

            // 4. Extraire la date de naissance depuis l'image
            LocalDate extractedBirthDate = ocrService.extractBirthDateFromImage(tempFile);

            // Supprimer le fichier temporaire
            tempFile.delete();


            if (extractedBirthDate == null) {
                return ResponseEntity.badRequest().body("Impossible d'extraire la date de naissance.");
            }

            // 5. Comparer la date de naissance avec celle stockée
            // Exemple : extraite = "2002-05-06" (String), BD = user.getDateOfBirth() (LocalDate)
            LocalDate extractedDate = LocalDate.parse(extractedBirthDate.toString()); // format "yyyy-MM-dd"
            if (!extractedDate.equals(user.getDateOfBirth())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("La date de naissance ne correspond pas.");
            }

            // 6. Générer un token JWT ou autre logique d'authentification
            String token = jwtService.generateToken(user);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("email", user.getEmail());
            response.put("roles", user.getRole());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'authentification.");
        }
    }



}

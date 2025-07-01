package org.example.babysitting.serviceImplement;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.io.InputStream;
import java.util.Arrays;


@Service
public class FaceService {

    private CascadeClassifier faceDetector;

    public FaceService() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        try (InputStream is = getClass().getResourceAsStream("/haarcascades/haarcascade_frontalface_alt.xml")) {
            if (is == null) {
                System.err.println("Fichier cascade non trouvé dans ressources !");
                throw new IOException("Cascade XML introuvable");
            }
            // Créer un fichier temporaire car CascadeClassifier n'accepte que chemin fichier
            File tempFile = File.createTempFile("haarcascade_frontalface_alt", ".xml");
            Files.copy(is, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            faceDetector = new CascadeClassifier(tempFile.getAbsolutePath());
            tempFile.deleteOnExit();
            if (faceDetector.empty()) {
                System.err.println("Erreur de chargement du cascade classifier.");
                throw new IOException("Cascade classifier vide");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    private Mat multipartFileToMat(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("temp_img", ".jpg");
        file.transferTo(tempFile);
        Mat mat = Imgcodecs.imread(tempFile.getAbsolutePath());
        tempFile.delete();
        return mat;
    }

    // Dans FaceService.java


   /* public Mat extractFace(Mat image) {
        // Définir la zone d'intérêt (ROI) en bas à gauche
        int roiWidth = (int) (image.width() * 0.32);   // ~32% de la largeur
        int roiHeight = (int) (image.height() * 0.60); // ~36% de la hauteur
        int roiX = 0;
        int roiY = image.height() - roiHeight;

        Rect roi = new Rect(roiX, roiY, roiWidth, roiHeight);
        Mat roiImage = new Mat(image, roi);
        // Vérifier si la ROI est valide

        //Imgcodecs.imwrite("debug_roi_" + System.currentTimeMillis() + ".jpg", roiImage);
        // Détection sur la ROI en niveaux de gris
        Mat grayImage = new Mat();
        Imgproc.cvtColor(roiImage, grayImage, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(grayImage, grayImage);

        MatOfRect faces = new MatOfRect();
        faceDetector.detectMultiScale(grayImage, faces, 1.1, 2, 0, new Size(20, 20), new Size(300, 300));
        Rect[] facesArray = faces.toArray();

        if (facesArray.length == 0) {
            System.out.println("***Aucun visage détecté dans la zone ROI***");
            return null;
        }

        // Adapter la position du visage détecté à l'image d'origine
        Rect faceRect = facesArray[0];
        faceRect.x += roiX;
        faceRect.y += roiY;

        // Extraire le visage sur l'image couleur d'origine
        return new Mat(image, faceRect);
    }*/

    public Mat extractFace(Mat image) {
        // Convertir en niveaux de gris
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(grayImage, grayImage);

        // Détection des visages sur toute l'image
        MatOfRect faces = new MatOfRect();
        faceDetector.detectMultiScale(grayImage, faces, 1.05, 1, 0, new Size(20, 20), new Size(400, 400));
        Rect[] facesArray = faces.toArray();
        System.out.println("Nombre de visages détectés : " + facesArray.length);
        if (facesArray.length == 0) {
            System.out.println("***Aucun visage détecté sur l'image***");
            return null;
        }

        // Extraire le premier visage détecté
        Rect faceRect = facesArray[0];
        return new Mat(image, faceRect);

    }

    public byte[] extractFacePlaceholder(MultipartFile imageFile) throws IOException {
        Mat image = multipartFileToMat(imageFile);
        Mat face = extractFace(image);
        if (face == null) {
            System.out.println("Aucun visage détecté dans l'image du CIN.");
            return null;
        }
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".jpg", face, buffer);
        return buffer.toArray();
    }


    public boolean compareFaceImages(byte[] face1Bytes, byte[] face2Bytes) throws IOException {
        Mat img1 = Imgcodecs.imdecode(new MatOfByte(face1Bytes), Imgcodecs.IMREAD_UNCHANGED);
        Mat img2 = Imgcodecs.imdecode(new MatOfByte(face2Bytes), Imgcodecs.IMREAD_UNCHANGED);

        if (img1.empty() || img2.empty()) {
            System.out.println("Une des images est vide ou corrompue.");
            return false;
        }

        // Sauvegarder les images d'entrée pour débogage
        //Imgcodecs.imwrite("input_face1_" + System.currentTimeMillis() + ".jpg", img1);
        //Imgcodecs.imwrite("input_face2_" + System.currentTimeMillis() + ".jpg", img2);

        Mat face1 = extractFace(img1);
        Mat face2 = extractFace(img2);

        if (face1 == null || face2 == null) {
            System.out.println("Impossible de détecter un visage sur l’une des images.");
           /* if (face1 == null) {
                //Imgcodecs.imwrite("failed_face1_" + System.currentTimeMillis() + ".jpg", img1);
            }
            if (face2 == null) {
                //Imgcodecs.imwrite("failed_face2_" + System.currentTimeMillis() + ".jpg", img2);
            }*/
            return false;
        }

        // Redimensionner les visages à une taille standard
        Size standardSize = new Size(128, 128);
        Imgproc.resize(face1, face1, standardSize);
        Imgproc.resize(face2, face2, standardSize);

        // Sauvegarder les visages extraits
        Imgcodecs.imwrite("extracted_face1_" + System.currentTimeMillis() + ".jpg", face1);
        Imgcodecs.imwrite("extracted_face2_" + System.currentTimeMillis() + ".jpg", face2);

        // Pré-traitement : Flou et normalisation
        Imgproc.GaussianBlur(face1, face1, new Size(5, 5), 0);
        Imgproc.GaussianBlur(face2, face2, new Size(5, 5), 0);
        face1.convertTo(face1, CvType.CV_32F);
        face2.convertTo(face2, CvType.CV_32F);

        // Comparaison
        Mat result = new Mat();
        Imgproc.matchTemplate(face1, face2, result, Imgproc.TM_CCOEFF_NORMED);

        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
        System.out.println("Score de similarité : " + mmr.maxVal);

        return mmr.maxVal > 0.5;
    }
    // Nouvelle méthode pour extraire visage en byte[] à partir de MultipartFile
   /* public byte[] extractFacePlaceholder(MultipartFile imageFile) throws IOException {
        Mat image = multipartFileToMat(imageFile);
        Mat face = extractFace(image);
        if (face == null) {
            System.out.println("Aucun visage détecté dans l'image du CIN.");
            return null;
        }
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".jpg", face, buffer);
        byte[] faceBytes = buffer.toArray();
        System.out.println("Taille de l'image extraite du CIN : " + faceBytes.length + " bytes");

        // Sauvegarder l'image extraite pour vérification
        File tempFaceFile = new File("extracted_face_" + System.currentTimeMillis() + ".jpg");
        Imgcodecs.imwrite(tempFaceFile.getAbsolutePath(), face);
        System.out.println("Image visage extraite sauvegardée à : " + tempFaceFile.getAbsolutePath());

        return faceBytes;
    }*/
}





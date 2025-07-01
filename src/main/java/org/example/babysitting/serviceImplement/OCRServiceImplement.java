package org.example.babysitting.serviceImplement;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.example.babysitting.service.OCRService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.*;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.imgscalr.Scalr;
@Service
public class OCRServiceImplement implements OCRService {

    private final Tesseract tesseract;

    public OCRServiceImplement() {
        tesseract = new Tesseract();  // initialise le champ d'instance
        File tessDataFolder = new File("src/main/resources/tessdata");
        tesseract.setDatapath(tessDataFolder.getAbsolutePath());
        tesseract.setLanguage("fra");
    }

    @Override
    public String extractCinFromImage(MultipartFile imageFile) {
        try {
            // 1. Convertir MultipartFile en BufferedImage
            BufferedImage originalImage = ImageIO.read(imageFile.getInputStream());

            // 2. Recadrer l'image sur la zone où se trouve le CIN
            int zoneWidth = originalImage.getWidth() / 2;
            int zoneHeight = originalImage.getHeight() / 6;
            int zoneX = originalImage.getWidth() / 4;
            int zoneY = originalImage.getHeight() / 4;

            BufferedImage croppedImage = originalImage.getSubimage(zoneX, zoneY, zoneWidth, zoneHeight);

            // 3. Redimensionner l'image pour une meilleure résolution
            BufferedImage resizedImage = Scalr.resize(croppedImage, Scalr.Method.ULTRA_QUALITY, 600, 200); // Ajustez 600x200 selon vos besoins

            // 4. Pré-traitement : Convertir en niveaux de gris et appliquer un seuillage
            BufferedImage grayImage = new BufferedImage(resizedImage.getWidth(), resizedImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D g2d = grayImage.createGraphics();
            g2d.drawImage(resizedImage, 0, 0, null);
            g2d.dispose();

            // Appliquer un seuillage simple (binarisation), ajustez le seuil si nécessaire
            for (int x = 0; x < grayImage.getWidth(); x++) {
                for (int y = 0; y < grayImage.getHeight(); y++) {
                    int rgb = grayImage.getRGB(x, y);
                    int r = (rgb >> 16) & 0xff;
                    int g = (rgb >> 8) & 0xff;
                    int b = rgb & 0xff;
                    int gray = (r + g + b) / 3;
                    int newPixel = gray < 150 ? 0 : 255; // Ajusté à 150 pour un meilleur contraste
                    grayImage.setRGB(x, y, (newPixel << 16) | (newPixel << 8) | newPixel);
                }
            }

            // 5. Sauvegarder temporairement le pré-traité pour Tesseract
            File tempFile = File.createTempFile("cin_crop", ".png");
            ImageIO.write(grayImage, "png", tempFile);

            // Sauvegarder pour vérification
            ImageIO.write(grayImage, "png", new File("test_cropped.png"));

            // 6. Configurer Tesseract
            Tesseract tesseract = new Tesseract();
            File tessDataFolder = new File("src/main/resources/tessdata");
            tesseract.setDatapath(tessDataFolder.getAbsolutePath());
            tesseract.setLanguage("fra");
            tesseract.setTessVariable("tessedit_char_whitelist", "0123456789");

            // 7. Extraire le texte (CIN)
            String result = tesseract.doOCR(tempFile);

            // 8. Nettoyer le texte pour ne garder que les chiffres
            String cin = result.replaceAll("[^0-9]", "");

            // 9. Vérifier la longueur et afficher dans IntelliJ (via console)
            if (cin.length() == 8) {
                System.out.println("Extracted CIN: " + cin);
                return cin;
            } else {
                System.out.println("Extracted CIN: Impossible d'extraire le CIN");
                return "Impossible d'extraire le CIN";
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Extracted CIN: Impossible d'extraire le CIN");
            return "Impossible d'extraire le CIN";
        } catch (TesseractException e) {
            e.printStackTrace();
            System.out.println("Extracted CIN: Impossible d'extraire le CIN");
            return "Impossible d'extraire le CIN";
        }
    }

    @Override
    public File convert(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(convFile);
        return convFile;
    }



    private String cleanOCRText(String rawText) {
        return rawText.replaceAll("[\\r\\n]+", " ")     // supprime les sauts de ligne
                .replaceAll("[^\\p{IsArabic}\\d\\s]", "")  // supprime les caractères non arabes et non numériques
                .trim();
    }

    private String normalizeArabicMonth(String month) {
        // Remplace les variantes courantes
        return month.replace('ى', 'ي').replace('ئ', 'ي').replace('ة', 'ه');
    }
    // partie pour extraire la date de naissance à partir d'une carte d'identité tunisienne
    private static final Map<String, String> ARABIC_MONTHS = Map.ofEntries(
            Map.entry("جانفي", "01"),
            Map.entry("فيفري", "02"),
            Map.entry("مارس", "03"),
            Map.entry("أفريل", "04"),
            Map.entry("ماي", "05"),
            Map.entry("جوان", "06"),
            Map.entry("جويلية", "07"),
            Map.entry("أوت", "08"),
            Map.entry("سبتمبر", "09"),
            Map.entry("أكتوبر", "10"),
            Map.entry("نوفمبر", "11"),
            Map.entry("ديسمبر", "12")
    );

    public LocalDate extractBirthDateFromImage(File imageFile) {
        try {
            BufferedImage original = ImageIO.read(imageFile);
            int cropWidth = original.getWidth() / 3;
            int cropHeight = original.getHeight() / 10;
            int cropX = original.getWidth() / 2;
            int cropY = original.getHeight() - (int) (cropHeight * 2.5);

            BufferedImage birthDateZone = original.getSubimage(cropX, cropY, cropWidth, cropHeight);
            File debugFile = new File("birthdate_zone_" + System.currentTimeMillis() + ".png");
            ImageIO.write(birthDateZone, "png", debugFile);

            Tesseract tesseract = new Tesseract();
            File tessDataFolder = new File("src/main/resources/tessdata");
            tesseract.setDatapath(tessDataFolder.getAbsolutePath());
            tesseract.setLanguage("ara");
            String rawText = tesseract.doOCR(birthDateZone);

            String cleaned = cleanOCRText(rawText);
            System.out.println("Texte OCR brut : " + rawText);
            System.out.println("Texte nettoyé : " + cleaned);

            Pattern pattern = Pattern.compile("(\\d{1,2})\\s+([\\p{IsArabic}]+)\\s+(\\d{4})");
            Matcher matcher = pattern.matcher(cleaned);

            if (matcher.find()) {
                String day = matcher.group(1);
                String arabicMonthRaw = matcher.group(2);
                String arabicMonth = ARABIC_MONTHS.get(normalizeArabicMonth(arabicMonthRaw));
                String year = matcher.group(3);
                if (arabicMonth == null) return null;
                String formatted = year + "-" + arabicMonth + "-" + String.format("%02d", Integer.parseInt(day));
                return LocalDate.parse(formatted);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


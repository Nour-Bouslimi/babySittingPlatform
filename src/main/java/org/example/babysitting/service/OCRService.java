package org.example.babysitting.service;

import net.sourceforge.tess4j.TesseractException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public interface OCRService {
    public String extractCinFromImage(MultipartFile imageFile) throws Exception;
    public File convert(MultipartFile file) throws IOException;
    public LocalDate extractBirthDateFromImage(File imageFile);
}

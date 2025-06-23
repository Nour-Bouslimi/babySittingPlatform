package org.example.babysitting.repository;

import org.example.babysitting.entities.Annonce;
import org.example.babysitting.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface AnnonceRepo extends JpaRepository<Annonce, Long> {
    //Named methods
    List<Annonce> findByIdUser(Long idUser);
    List<Annonce> findByDate(LocalDate date);
    boolean existsByIdAnnonce(Long idAnnonce);
    List<Annonce> findByTitreContaining(String titre); // Method to find annonces by title containing a specific string
    List<Annonce> findByDescriptionContaining(String description); // Method to find annonces by description containing a specific string







}

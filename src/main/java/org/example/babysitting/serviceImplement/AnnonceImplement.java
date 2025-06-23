package org.example.babysitting.serviceImplement;

import org.example.babysitting.entities.Annonce;
import org.example.babysitting.repository.AnnonceRepo;
import org.example.babysitting.service.AnnonceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class AnnonceImplement implements AnnonceInterface {

@Autowired
    AnnonceRepo annonceRepo;
    @Override
    public Annonce addAnnonce(Annonce annonce) {
        return annonceRepo.save(annonce);
    }

    @Override
    public void deleteAnnonce(Long id) {
        if (annonceRepo.existsByIdAnnonce(id)) {
            annonceRepo.deleteById(id);
        } else {
            throw new IllegalArgumentException("Annonce with id " + id + " does not exist.");
        }

    }

    @Override
    public List<Annonce> addListAnnonces(List<Annonce> annonces) {
        return annonceRepo.saveAll(annonces);
    }

    @Override
    public Annonce updateAnnonce(Long id, Annonce annonce) {
        Annonce a =getAnnonceById(id);
        if (a != null) {
            a.setTitre(annonce.getTitre());
            a.setDescription(annonce.getDescription());
            a.setDate(annonce.getDate());
            a.setIdUser(annonce.getIdUser());
            return annonceRepo.save(a);
        } else {
            throw new IllegalArgumentException("Annonce with id " + id + " does not exist.");
        }
    }

    @Override
    public List<Annonce> getAllAnnonces() {
        return annonceRepo.findAll();
    }

    @Override
    public Annonce getAnnonceById(Long id) {
        return annonceRepo.findById(id).orElse(null);
    }

    @Override
    public List<Annonce> getAnnoncesByIdUser(Long idUser) {
        return annonceRepo.findByIdUser(idUser);
    }

    @Override
    public List<Annonce> getAnnoncesByDate(LocalDate date) {
        return annonceRepo.findByDate(date);
    }

    @Override
    public List<Annonce> getAnnoncesByTitreContaining(String titre) {
        return annonceRepo.findByTitreContaining(titre);
    }

    @Override
    public List<Annonce> getAnnoncesByDescriptionContaining(String description) {
        return annonceRepo.findByDescriptionContaining(description);
    }
}

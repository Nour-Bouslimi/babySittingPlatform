package org.example.babysitting.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Entity
@Data// Lombok will generate getters, setters, toString, equals, and hashCode methods
public class User {
    @Id
   @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idUser;

    @Column(name="firstName", nullable = false, length = 50)
    @Pattern(regexp = "^[a-zA-Z]+$",
             message = "First name should contain only letters")
    @JsonProperty("firstName")
    private String firstName;
    @Column( name = "lastName",nullable = false, length = 50)
    @Pattern(regexp = "^[a-zA-Z]+$",
             message = "Last name should contain only letters")
    @JsonProperty("lastName")
    private String lastName;
    @Column( name = "email",nullable = false, length = 100, unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
             message = "Email should be valid")
    @JsonProperty("email")
    private String email;
    @Column( name = "password",nullable = false, length = 100)
   // @Size(min = 8, max = 20, message = "Password must be between 8 and 15 characters")

    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$",
            message = " Password must contains at least 8 caracters , One letter and one digit. "

    )

    @JsonProperty("password")
    private String password;
    @Column(name = "phoneNumber", nullable = false, length = 20)
    @Pattern(regexp = "^\\+?\\d{8,20}$", message = "The phone number must contain only digits, with an optional + at the beginning")
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @Column(name = "address", nullable = false, length = 150)
    /*@Pattern(regexp = "^[a-zA-Z0-9\\s,.'-]{3,150}$",
             message = "Address should be valid")*/
    @JsonProperty("address")
    private String address;

    @Column(name = "cin", nullable = false, length = 8, unique = true)
    @Pattern(regexp = "^[0-9]{8}$", message = "CIN must be exactly 8digits")
    @JsonProperty("cin")
    private String cin;
    @Column( name = "genre",nullable = false, length = 20)
    @JsonProperty("genre")
    private String genre;
    @Enumerated(EnumType.STRING)
    @Column( name = "role",nullable = false, length = 20)
    @JsonProperty("role")
    private UserRole role;
    @JsonProperty("isBlocked")
 @Column( name = "isBlocked")
    private int isBlocked;
    @JsonProperty("photo")
 @Column( name = "photo")
    private String photo;
    @JsonProperty("dateOfBirth")
 @Column( name = "dateOfBirth")
    private LocalDate dateOfBirth;
    @JsonProperty("nbChildren")
 @Column( name = "nbChildren")
    private int nbChildren;
    @Pattern(
            regexp = "^(\\d{1,2})\\s?(mois|ans)$",
            message = "ageChildren must be in the format 'X mois' or 'X ans'"
    )
    @JsonProperty("ageChildren")
    @Column( name = "ageChildren")
    private String ageChildren;
    @JsonProperty("etatCivil")
    @Column(name = "etatCivil", length = 30)
    private String etatCivil;
    @JsonProperty("niveauEtude")
    @Column( name = "niveauEtude",length = 30)
    private String niveauEtude;
    @JsonProperty("experience")
 @Column( name = "experience")
    private String experience;
    @JsonProperty("domaineEtude")
    @Column(name="domaineEtude", length = 100)
    private String domaineEtude;
    @JsonProperty("imgEtude")
    @Column(name="imgEtude", length = 255)
    private String imgEtude;
    @JsonProperty("langue")
    @Column(name="langue", length = 50)
    private String langue;
    @JsonProperty("niveau")
    @Column(name="niveau", length = 50)
    private String niveau;
    @JsonProperty("centreInteret")
    @Column( name="centreInteret",length = 100)
    private String centreInteret;
    @JsonProperty("motorise")
    @Column(name="motorise", length = 10)
    private String motorise;
    @JsonProperty("fumer")
    @Column(name="fumer", length = 10)
    private String fumer;
    @JsonProperty("imgIdent1")
    @Column(name="imgIdent1")
    private String imgIdent1;
    @JsonProperty("imgIdent2")
    @Column(name="imgIdent2")
    private String imgIdent2;
    @JsonProperty("zoneDeDispo")
    @Column(name="zoneDeDispo", length = 100)
    private String zoneDeDispo;
    @JsonProperty("tarifHoraire")
    @Column(name="tarifHoraire")
    private float tarifHoraire;


    //association with Annonce
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Annonce> annonces;

    //association with Disponibilite
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Disponibilite> disponibilites;

    public User() {

    }
    //association with Reservation
    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Reservation> reservations;


    //association with Message
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch= FetchType.LAZY)
    private List<Message> messages;
    //association with Notification
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Notification> notifications;

    //association with Reponse
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reponse> reponses;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String firstName) {
        this.firstName = firstName;
    }

    public  String getLastName() {
        return lastName;
    }

    public void setLastName( String lastName) {
        this.lastName = lastName;
    }

    public  String getEmail() {
        return email;
    }

    public void setEmail( String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public  String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber( String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public int getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(int isBlocked) {
        this.isBlocked = isBlocked;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth != null && dateOfBirth.isAfter(LocalDate.now().minusYears(19))) {
            throw new IllegalArgumentException("User must be at least 19 years old (born in 2006 or before)");
        }

        this.dateOfBirth = dateOfBirth;
    }

    public int getNbChildren() {
        return nbChildren;
    }

    public void setNbChildren(int nbChildren) {
        this.nbChildren = nbChildren;
    }

    public String getAgeChildren() {
        return ageChildren;
    }

    public void setAgeChildren(String ageChildren) {


        this.ageChildren = ageChildren;
    }

    public String getEtatCivil() {
        return etatCivil;
    }

    public void setEtatCivil(String etatCivil) {
        this.etatCivil = etatCivil;
    }

    public String getNiveauEtude() {
        return niveauEtude;
    }

    public void setNiveauEtude(String niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getDomaineEtude() {
        return domaineEtude;
    }

    public void setDomaineEtude(String domaineEtude) {
        this.domaineEtude = domaineEtude;
    }

    public String getImgEtude() {
        return imgEtude;
    }

    public void setImgEtude(String imgEtude) {
        this.imgEtude = imgEtude;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getCentreInteret() {
        return centreInteret;
    }

    public void setCentreInteret(String centreInteret) {
        this.centreInteret = centreInteret;
    }

    public String getMotorise() {
        return motorise;
    }

    public void setMotorise(String motorise) {
        this.motorise = motorise;
    }

    public String getFumer() {
        return fumer;
    }

    public void setFumer(String fumer) {
        this.fumer = fumer;
    }

    public String getImgIdent1() {
        return imgIdent1;
    }

    public void setImgIdent1(String imgIdent1) {
        this.imgIdent1 = imgIdent1;
    }

    public String getImgIdent2() {
        return imgIdent2;
    }

    public void setImgIdent2(String imgIdent2) {
        this.imgIdent2 = imgIdent2;
    }

    public String getZoneDeDispo() {
        return zoneDeDispo;
    }

    public void setZoneDeDispo(String zoneDeDispo) {
        this.zoneDeDispo = zoneDeDispo;
    }

    public float getTarifHoraire() {
        return tarifHoraire;
    }

    public void setTarifHoraire(float tarifHoraire) {
        this.tarifHoraire = tarifHoraire;
    }
}

package org.example.babysitting.repository;

import org.example.babysitting.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    // Additional query methods can be defined here if needed
    //on a 3 types de methodes pour faire les requetes:
    //named methods , JPQL methods (ya3mel des requete 3al entity fy wost lcode), SQL methods

    //named methods: on y7ot esm el methode w Spring y3refha w y3ref chnowa bch y5dem biha
    List<User> findByEmailAndFirstNameStartingWith(String email, String firstName);
    boolean existsByEmail(String email); //vérifie si l'email existe déjà dans la base de données
    boolean existsByFirstName(String firstName); //vérifie si le prénom existe déjà dans la base de données
    User findByFirstName(String firstName); //trouve un utilisateur par son prénom

    // JPQL methods: requete 3al entity fy wost spring boot

    @Query("SELECT u from User u WHERE u.firstName=?1")  // ma3neha: select * from user where firstName= ?1 1 howa l'index te3 el parametre eli bch na3tiwha lel requete
    User findByFirstNamejpql(String firstNamme); // trouve un utilisateur par son prénom, mais avec une requête JPQL
    @Query("SELECT CASE WHEN COUNT (u)>0 THEN true ELSE false end  from User u WHERE u.firstName=:firstName") // ma3neha: select * from user where firstName= :firstName elly howa el parametre eli 3tytou lel methode
    boolean existsByFirstNamejql(@Param("firstName") String firstName); // vérifie si le prénom existe déjà dans la base de données, mais avec une requête JPQL

    //SQL methods: requete 3al base de données
    @Query(value="SELECT * from user u WHERE u.first_name=?1", nativeQuery = true) // nativeQuery = true bch ya3ref elly ana ne5dem b SQL
    List<User> findByFirstNamesql(String firstName); // trouve un utilisateur par son prénom, mais avec une requête SQL

    @Query(value="SELECT COUNT(*) from user WHERE firstName:u", nativeQuery = true) // ma3neha: select count(*) from user where firstName= :firstName

    boolean existsByFirstNamesql(@Param("u") String firstName); // vérifie si le prénom existe déjà dans la base de données, mais avec une requête SQL)

    @Query(value="select * from user where firstName like :cle%",nativeQuery = true)
    List<User> findBycle(@Param("cle") String un); // trouve les utilisateurs dont le prénom commence par une certaine chaîne de caractères, avec une requête SQL

    @Query(value="select * from user where email like %:domaine%",nativeQuery = true)
    List<User> findByEmailDomain(@Param("domaine") String domaine); // trouve les utilisateurs dont l'email contient un certain domaine, avec une requête SQL








}

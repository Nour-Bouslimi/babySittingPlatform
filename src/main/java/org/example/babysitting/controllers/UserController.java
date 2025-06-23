package org.example.babysitting.controllers;

import org.example.babysitting.entities.User;
import org.example.babysitting.entities.UserRole;
import org.example.babysitting.service.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user") //tetsama global api: ma3neha bch ye5ou nafs les api te3 les controller lkol ama yabdew b /user/esm l'api exp hnee /user/afficher
public class UserController {

        @Autowired
        UserInterface userInterface;

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
    public User getUserById(@PathVariable Long id) {
        return userInterface.getUserById(id);
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
    @GetMapping("getUsersByRole/{role}")
    public List<User> getUsersByRole(@PathVariable String role) {
         UserRole roleEnum = UserRole.valueOf(role.toUpperCase());
        return userInterface.getUsersByRole(roleEnum);
    }
    @GetMapping("getUserByEmail/{email}")
    public boolean getUserByEmail(@PathVariable String email) {
        return userInterface.getUserByEmail(email);
    }
}

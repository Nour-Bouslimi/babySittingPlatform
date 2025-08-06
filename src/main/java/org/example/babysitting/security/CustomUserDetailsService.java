package org.example.babysitting.security;

import org.example.babysitting.entities.User;
import org.example.babysitting.entities.UserRole;
import org.example.babysitting.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo; // Assuming you have a UserRepo to fetch user data
   /* @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Here you would typically fetch the user from the database using the email
        User user = userRepo.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRoleToAuthorities(user.getRole()));
    }

    private Collection<GrantedAuthority> mapRoleToAuthorities(UserRole role){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return authorities;
    }*/
   @Override
   public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       User user = userRepo.findByEmail(email);

            // .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
       System.out.println(user);
       // Vérifier si le mot de passe est encodé
       return new org.springframework.security.core.userdetails.User(
               user.getEmail(),
               user.getPassword(), // Doit être encodé (par exemple, avec BCrypt)
               mapRolesToAuthorities(user.getRole())
       );
   }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(UserRole role) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name())); // Ajouter le préfixe ROLE_
        return authorities;
    }




}

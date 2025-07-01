package org.example.babysitting.security;

import org.example.babysitting.entities.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Configuration de la sécurité de l'application
// Vous pouvez ajouter des configurations de sécurité ici, comme les filtres, les règles d'autorisation, etc.
// Par exemple, vous pouvez configurer les endpoints sécurisés, les rôles, etc.

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
@Autowired
    public SecurityConfig(JwtAuthEntryPoint jwtAuthEntryPoint) {
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults()) // Activer CORS avec les paramètres par défaut 5ater spring boot yeblouki les requete mil web
            .csrf(csrf -> csrf.disable()) // Désactiver CSRF pour les requêtes HTTP (peut être nécessaire pour les API REST)
            .sessionManagement(session-> session.sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS)) // Utiliser une session sans état (stateless) pour les API REST
            .exceptionHandling(exception -> exception
                    .authenticationEntryPoint(jwtAuthEntryPoint)) // Gérer les exceptions d'authentification avec JwtAuthEntryPoint
            .authorizeHttpRequests(auth-> auth
                    .requestMatchers("/api/auth/**").permitAll() // Autoriser les requêtes vers /api/auth/** sans authentification
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Autoriser les requêtes OPTIONS (CORS) sans authentification
                    //.requestMatchers("/dashboard/**").hasAuthority(UserRole.ADMIN.toString()) // Autoriser les requêtes vers /dashboard/** uniquement pour les utilisateurs avec le rôle ADMIN
                            .anyRequest().permitAll()
                    //.anyRequest().authenticated() // Toutes les autres requêtes nécessitent une authentification
            );
    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // Ajouter le filtre JWTAuthenticationFilter avant le filtre UsernamePasswordAuthenticationFilter

    return http.build(); // Construire et retourner le SecurityFilterChain
}
@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
}
@Bean
    public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}
@Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception {
    return new JWTAuthenticationFilter();

    }



}

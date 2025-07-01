package org.example.babysitting.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Cette classe étend OncePerRequestFilter pour s'assurer que le filtre est appliqué une seule fois par requête.
// Vous pouvez implémenter la logique de filtrage ici, par exemple, pour vérifier le token JWT dans les en-têtes de la requête.

public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JWTGenerator tokenGenerator;
    private CustomUserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Récupérer le token JWT de l'en-tête Authorization
        String token = getJWTFromRequest(request);
        if(StringUtils.hasText(token) && tokenGenerator.validateToken(token)){
            // Si le token est valide, extraire les informations de l'utilisateur et les mettre dans le contexte de sécurité
            String email = tokenGenerator.getEmailFromJWT(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            // Créer un objet d'authentification avec les détails de l'utilisateur
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
           // Définir les détails de l'authentification
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // Mettre l'authentification dans le contexte de sécurité
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // faire le filtrage de la requête
            filterChain.doFilter(request, response);
        }
        else {
            // Si le token n'est pas valide, passer au filtre suivant sans authentification
            filterChain.doFilter(request, response);
        }
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7,bearerToken.length()); // Retirer le préfixe "Bearer " pour obtenir le token
        }
        return null; // Si le token n'est pas présent ou mal formé, retourner null
    }





}

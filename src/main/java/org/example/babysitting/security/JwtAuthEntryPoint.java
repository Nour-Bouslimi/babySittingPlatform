package org.example.babysitting.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
// si fama problem d'authentification (elly houma yee unauthorized yee forbidden) lclasse hedhy va gérer l'exception et renvoyer une réponse appropriée
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: " + authException.getMessage());
        // SC_UNAUTHORIZED = 401 : C'est une constante HTTP qui indique que la requête nécessite une authentification



    }
}

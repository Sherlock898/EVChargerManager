package com.noder.restapi.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        System.out.println("Unauthorized error on " + request.getRequestURI() + " [" + request.getMethod() + "]");
        System.out.println("User: " + request.getRemoteUser() + " is not authorized to access this resource.");
        if (request.getUserPrincipal() != null) {
            System.out.println("Roles: " + request.getUserPrincipal().getName());
        }

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String json = String.format("{\"error\":\"Unauthorized\", \"message\":\"%s\"}", authException.getMessage());
        response.getWriter().write(json);
    }
    
}

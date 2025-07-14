package com.RicardoRosendo.ProjectSimples.Security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.RicardoRosendo.ProjectSimples.Exceptions.GlobalExceptionHandler;
import com.RicardoRosendo.ProjectSimples.models.Users;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authententicationManager;
    private JWTUtil jwtUtil;

    public JWTAuthenticationFilter(AuthenticationManager authententicationManager, JWTUtil jwtUtil) {
        setAuthenticationFailureHandler(new GlobalExceptionHandler());
        this.authententicationManager = authententicationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            Users userCredentials = new ObjectMapper().readValue(request.getInputStream(), Users.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userCredentials.getUserName(), userCredentials.getPassWord(), new ArrayList<>());
            return this.authententicationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new AuthenticationException("Invalid authentication credentials") {
                @Override
                public String getMessage() {
                    return "Error parsing authentication data";
                }
            };
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain, Authentication authResult) throws IOException, ServletException {
        UserSpringSecurity userSpringSecurity = ((UserSpringSecurity) authResult.getPrincipal());
        String userName = userSpringSecurity.getUsername();
        String token = this.jwtUtil.generationToken(userName);
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        SecurityContextHolder.getContext().setAuthentication(authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        // Define status de erro 401 e escreve um JSON no corpo da resposta
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\"error\": \"Authentication failed\"}");
    }
}

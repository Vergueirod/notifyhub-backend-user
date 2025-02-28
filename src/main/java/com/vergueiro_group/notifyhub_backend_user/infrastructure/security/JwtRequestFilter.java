package com.vergueiro_group.notifyhub_backend_user.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Define a classe JwtRequestFilter, que estende OncePerRequestFilter
public class JwtRequestFilter extends OncePerRequestFilter {

    // Define propriedades para armazenar instâncias de JwtUtil e UserDetailsService
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    // Construtor que inicializa as propriedades com instâncias fornecidas
    public JwtRequestFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    // Método chamado uma vez por requisição para processar o filtro
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Obtém o valor do header "Authorization" da requisição
        final String authorizationHeader = request.getHeader("Authorization");
        logger.debug("Cabeçalho Authorization: {}", authorizationHeader); // Log do cabeçalho recebido

        String token = null;
        String username = null;
        logger.debug("🚀 JwtRequestFilter chamado para: " + request.getRequestURI());
        // Verifica se o cabeçalho existe e começa com "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(token);
            logger.debug("Token extraído: {}", token);
            logger.debug("Usuário extraído do token: {}", username);
        } else {
            logger.debug("Nenhum token JWT encontrado no cabeçalho Authorization.");
        }

        // Se o nome de usuário não for nulo e o usuário não estiver autenticado ainda
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Carrega os detalhes do usuário a partir do nome de usuário
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Valida o token JWT
            if (jwtUtil.validateToken(token, username)) {
                logger.debug("Token válido, autenticando usuário...");

                // Cria um objeto de autenticação com as informações do usuário
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Define a autenticação no contexto de segurança
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                logger.debug("Token inválido!");
            }
        }

        // Continua a cadeia de filtros, permitindo que a requisição prossiga
        chain.doFilter(request, response);
    }

}

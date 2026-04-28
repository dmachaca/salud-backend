package com.pe.security.filter;

import com.pe.security.jwt.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        try {

            Claims claims = jwtService.extractClaims(token);

            String type = claims.get("type", String.class);
            if (!"ACCESS".equals(type)) {
                throw new RuntimeException("TOKEN NO ES ACCESS");
            }

            String username = claims.getSubject();

            if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {


                var roles = (List<String>) claims.get("roles");

                Collection<SimpleGrantedAuthority> authorities =
                        roles == null ? List.of() :
                                roles.stream()
                                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                                        .toList();

                var auth = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        authorities
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {

            e.printStackTrace();
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }
}
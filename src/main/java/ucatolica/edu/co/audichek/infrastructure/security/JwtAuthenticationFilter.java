package ucatolica.edu.co.audichek.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ucatolica.edu.co.audichek.exceptions.InvalidTokenException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtilComponent jwtUtil;

    public JwtAuthenticationFilter(JwtUtilComponent jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.isTokenValid(token)) {
            throw new InvalidTokenException("Token is invalid or expired");
        }


        String userId = jwtUtil.extractId(token);
        List<String> authoritiesFromToken = jwtUtil.extractAuthorities(token);
        String status = jwtUtil.extractStatus(token);

        if (status == null || !status.equalsIgnoreCase("ACTIVE")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{ \"error\": \"Tu cuenta no está activa.\" }");
            return;
        }

        var authorities = authoritiesFromToken.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        var authToken = new UsernamePasswordAuthenticationToken(userId, null, authorities);
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}

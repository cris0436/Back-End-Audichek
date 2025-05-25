package ucatolica.edu.co.audichek.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.PersonStatus;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.RolePermission;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtilComponent {

    @Value("${jwt.secret}")
    private String SECRET;

    private static final long EXPIRATION_MS = 86400000; // 24 horas

    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC512(SECRET);
    }

    public String generateToken(Person person, List<RolePermission> personRoles , PersonStatus status) {
        if (person == null || personRoles == null) {
            throw new IllegalArgumentException("El objeto 'Person' o 'personRoles' no puede ser null.");
        }

        List<String> authorities = personRoles.stream()
                .map(permission -> permission.getPermission().getPermissionName())
                .distinct()
                .collect(Collectors.toList());

        return JWT.create()
                .withSubject(person.getEmail())
                .withClaim("id", person.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .withClaim("authorities", authorities)
                .withClaim("status" ,status.getDescripcion())
                .sign(algorithm);
    }

    public boolean isTokenValid(String token) {
        try {
            JWT.require(algorithm).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            System.err.println("Token inválido: " + e.getMessage());
            return false;
        }
    }

    public String extractId(String token) {
        try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
            return jwt.getClaim("id").asString();
        } catch (JWTVerificationException e) {
            System.err.println("Error al extraer ID: " + e.getMessage());
            return null;
        }
    }

    public List<String> extractAuthorities(String token) {
        try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
            return jwt.getClaim("authorities").asList(String.class);
        } catch (JWTVerificationException e) {
            System.err.println("Error al extraer authorities: " + e.getMessage());
            return null;
        }
    }

    public String extractStatus(String token) {
        try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
            return jwt.getClaim("status").asString();
        } catch (JWTVerificationException e) {
            System.err.println("Error al extraer status: " + e.getMessage());
            return null;
        }
    }
}

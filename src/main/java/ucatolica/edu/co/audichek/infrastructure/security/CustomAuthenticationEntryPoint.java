package ucatolica.edu.co.audichek.infrastructure.security;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ucatolica.edu.co.audichek.exceptions.InvalidTokenException;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        System.out.println(">> authException: " + authException.getClass().getSimpleName() + " - " + authException.getMessage());

        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        String errorMessage = "Authentication is required";

        if (authException instanceof InvalidTokenException) {
            statusCode = HttpServletResponse.SC_FORBIDDEN;
            errorMessage = "Token is invalid or expired";
        }

        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"" + errorMessage + "\"}");
    }
}

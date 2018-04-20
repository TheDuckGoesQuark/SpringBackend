package BE.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        BE.exceptions.excludedFromBaseException.AuthenticationException ex = (BE.exceptions.excludedFromBaseException.AuthenticationException) e;
        response.sendError(HttpStatus.UNAUTHORIZED.value(), ex.getErrorResponse().toString());
    }
}

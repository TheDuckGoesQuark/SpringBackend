package BE.security.handlers;

import BE.security.enums.AuthenticationFailureType;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding("UTF-8");
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("error", AuthenticationFailureType.INVALID_REQUEST);
        jsonResponse.put("error_description", AuthenticationFailureType.INVALID_REQUEST);
        httpServletResponse.getWriter().write(jsonResponse.toString());
    }
}

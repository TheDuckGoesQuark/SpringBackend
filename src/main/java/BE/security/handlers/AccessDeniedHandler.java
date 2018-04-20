package BE.security.handlers;

import BE.security.SecurityUtils;
import BE.security.enums.AuthenticationFailureType;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding("UTF-8");
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("error", AuthenticationFailureType.INVALID_GRANT);
        jsonResponse.put("error_description", AuthenticationFailureType.INVALID_GRANT);
        httpServletResponse.getWriter().write(jsonResponse.toString());
    }
}

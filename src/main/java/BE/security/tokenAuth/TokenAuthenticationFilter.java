package BE.security.tokenAuth;

import BE.responsemodels.security.TokenHeaderModel;
import BE.exceptions.excludedFromBaseException.AuthenticationException;
import BE.security.enums.AuthenticationFailureType;
import BE.security.SecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filters requests that contain the authorisation header and a suitable token.
 */
public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public TokenAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws org.springframework.security.core.AuthenticationException, IOException, ServletException {

        String auth_token = request.getHeader(SecurityUtils.AUTHORISATION_HEADER);
        String access_token = null;

        if (auth_token != null) access_token = auth_token.replace(SecurityUtils.TOKEN_BEARER_STRING_FOR_APPENDING, "");

        if (access_token != null) return getAuthenticationManager().authenticate(new TokenHeaderModel(null, access_token));

        throw new AuthenticationException("Invalid token.", AuthenticationFailureType.INVALID_GRANT);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }
}
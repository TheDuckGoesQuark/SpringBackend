/*
package BE.security;

import org.springframework.security.core.token.TokenService;
import org.springframework.web.filter.GenericFilterBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RESTAuthenticationTokenProcessingFilter extends GenericFilterBean {

    public RESTAuthenticationTokenProcessingFilter(){}

    public RESTAuthenticationTokenProcessingFilter(UserDetailsService userService, String restUser) {
        this.userService = userService;
        this.REST_USER = restUser;
    }

    @Autowired
    private TokenService tokenService;
    private UserDetailsService userService;
    private String REST_USER;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = getAsHttpRequest(request);

        String authToken = extractAuthTokenFromRequest(httpRequest);
        String[] parts = authToken.split(":");

        if (parts.length == 2) {
            String tokenKey = parts[0];
            String tokenSecret = parts[1];
            if (validateTokenKey(tokenKey)) {
                Token token = tokenService.getTokenByKey(tokenKey);
                List<String> allowedIPs = new Gson().fromJson(token.getAllowedIP(), new TypeToken<ArrayList<String>>() {}.getType());
                if (isAllowIP(allowedIPs, request.getRemoteAddr())) {
                    if (token != null) {
                        if (token.getToken().equals(tokenSecret) && token.getExpired().getTime() > System.currentTimeMillis()) {
                            UserDetails userDetails = userService.loadUserByUsername(REST_USER);
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                            log.info("Authenticated " + token.getKey() + " via IP: " + request.getRemoteAddr());
                            updateLastLogin(token);
                        }
                        else {
                            log.info("Unable to authenticate the token: " + authToken + ". Incorrect secret or token is expired");
                        }
                    }
                }
                else {
                    log.info("Unable to authenticate the token: " + authToken + ". IP - " + request.getRemoteAddr() + " is not allowed");
                }
            }
            else {
                log.info("Unable to authenticate the token: " + authToken + ". Key is broken");
            }
        }

        chain.doFilter(request, response);
    }

    private void updateLastLogin(final Token token) {
        Thread updateTokenShread;
        updateTokenShread = new Thread(new Runnable() {
            public void run() {
                tokenService.updateLastLoginByCurrentDate(token);
            }
        });
        updateTokenShread.setName("RESTTokenThread-" + RandomStringUtils.randomNumeric(4));
        updateTokenShread.start();

    }

    private boolean isAllowIP(List<String> allowedIps, String remoteAddr) {
        for (String allowedIp : allowedIps) {
            if (validateIP(allowedIp, remoteAddr)) {
                return true;
            }
        }
        return false;
    }

    private boolean validateIP(String allowedIp, String remoteAddr) {
        if (allowedIp.contains("/")) {
            return IpMACUtils.isIpInSubnet(remoteAddr, allowedIp);
        }
        else {
            return allowedIp.equals(remoteAddr);
        }
    }

    private boolean validateTokenKey(String tokenKey) {
        String[] parts = tokenKey.split("-");
        return parts.length == 5;
    }

    private HttpServletRequest getAsHttpRequest(ServletRequest request) {
        if (!(request instanceof HttpServletRequest)) {
            throw new RuntimeException("Expecting an HTTP request");
        }

        return (HttpServletRequest) request;
    }


    private String extractAuthTokenFromRequest(HttpServletRequest httpRequest) {
        */
/* Get token from header *//*

        String authToken = httpRequest.getHeader("X-Auth-Token");

		*/
/* If token not found get it from request parameter *//*

        if (authToken == null) {
            authToken = httpRequest.getParameter("token");
        }

        return authToken;
    }


}*/

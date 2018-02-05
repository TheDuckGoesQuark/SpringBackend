package BE.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationSuccessHandler loginSuccessfulHandler;
    private final AuthenticationFailureHandler loginFailureHandler;
    private final AccessDeniedHandler customAccessDeniedHandler;
    private final AuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    public SecurityConfig(AuthenticationSuccessHandler loginSuccessfulHandler, AuthenticationFailureHandler loginFailureHandler, AccessDeniedHandler customAccessDeniedHandler, AuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.loginSuccessfulHandler = loginSuccessfulHandler;
        this.loginFailureHandler = loginFailureHandler;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("admin").password("password").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // disable CSRF for this application
                .formLogin() // Using form based login instead of Basic Authentication
                .loginProcessingUrl("/oauth/token") // Endpoint which will process the authentication request. This is where we will post our credentials to authenticate
                .successHandler(loginSuccessfulHandler)
                .failureHandler(loginFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/token").permitAll() // Enabling URL to be accessed by all users (even un-authenticated)
                .antMatchers("/swagger-ui.html").permitAll()
                 //.antMatchers("/secure/admin").access("hasRole('ADMIN')") // Configures specified URL to be accessed with user having role as ADMIN
                .anyRequest().authenticated() // Any resources not mentioned above needs to be authenticated
                .and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .anonymous().disable(); // Disables anonymous authentication with anonymous role.
    }
}

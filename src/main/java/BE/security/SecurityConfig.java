package BE.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
                .loginProcessingUrl("/auth/token") // Endpoint which will process the authentication request. This is where we will post our credentials to authenticate
                .successHandler(loginSuccessfulHandler)
                .failureHandler(loginFailureHandler)
                .and()
                .logout()
                .logoutUrl("/auth/logout") // Configures the URL to logout from application
                .logoutSuccessHandler(logoutSuccessfulHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/login").permitAll() // Enabling URL to be accessed by all users (even un-authenticated)
                .antMatchers("/secure/admin").access("hasRole('ADMIN')") // Configures specified URL to be accessed with user having role as ADMIN
                .anyRequest().authenticated() // Any resources not mentioned above needs to be authenticated
                .and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .anonymous().disable(); // Disables anonymous authentication with anonymous role.
    }
}

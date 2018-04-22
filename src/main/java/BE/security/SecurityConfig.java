package BE.security;

import BE.security.passwordAuth.FormAuthenticationFilter;
import BE.security.passwordAuth.FormAuthenticationProvider;
import BE.security.tokenAuth.TokenAuthenticationFilter;
import BE.security.tokenAuth.TokenAuthenticationProvider;
import BE.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedHandler customAccessDeniedHandler;
    @Autowired
    private AuthenticationEntryPoint customAuthenticationEntryPoint;
    @Autowired
    private UserDetailsService userService;
    @Autowired
    private TokenService tokenService;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DataSource datasource;
    @Autowired
    private FormAuthenticationProvider formAuthenticationProvider;
    @Autowired
    private TokenAuthenticationProvider tokenAuthenticationProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    protected FormAuthenticationFilter getFormAuthenticationFilter(String pattern) {
        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter(new AntPathRequestMatcher(pattern), tokenService);
        formAuthenticationFilter.setAuthenticationManager(this.authenticationManager);
        return formAuthenticationFilter;
    }

    protected TokenAuthenticationFilter getTokenAuthenticationFilter(String pattern) {
        TokenAuthenticationFilter tokenAuthenticationFilter = new TokenAuthenticationFilter(new AntPathRequestMatcher(pattern));
        tokenAuthenticationFilter.setAuthenticationManager(this.authenticationManager);
        return tokenAuthenticationFilter;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(formAuthenticationProvider);
        auth.authenticationProvider(tokenAuthenticationProvider)
                .userDetailsService(userService)
                .and()
                .jdbcAuthentication().dataSource(datasource);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**")
        .and().ignoring().regexMatchers(HttpMethod.POST, "(\\/users\\/)([^\\/]+)(\\?action=create)");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                 .csrf().disable() // disable CSRF for this application
                .formLogin() // Using form based login instead of Basic Authentication
                    .loginProcessingUrl("/oauth/token") // Endpoint which will process the authentication request. This is where we will post our credentials to authenticate
                .and()
                    .authorizeRequests()
                    .antMatchers("/oauth/token").permitAll() // Enabling URL to be accessed by all users (even un-authenticated)
                .and()
                    .authorizeRequests()
                    .antMatchers("/swagger-ui.html").permitAll()
                .and()
                        .authorizeRequests()
                        //.antMatchers("/secure/admin").access("hasRole('ADMIN')") // Configures specified URL to be accessed with user having role as ADMIN
                        .anyRequest().authenticated() // Any resources not mentioned above needs to be authenticated
                    .and()
                        .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .and()
                        .addFilterBefore(getFormAuthenticationFilter("/oauth/token"), UsernamePasswordAuthenticationFilter.class)
                        .addFilterBefore(getTokenAuthenticationFilter("/**"), UsernamePasswordAuthenticationFilter.class);
    }
}

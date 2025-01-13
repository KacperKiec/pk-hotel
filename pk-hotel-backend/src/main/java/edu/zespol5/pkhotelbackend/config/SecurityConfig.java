package edu.zespol5.pkhotelbackend.config;

import edu.zespol5.pkhotelbackend.config.custom.AuthenticationFailureHandler;
import edu.zespol5.pkhotelbackend.config.custom.LoginSuccessHandler;
import edu.zespol5.pkhotelbackend.config.custom.LogoutSuccessHandler;
import edu.zespol5.pkhotelbackend.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration class for the application.
 * <p>
 * This class configures authentication and authorization mechanisms using Spring Security.
 * It defines beans for user authentication, password encoding, and the security filter chain.
 * </p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final LoginSuccessHandler loginSuccessHandler;
    private final LogoutSuccessHandler logoutSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * Constructor for {@code SecurityConfig}.
     *
     * @param loginSuccessHandler      handler for successful login events
     * @param logoutSuccessHandler     handler for successful logout events
     * @param authenticationFailureHandler handler for failed authentication attempts
     */
    public SecurityConfig(LoginSuccessHandler loginSuccessHandler, LogoutSuccessHandler logoutSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler) {
        this.loginSuccessHandler = loginSuccessHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    /**
     * Creates a {@link UserDetailsService} bean that uses the {@link UserService} implementation.
     *
     * @param userService the user service to retrieve user details
     * @return a {@link UserDetailsService} implementation
     */
    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return userService;
    }

    /**
     * Creates an {@link AuthenticationProvider} bean configured with a DAO-based user details service
     * and a password encoder.
     *
     * @param userDetailsService the service used to retrieve user details
     * @return a {@link DaoAuthenticationProvider} instance
     */
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(encoder());
        return provider;
    }

    /**
     * Configures the security filter chain for the application.
     * <p>
     * This method sets up:
     * <ul>
     *     <li>CORS and CSRF policies</li>
     *     <li>Form-based login and logout handling</li>
     *     <li>Authorization rules for various URL patterns</li>
     *     <li>Custom handlers for authentication success, failure, and logout events</li>
     * </ul>
     * </p>
     *
     * @param http the {@link HttpSecurity} object used to configure security settings
     * @return a configured {@link SecurityFilterChain} instance
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .formLogin(httpForm -> httpForm
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(loginSuccessHandler)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(logoutSuccessHandler)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/user/**").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/reservation/**").authenticated()
                        .requestMatchers("/hotels/review").authenticated()
                        .anyRequest().permitAll())
                .exceptionHandling(handler -> handler
                        .authenticationEntryPoint(authenticationFailureHandler))
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    /**
     * Creates a {@link PasswordEncoder} bean for encoding and validating passwords.
     * This implementation uses BCrypt for hashing passwords.
     *
     * @return a {@link BCryptPasswordEncoder} instance
     */
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}

package br.com.laghettohoteis.api_biglietto.infra.configurations;

import br.com.laghettohoteis.api_biglietto.infra.filters.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->  authorize
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/auth/create").hasRole("ADMIN_MASTER")
                        .requestMatchers(HttpMethod.POST, "/api/auth/init").permitAll()

                        .requestMatchers(HttpMethod.GET,"/api/hotel/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/hotel/**").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/api/hotel/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/api/hotel/**").permitAll()

                        .requestMatchers(HttpMethod.GET,"api/type/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"api/type/**").hasRole("ADMIN_HOTEL")
                        .requestMatchers(HttpMethod.PUT,"api/type/**").hasRole("ADMIN_HOTEL")
                        .requestMatchers(HttpMethod.DELETE,"api/type/**").hasRole("ADMIN_HOTEL")

                        .anyRequest().authenticated());



        return http.addFilterBefore(this.securityFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
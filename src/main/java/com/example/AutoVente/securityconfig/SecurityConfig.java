package com.example.AutoVente.securityconfig;

import com.example.AutoVente.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Pages publiques
                        .requestMatchers("/", "/accueil", "/recherche", "/voitures/**",
                                "/login", "/register", "/css/**", "/js/**",
                                "/images/**", "/webjars/**").permitAll()
                        // Pages Acheteur
                        .requestMatchers("/annonces/**", "/comparateur/**").hasAnyRole("ACHETEUR", "VENDEUR", "MODERATEUR", "ADMIN")
                        // Pages Vendeur
                        .requestMatchers("/annonces/nouvelle", "/annonces/modifier/**",
                                "/annonces/supprimer/**", "/mes-annonces").hasAnyRole("VENDEUR", "ADMIN")
                        // Pages Modérateur
                        .requestMatchers("/moderation/**").hasAnyRole("MODERATEUR", "ADMIN")
                        // Pages Admin
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .rememberMe(remember -> remember
                        .key("autoventeSecretKey")
                        .tokenValiditySeconds(86400 * 7) // 7 jours
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        var builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userService).passwordEncoder(passwordEncoder());
        return builder.build();
    }
}
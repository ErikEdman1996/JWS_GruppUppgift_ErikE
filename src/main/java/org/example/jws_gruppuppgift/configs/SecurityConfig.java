package org.example.jws_gruppuppgift.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig
{
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.withUsername("Erik")
                .password("{noop}Edman")
                .roles("USER")
                .build();

        UserDetails user2 = User.withUsername("Jocelyn")
                .password("{noop}CarrilloCampos")
                .roles("USER")
                .build();

        UserDetails user3 = User.withUsername("Mohamed")
                .password("{noop}Sharshar")
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("Hugo")
                .password("{noop}Ransvi")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1, user2, user3, admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/wigelltravels/v1/listcanceled").hasRole("ADMIN")
                        .requestMatchers("/api/wigelltravels/v1/listupcoming").hasRole("ADMIN")
                        .requestMatchers("/api/wigelltravels/v1/listpast").hasRole("ADMIN")
                        .requestMatchers("/api/wigelltravels/v1/addtravel").hasRole("ADMIN")
                        .requestMatchers("/api/wigelltravels/v1/updatetravel").hasRole("ADMIN")
                        .requestMatchers("/api/wigelltravels/v1/removetravel/**").hasRole("ADMIN")
                        .requestMatchers("/api/wigelltravels/v1/travels").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/api/wigelltravels/v1/booktrip").hasRole("USER")
                        .requestMatchers("/api/wigelltravels/v1/canceltrip").hasRole("USER")
                        .requestMatchers("/api/wigelltravels/v1/mybookings").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}

package com.unibuc.newsapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers("/api/users/**").permitAll()
                .requestMatchers("/api/articles/create").hasAnyRole( "ADMIN")
                .requestMatchers("/api/articles/update/**").hasAnyRole( "ADMIN")
                .requestMatchers("/api/articles/delete/**").hasAnyRole( "ADMIN")
                .requestMatchers("/api/categories/add").hasAnyRole( "ADMIN")
                .requestMatchers("/api/categories/update/**").hasAnyRole( "ADMIN")
                .requestMatchers("/api/categories/delete/**").hasAnyRole( "ADMIN")
                .requestMatchers("/api/articles/**").permitAll()
                .requestMatchers("/api/categories/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}

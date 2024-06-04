package com.src.actors.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // disable CSRF (cross site request forgery) for easier testing
    return http.authorizeHttpRequests(request -> request.anyRequest().authenticated()).csrf(csrf -> csrf.disable())
        .formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults()).build();
  }
}

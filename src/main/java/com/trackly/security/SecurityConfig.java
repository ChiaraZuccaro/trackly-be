package com.trackly.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

  private List<String> allowedOrigins;
  private List<String> allowedMethods;
  private List<String> noTokenCall;
  
  private final OAuth2SuccessHandler oAuth2SuccessHandler;

  public SecurityConfig(OAuth2SuccessHandler oAuth2SuccessHandler) {
    this.oAuth2SuccessHandler = oAuth2SuccessHandler;
    this.allowedOrigins = List.of("http://localhost:1008");
    this.allowedMethods = List.of("GET", "POST", "PUT", "DELETE", "OPTIONS");
    this.noTokenCall = List.of(
      "/trackly/auth/login",
      "/trackly/auth/register",
      "/trackly/auth/password/recover",
      "/trackly/auth/password/reset"
    );
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(allowedOrigins);
    config.setAllowedMethods(allowedMethods);
    config.setAllowCredentials(true);
    config.setAllowedHeaders(List.of("*"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
    http.csrf(csrf -> csrf.disable())
      .cors(cors -> cors.configurationSource(corsConfigurationSource()))
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(this.noTokenCall.toArray(new String[0])).permitAll()
        .anyRequest().authenticated()
      )
      .oauth2Login(oauth2 -> oauth2.successHandler(oAuth2SuccessHandler))
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}

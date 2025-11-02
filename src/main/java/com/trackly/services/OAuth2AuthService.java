package com.trackly.services;

import org.springframework.stereotype.Service;

import com.trackly.models.User;
import com.trackly.repositories.UserRepository;
import com.trackly.security.JwtService;

@Service
public class OAuth2AuthService {

  private final UserRepository userRepository;
  private final JwtService jwtService;

  public OAuth2AuthService(UserRepository userRepository, JwtService jwtService) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
  }

  public String findOrCreateAndGenerateToken(String email, String username) {
    User user = userRepository.findByEmail(email).orElseGet(() -> {
      User newUser = new User();
      newUser.setEmail(email);
      newUser.setUsername(username);
      newUser.setPassword(""); // OAuth2, quindi password non usata
      return userRepository.save(newUser);
    });

    return jwtService.generateToken(user.getUsername());
  }
}

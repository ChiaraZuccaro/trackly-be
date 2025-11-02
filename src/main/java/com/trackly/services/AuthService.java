package com.trackly.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trackly.dtos.LoginUserDto;
import com.trackly.dtos.RegisterUserDto;
import com.trackly.models.PasswordResetToken;
import com.trackly.models.User;
import com.trackly.enums.RespCode;
import com.trackly.repositories.PasswordResetTokenRepository;
import com.trackly.repositories.UserRepository;
import com.trackly.security.JwtService;
import com.trackly.utils.CodeException;

@Service
public class AuthService {

  @Autowired
  private PasswordResetTokenRepository passwordResetTokenRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtService jwtService;

  public void registerUser(RegisterUserDto req) {
    if (userRepository.existsByEmail(req.getEmail())) {
      throw new CodeException(RespCode.CONFLICT);
    }

    if (req.getUsername() == null) {
      throw new CodeException(RespCode.BAD_REQUEST);
    }

    User user = new User();
    user.setEmail(req.getEmail());
    user.setPassword(passwordEncoder.encode(req.getPassword()));
    user.setUsername(req.getUsername());

    userRepository.save(user);
  }

  public String loginUser(LoginUserDto req) {
    User user = userRepository.findByEmail(req.getEmail())
        .orElseThrow(() -> new CodeException(RespCode.NOT_FOUND));

    boolean passwordValid = passwordEncoder.matches(req.getPassword(), user.getPassword());
    if (!passwordValid) {
      throw new CodeException(RespCode.UNAUTHORIZED);
    }

    return jwtService.generateToken(user.getUsername());
  }

  public String createPasswordResetToken(String email) {
    User user = userRepository.findByEmail(email)
      .orElseThrow(() -> new CodeException(RespCode.NOT_FOUND));

    String token = UUID.randomUUID().toString();

    PasswordResetToken resetToken = new PasswordResetToken();
    resetToken.setToken(token);
    resetToken.setUser(user);
    resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));

    passwordResetTokenRepository.save(resetToken);

    return token;
  }

  public void resetPassword(String token, String newPassword) {
    PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
        .orElseThrow(() -> new CodeException(RespCode.NOT_FOUND));

    if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
      throw new CodeException(RespCode.UNAUTHORIZED); // token scaduto
    }

    User user = resetToken.getUser();
    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);

    // invalida il token
    passwordResetTokenRepository.delete(resetToken);
  }

}

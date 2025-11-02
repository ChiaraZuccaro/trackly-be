package com.trackly.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trackly.models.PasswordResetToken;
import com.trackly.models.User;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
  Optional<PasswordResetToken> findByToken(String token);

  Optional<PasswordResetToken> findByUser(User user);
}

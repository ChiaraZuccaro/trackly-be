package com.trackly.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_token", indexes = {
  @Index(name = "idx_prt_token", columnList = "token", unique = true)
})
public class PasswordResetToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 200)
  private String token;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "expiry_date", nullable = false)
  private LocalDateTime expiryDate;

  public PasswordResetToken() {}

  public Long getId() { return id; }
  public String getToken() { return token; }
  public User getUser() { return user; }
  public LocalDateTime getExpiryDate() { return expiryDate; }

  public void setToken(String token) { this.token = token; }
  public void setUser(User user) { this.user = user; }
  public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }
}

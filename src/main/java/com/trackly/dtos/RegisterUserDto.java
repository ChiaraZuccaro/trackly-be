package com.trackly.dtos;

public class RegisterUserDto extends LoginUserDto {
  private String username;

  public RegisterUserDto() { super(); }

  // Getter e Setter
  public String getUsername() { return username; }
  public void setUsername(String name) { this.username = name; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }
}

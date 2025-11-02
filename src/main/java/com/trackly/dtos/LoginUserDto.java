package com.trackly.dtos;

public class LoginUserDto {
  protected String email;
  protected String password;

  // public LoginUserDto() {}
  // public LoginUserDto(String name, String email, String password) {
  //   this.name = name;
  //   this.email = email;
  //   this.password = password;
  // }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }

}

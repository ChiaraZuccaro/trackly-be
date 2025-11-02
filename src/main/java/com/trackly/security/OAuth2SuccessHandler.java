package com.trackly.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.trackly.services.OAuth2AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
  private final OAuth2AuthService oAuth2AuthService;

  public OAuth2SuccessHandler(OAuth2AuthService oAuth2Auth) {
    this.oAuth2AuthService = oAuth2Auth;
  }

  @Override
  public void onAuthenticationSuccess(
    HttpServletRequest request,
    HttpServletResponse response,
    Authentication authentication
  ) throws IOException {

    OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
    String email = oauthUser.getAttribute("email");
    String name = oauthUser.getAttribute("name");

    String jwt = oAuth2AuthService.findOrCreateAndGenerateToken(email, name);

    int tokenAge = 24 * 60 * 60;
    jakarta.servlet.http.Cookie jwtCookie = new jakarta.servlet.http.Cookie("jwt", jwt);

    jwtCookie.setHttpOnly(true);
    jwtCookie.setSecure(false);
    jwtCookie.setPath("/");
    jwtCookie.setMaxAge(tokenAge);
    jwtCookie.setAttribute("Samesite", "None");

    response.addCookie(jwtCookie);
    // TODO frontend path
    response.sendRedirect("/login-success");
  }
}

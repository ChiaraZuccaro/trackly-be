package com.trackly.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trackly.dtos.LoginUserDto;
import com.trackly.dtos.RegisterUserDto;
import com.trackly.enums.RespCode;
import com.trackly.services.AuthService;
import com.trackly.utils.CodeException;
import com.trackly.utils.ApiResp;
import com.trackly.utils.Messages;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  private Cookie createTokenCookie(String token) {
    int tokenAge = 24 * 60 * 60; // 1 day
    Cookie jwtCookie = new Cookie("jwt", token);
    jwtCookie.setHttpOnly(true);
    jwtCookie.setSecure(false);
    jwtCookie.setPath("/");
    jwtCookie.setMaxAge(tokenAge);
    jwtCookie.setAttribute("Samesite", "none");

    return jwtCookie;
  }

  private String getMessage(RespCode code, String type) {
    String fallbackMsg = "Errore di fallback";
    switch (type) {
      case "register":
        return Messages.RegisterMessages.getOrDefault(code, fallbackMsg);
      case "login":
        return Messages.LoginMessages.getOrDefault(code, fallbackMsg);
      default:
        return fallbackMsg;
    }
  }

  @PostMapping("/register")
  public ResponseEntity<ApiResp> register(@RequestBody RegisterUserDto request) {
    String typeMsgs = "register";
    try {
      authService.registerUser(request);

      String msg = getMessage(RespCode.OK, typeMsgs);
      return new ApiResp(RespCode.OK, msg).send();

    } catch (CodeException e) {

      String msg = getMessage(e.getCode(), typeMsgs);
      return new ApiResp(e.getCode(), msg, true).send();
    } catch (Exception e) {

      String msg = getMessage(RespCode.INTERNAL_SERVER_ERROR, typeMsgs);
      return new ApiResp(RespCode.INTERNAL_SERVER_ERROR, msg, true).send();
    }
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResp> login(@RequestBody LoginUserDto request, HttpServletResponse response) {
    String typeMsgs = "login";
    try {
      String token = authService.loginUser(request);

      Cookie jwtCookie = createTokenCookie(token);
      response.addCookie(jwtCookie);

      String msg = getMessage(RespCode.OK, typeMsgs);
      return new ApiResp(RespCode.OK, msg).send();

    } catch (CodeException e) {

      String msg = getMessage(e.getCode(), typeMsgs);
      return new ApiResp(e.getCode(), msg, true).send();
    } catch (Exception e) {

      String msg = getMessage(RespCode.INTERNAL_SERVER_ERROR, typeMsgs);
      return new ApiResp(RespCode.INTERNAL_SERVER_ERROR, msg, true).send();
    }
  }

  @PostMapping("/password/recover")
  public ResponseEntity<ApiResp> recoverPassword(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    String typeMsgs = "login";

    try {
      authService.createPasswordResetToken(email);

      String msg = "Email inviata con istruzioni per il reset.";
      return new ApiResp(RespCode.OK, msg).send();

    } catch (CodeException e) {
      String msg = getMessage(e.getCode(), typeMsgs);
      return new ApiResp(e.getCode(), msg, true).send();
    } catch (Exception e) {
      String msg = getMessage(RespCode.INTERNAL_SERVER_ERROR, typeMsgs);
      return new ApiResp(RespCode.INTERNAL_SERVER_ERROR, msg, true).send();
    }
  }

  @PostMapping("/password/reset")
  public ResponseEntity<ApiResp> resetPassword(@RequestBody Map<String, String> body) {
    String token = body.get("token");
    String newPassword = body.get("password");
    String typeMsgs = "login";

    try {
      authService.resetPassword(token, newPassword);

      String msg = "Password reimpostata con successo!";
      return new ApiResp(RespCode.OK, msg).send();

    } catch (CodeException e) {
      String msg = getMessage(e.getCode(), typeMsgs);
      return new ApiResp(e.getCode(), msg, true).send();
    } catch (Exception e) {
      String msg = getMessage(RespCode.INTERNAL_SERVER_ERROR, typeMsgs);
      return new ApiResp(RespCode.INTERNAL_SERVER_ERROR, msg, true).send();
    }
  }

}
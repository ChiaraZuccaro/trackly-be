package com.trackly.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

  // Segreto per firmare il token (da sostituire con uno più sicuro e non
  // hardcodato)
  private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  // Durata del token (es. 1 ora)
  private final long expirationMs = 3600000;

  // Genera un token JWT
  public String generateToken(String username) {
    return Jwts.builder()
      .setSubject(username)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
      .signWith(key)
      .compact();
  }

  // Estrae il username dal token
  public String extractUsername(String token) {
    return getClaims(token).getSubject();
  }

  // Controlla se il token è valido
  public boolean isTokenValid(String token, String username) {
    String tokenUsername = extractUsername(token);
    return tokenUsername.equals(username) && !isTokenExpired(token);
  }

  // Controlla se il token è scaduto
  private boolean isTokenExpired(String token) {
    return getClaims(token).getExpiration().before(new Date());
  }

  // Estrae i claims
  private Claims getClaims(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(key)
      .build()
      .parseClaimsJws(token)
      .getBody();
  }
}

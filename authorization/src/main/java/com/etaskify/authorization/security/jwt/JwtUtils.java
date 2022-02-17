package com.etaskify.authorization.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {

  @Value("${token.expiration}")
  private Long expiration;

  @Value("${token.secret}")
  private String secret;

  public String extractUsername(@NonNull String token){
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(@NonNull String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private <T> T extractClaim(@NonNull String token, @NonNull Function<Claims,T> claimResolver) {
    Claims claims = extractAllClaims(token);
    return claimResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Boolean validateToken(String token) {
    try {
      String username = extractUsername(token);
      return (extractUsername(token).equals(username) && !isTokenExpired(token));
    } catch (SignatureException e) {
      e.printStackTrace();
      return false;
    }
  }

  public String generateToken(UserDetails userDetails){
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userDetails.getUsername());
  }

  private String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
  }

}

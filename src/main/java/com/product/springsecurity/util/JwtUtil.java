package com.product.springsecurity.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
 private static final String  SECRET="hb3J1ZW9idTQzNW5zdGF1In0Khb3J1ZW9idTQzNW5zdGF1In0Khb3J1ZW9idTQzNW5zdGF1In0Khb3J1ZW9idTQzNW5zdGF1In0Khb3J1ZW9idTQzNW5zdGF1In0Khb3J1ZW9idTQzNW5zdGF1In0Khb3J1ZW9idTQzNW5zdGF1In0Khb3J1ZW9idTQzNW5zdGF1In0K";

 public String extractUsername(String token) {
     return extractClaims(token, Claims::getSubject);
 }
 public Date extractExpiration(String token) {
     return extractClaims(token, Claims::getExpiration);
 }

 public Claims extractAllClaims(String token) {
     return Jwts
             .parserBuilder()
             .setSigningKey(getSignKey())
             .build()
             .parseClaimsJws(token)
             .getBody();
 }
 public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
     final Claims claims = extractAllClaims(token);
      return claimsResolver.apply(claims);
 }

 public String createToken(Map<String,Object> claims,String subject) {
     return Jwts.builder()
             .setSubject(subject)
             .setIssuedAt(new Date(System.currentTimeMillis()))
             .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
             .signWith(SignatureAlgorithm.HS256,getSignKey())
             .compact();
 }

 public String generateToken(String username) {
     Map<String, Object> claims = new HashMap<>();
     return createToken(claims, username);
 }

 public Boolean validateToken(String token, UserDetails userDetails) {
     final String username = this.extractUsername(token);
     return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
 }


 public Boolean isTokenExpired(String token) {
     return extractExpiration(token).before(new Date());
 }


 public Key getSignKey() {
     byte[] keyBytes= Decoders.BASE64.decode(SECRET);
     return Keys.hmacShaKeyFor(keyBytes);
 }










}

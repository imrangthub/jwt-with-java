package com.madbarsoft;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTGenerateValidateHMAC {

	public static void main(String[] args) {
		
		Instant now = Instant.now();
		String secret = "my-super-sec-balabalalbalabalabalabalabalabalbalab";


		String jwtToken = Jwts.builder()
				.claim("name", "MD IMRAN HOSSAIN")
				.claim("email", "imranmadbar@gamil.com")
				.setSubject("test-jwt")
                .claim("role", "admin")
				.setId(UUID.randomUUID().toString()).setIssuedAt(Date.from(now))
				.setExpiration(Date.from(now.plus(5l, ChronoUnit.MINUTES)))
				.signWith(SignatureAlgorithm.HS256, secret.getBytes())
				.compact();

		System.out.println("Token: " + jwtToken);

		Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(jwtToken);

		Claims claims = claimsJws.getBody();
		System.out.println("\nDecoded Claims:");
		System.out.println("Subject: " + claims.getSubject());
		System.out.println("Issued At: " + claims.getIssuedAt());
		System.out.println("Expiration: " + claims.getExpiration());
		System.out.println("Role: " + claims.get("role"));

	}

}

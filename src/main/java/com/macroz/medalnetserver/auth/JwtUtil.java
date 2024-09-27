package com.macroz.medalnetserver.auth;

import com.macroz.medalnetserver.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {


	private final SecretKey secretKey = Keys.hmacShaKeyFor("ultraTopSecretKey:)AtLeast32CharactersLong".getBytes());
	private final long accessTokenValidity = 60*24*30;	// in minutes, now equals 30 days

	private final JwtParser jwtParser;

	private final String TOKEN_HEADER = "Authorization";
	private final String TOKEN_PREFIX = "Bearer ";

	public JwtUtil(){
		this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
	}

	public String createToken(User user) {
		Claims claims = Jwts.claims()
				.subject(user.getEmail())
				.add("username", user.getUsername())
				.build();
		Date tokenCreateTime = new Date();
		Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));
		return Jwts.builder()
				.claims(claims)
				.expiration(tokenValidity)
				.signWith(secretKey, Jwts.SIG.HS256)
				.compact();
	}

	private Claims parseJwtClaims(String token) {
		return jwtParser.parseSignedClaims(token).getPayload();
	}

	public Claims resolveClaims(HttpServletRequest req) {
		try {
			String token = resolveToken(req);
			if (token != null) {
				return parseJwtClaims(token);
			}
			return null;
		} catch (ExpiredJwtException ex) {
			req.setAttribute("expired", ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			req.setAttribute("invalid", ex.getMessage());
			throw ex;
		}
	}

	public String resolveToken(HttpServletRequest request) {

		String bearerToken = request.getHeader(TOKEN_HEADER);
		if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
			return bearerToken.substring(TOKEN_PREFIX.length());
		}
		return null;
	}

	public String resolveToken(HttpHeaders headers) {
		String bearerToken = headers.getFirst(TOKEN_HEADER);
		if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
			return bearerToken.substring(TOKEN_PREFIX.length());
		}
		return null;
	}

	public boolean validateClaims(Claims claims) throws AuthenticationException {
		try {
			return claims.getExpiration().after(new Date());
		} catch (Exception e) {
			throw e;
		}
	}

	public String getEmail(Claims claims) {
		return claims.getSubject();
	}

	public String getUsername(Claims claims) {
		return (String) claims.get("username");
	}

	public String getUsernameFromToken(String accessToken) {
		Claims claims = parseJwtClaims(accessToken);
		return getUsername(claims);
	}
}
package com.restaurant.util;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
//	public static final String SECRET ="DIzrYOpgWjeggSQvgis322WpedEgqfynpigkbkkfhSQ=";
	
	public String generateToken(UserDetails userDetail) {
		return generateToken(new HashMap<>(),userDetail);
	}
	private String generateToken(Map<String,Object> extraClaims,UserDetails userDetails) {
		
		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
				.signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
	}
	private Key getSignKey() {
		// TODO Auto-generated method stub
		byte[] keyBytes=Decoders.BASE64.decode("DIzrYOpgWjeggSQvgis322WpedEgqfynpigkbkkfhSQ");
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        System.out.println("im in istokenvalid"+token);
//     

        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
	
	public String extractUsername(String token) {
		System.out.println("im in extractUsername "+token);
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token,Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public interface ClaimsResolver<T> {
        T apply(Claims claims);
    }
	
}

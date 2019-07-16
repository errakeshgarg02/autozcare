package com.autozcare.main.security;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.autozcare.main.exception.AutozcareException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {
	
	@Value("${autozcare.jwtSecret:JWTSuperSecretKey}")
    private String jwtSecret;

    @Value("${autozcare.jwtExpirationInMs:604800000}")
    private int jwtExpirationInMs;
    
	@Autowired
	private AuthenticationManager authenticationManager;

    public String generateToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        List<String> roles = userPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        
        return Jwts.builder()
                .setSubject(userPrincipal.getUser().getMobileNumber())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .claim("role", roles)
                .compact();
    }

    public String getMobileNumberFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
        	log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
        	log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
        	log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
        	log.error("JWT claims string is empty.");
        }
        return false;
    }
    
    public Optional<String> authenticateUserAndGenerateToken(String userId, Object password) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userId, password));
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		
		if(!userPrincipal.getUser().isValidOtp()) {
			throw new AutozcareException(HttpStatus.FORBIDDEN, "Please verify your mobile number with OTP!");
		}
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return Optional.ofNullable(this.generateToken(authentication));

    }
}

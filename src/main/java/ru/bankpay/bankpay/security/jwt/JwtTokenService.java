package ru.bankpay.bankpay.security.jwt;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenService {

    @Value("${jwt.secret.access}")
    private String accessSecret;

    @Value("${jwt.ttl.second}")
    private Integer jwtTokenTtl;

    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    protected void init() {
        accessSecret = Base64.getEncoder().encodeToString(accessSecret.getBytes());
    }

    public String createToken(String username) {

        Claims claims = Jwts.claims().setSubject(username).build();

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtTokenTtl);

        return Jwts.builder()//
            .setClaims(claims)//
            .setIssuedAt(now)//
            .setExpiration(validity)//
            .signWith(SignatureAlgorithm.HS256, accessSecret)//
            .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
            .verifyWith(signKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(signKey())
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException exception) {
            log.info("JWT token expired", exception);
        } catch (UnsupportedJwtException exception) {
            log.info("Unsupported JWT token", exception);
        } catch (MalformedJwtException exception) {
            log.info("Malformed JWT token", exception);
        } catch (Exception exception) {
            log.info("Failed validate JWT token", exception);
        }
        return false;
    }

    private SecretKey signKey() {
        byte[] decodedKey = Base64.getDecoder().decode(accessSecret);
        return new SecretKeySpec(decodedKey, SignatureAlgorithm.HS512.getJcaName());
    }
}

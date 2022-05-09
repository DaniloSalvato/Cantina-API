package com.pb.ProjetoGrupo2.config.security;

import com.pb.ProjetoGrupo2.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${cantina.jwt.expiration}")
    private String expiration;

    @Value("${cantina.jwt.secret}")
    private String secret;


    public String generateToken(Authentication authentication){
        User on = (User) authentication.getPrincipal();
        Date today = new Date();
        Date expirationDate = new Date(today.getTime()+  Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("API da cantina")
                .setSubject(on.getId().toString())
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public Long getIdUser(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }
}

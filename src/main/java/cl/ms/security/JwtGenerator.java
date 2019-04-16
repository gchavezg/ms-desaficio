package cl.ms.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Component;

import cl.ms.model.UsuarioModel;

@Component
public class JwtGenerator {

    public String generateToken(UsuarioModel jwtUser) {

        Claims claims = Jwts.claims()
                .setSubject(jwtUser.getName())
                .setExpiration(new LocalDateTime().now().plusMinutes(1).toDate());
        claims.put("id", String.valueOf(jwtUser.getUsuCod().toString()));
        claims.put("email", jwtUser.getEmail());


        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "GeraldChavez")
                .compact();
    }
}

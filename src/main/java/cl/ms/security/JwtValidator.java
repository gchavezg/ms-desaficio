package cl.ms.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import cl.ms.exception.ErrorDto;
import cl.ms.exception.ErrorNegocioException;
import cl.ms.model.JwtUser;

@Component
public class JwtValidator {


    private String secret = "GeraldChavez";

    public JwtUser validate(String token) {

        JwtUser jwtUser = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            jwtUser = new JwtUser();

            jwtUser.setUserName(body.getSubject());
            jwtUser.setId((String) body.get("id"));
            jwtUser.setRole((String) body.get("email"));
        }catch (ExpiredJwtException wee) {
        	throw new ErrorNegocioException("Sesión inválida");
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return jwtUser;
    }
}

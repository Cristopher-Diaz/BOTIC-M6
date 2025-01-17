package com.evaluacionFinal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.evaluacionFinal.model.Role;

import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    private SecretKey key;

    @PostConstruct
    protected void init() {
        // Genera la clave secreta basada en la configuración
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // Genera un token JWT
    public String createToken(String username, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);

        // Convierte los roles en una lista de cadenas (si Role no es un String)
        claims.put("roles", roles.stream().map(Role::toString).toList());

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds); // Configura el tiempo de expiración

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now) // Fecha de emisión
                .setExpiration(validity) // Fecha de expiración
                .signWith(key, SignatureAlgorithm.HS256) // Firma el token
                .compact();
    }
    
    // Valida un token JWT
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            System.out.println("Token expirado: " + e.getMessage());
        } catch (io.jsonwebtoken.JwtException e) {
            System.out.println("Token inválido: " + e.getMessage());
        }
        return false;
    }


    // Extrae el nombre de usuario (username) del token JWT
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    
    public Authentication getAuthentication(String token) {
        String username = getUsername(token);

        // Extraer roles del token
        List<String> roles = getRoles(token);

        // Convertir los roles a GrantedAuthority
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // Crear UserDetails
        UserDetails userDetails = new User(username, "", authorities);

        // Crear Authentication
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    // Método auxiliar para extraer roles del token
    private List<String> getRoles(String token) {
        return (List<String>) Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles", List.class);
    }
}

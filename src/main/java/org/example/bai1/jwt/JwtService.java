package org.example.bai1.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.example.bai1.model.entity.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expiredAccessTime}")
    private long expiredAccessTime;

    @Value("${jwt.expiredRefreshTime}")
    private long expiredRefreshTime;

    public String generateAccessToken(Employee employee) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        List<String> roles = List.of(employee.getRole().getRoleName());

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiredAccessTime);

        return Jwts.builder().setClaims(claims).setSubject(employee.getUsername()).setIssuedAt(now).setExpiration(expiryDate).signWith(key, SignatureAlgorithm.HS256).compact();
    }

    public String generateRefreshToken(Employee employee) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiredRefreshTime);

        return Jwts.builder().setSubject(employee.getUsername()).setIssuedAt(now).setExpiration(expiryDate).signWith(key, SignatureAlgorithm.HS256).compact();
    }
}
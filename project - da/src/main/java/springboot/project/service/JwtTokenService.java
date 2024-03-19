package springboot.project.service;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@Slf4j
public class JwtTokenService {

    private final Key secretKey;
    private final Key refeshKey;
    @Value(("${jwt.validity}"))
    private long validityInMilliseconds;

    @Autowired
    UserPrincipalService userPrincipalService;

    public JwtTokenService(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        this.refeshKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    // Tạo JWT từ thông tin của User
    public String createToken(String email) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        // Tạo chuỗi JWT từ email
        String accessToken = Jwts.builder().setSubject(email)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return accessToken;
    }

    public String createRefreshToken(String email) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        // Tạo chuỗi JWT từ email
        String accessToken = Jwts.builder().setSubject(email)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(refeshKey, SignatureAlgorithm.HS256)
                .compact();

        return accessToken;
    }

    // Lấy thông tin User từ JWT
    public String getEmailFromJwt(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Kiểm tra thời hạn của token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT Token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT Token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT Token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims String is empty");
        }
        return false;
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parser().setSigningKey(refeshKey).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT Token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT Token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT Token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims String is empty");
        }
        return false;
    }
    public Authentication getAuthentication(String token){
        // Lấy email từ DB sau đó convert về UserDetails và set quyền cho Security (tạo ra một đăng nhập)
        UserDetails userDetails = userPrincipalService.loadUserByUsername(getEmailFromJwt(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }
}
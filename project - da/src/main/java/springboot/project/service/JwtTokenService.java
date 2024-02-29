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

@Service
@Slf4j
public class JwtTokenService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value(("${jwt.validity}"))
    private long validityInMilliseconds;

    @Autowired
    UserPrincipalService userPrincipalService;


    // tạo jwt từ thông tin của User
    public String createToken(String email) {

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        // tạo chuỗi jwt từ email
        String accesstoken = Jwts.builder().setSubject(email)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return accesstoken;
    }

    // lấy thông tin User từ jwt
    public String getEmailFromJwt(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // kiểm tra thời hạn của token
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
            log.error("JWT claims STring is empty");
        }
        return false;
    }

    public Authentication getAuthentication(String token){
        // lấy email password từ db lên sau dó convert về userdetails , và set quyền cho security (tạo ra 1 đăng nhập)
        UserDetails userDetails = userPrincipalService.loadUserByUsername(getEmailFromJwt(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }
}

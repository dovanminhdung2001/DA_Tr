package springboot.project.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import springboot.project.service.JwtTokenService;

import java.io.IOException;

@Service
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtTokenService jwtTokenService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = resolveToken(request);
        try {
            if (token != null && jwtTokenService.validateToken(token)) {
                // lấy ra thông tin người dùng
                Authentication authentication = jwtTokenService.getAuthentication(token);
                // set vào context để có đăng nhập .(set thông tin cho người dùng cho security context)
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex){
            SecurityContextHolder.clearContext();
            response.sendError(401,ex.getMessage());
        }

        filterChain.doFilter(request,response);

    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // kiểm tra xem header Authorization có chứa thông jwt ko
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

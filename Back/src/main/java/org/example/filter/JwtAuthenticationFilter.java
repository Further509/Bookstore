package org.example.filter;

import io.jsonwebtoken.Claims;
import org.example.model.CustomAuthenticationToken;
import org.example.model.JwtUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 从请求头中获取 JWT Token
        String token = getTokenFromRequest(request);

        if (token != null && jwtUtil.validateJwt(token)) {
            // 解析 JWT
            Claims claims = jwtUtil.parseJwt(token);
            Integer userId = claims.get("userId", Integer.class);
            String role = claims.get("role", String.class); // 从 JWT 获取角色
            System.out.println("Parsed JWT -> userId: " + userId + ", role: " + role);  // 输出日志
            // 设置认证信息到 Spring Security 上下文
            if (userId != null) {
                CustomAuthenticationToken authentication = new CustomAuthenticationToken(userId,  null);
                authentication.setAuthenticated(true); // 设置认证为已认证
                // 将角色添加到认证信息中
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
//                authentication.setAuthorities(authorities);  // 设置权限列表
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 继续过滤链
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // 移除 "Bearer " 部分
        }
        return null;
    }
}

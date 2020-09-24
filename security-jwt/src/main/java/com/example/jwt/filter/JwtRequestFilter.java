package com.example.jwt.filter;


import com.example.jwt.Constants;
import com.example.jwt.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        // JWT一般是"Bearer token"这样的格式，去除Bearer 前缀，只留下token本身
        String bearerPrefix = "Bearer ";
        if (requestTokenHeader != null && requestTokenHeader.startsWith(bearerPrefix)) {
            jwtToken = requestTokenHeader.substring(7);
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
            for (Cookie cookie : request.getCookies()) {
                if (Constants.JWT.equals(cookie.getName())) {
                    jwtToken = cookie.getValue();
                }
            }
        }
        try {
            username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            System.out.println("JWT Token has expired");
        }


        // 验证token
        boolean setAuthentication = username != null
                && SecurityContextHolder.getContext().getAuthentication() == null
                && jwtTokenUtil.validateToken(jwtToken, username);
        if (setAuthentication) {
            //如果token有效，则进行手动授权并将其设置到Spring Security的上下文中
            String userJson = jwtTokenUtil.getClaimFromToken(jwtToken, (c) -> c.get(Constants.PRINCIPAL, String.class));
            User userDetails = objectMapper.readValue(userJson, User.class);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}

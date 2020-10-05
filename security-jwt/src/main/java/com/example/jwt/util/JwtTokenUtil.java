package com.example.jwt.util;


import com.example.jwt.Constants;
import com.example.jwt.domain.JwtUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * token有效期，单位为秒
     */
    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    @Value("${jwt.secret}")
    private String secret;
    private final ObjectMapper objectMapper;

    public String getUsernameFromToken(String token) {
        //从JWT中获取用户名
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        //从JWT中获取过期时间
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        //通过秘钥获取JWT中所有Claims信息
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        //确认token是否过期
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) throws JsonProcessingException {
        //为某用户生成token
        Map<String, Object> claims = new HashMap<>();
        //……此处可以根据需求在claims中新增用户状态信息,此处将principle以JSONString的格式加入到Claims中属于可选项
        JwtUser principal = new JwtUser()
                .setUsername(userDetails.getUsername())
                .setPassword(userDetails.getPassword())
                .setAuthorities(userDetails.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()));
        claims.put(Constants.PRINCIPAL, objectMapper.writeValueAsString(principal));
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        //生成token的大致步骤：
        //1.定义令牌的声明信息，例如主体、过期时间
        //2.使用HS512算法与配置好的秘钥对JWT进行加密
        //3.对JTW进行压缩
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        //验证token
        return Objects.equals(getUsernameFromToken(token), username)
                && !isTokenExpired(token);
    }
}

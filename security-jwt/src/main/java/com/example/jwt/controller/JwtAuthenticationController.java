package com.example.jwt.controller;

import com.example.jwt.Constants;
import com.example.jwt.domain.R;
import com.example.jwt.domain.UserDto;
import com.example.jwt.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;


    @PostMapping("/authenticate")
    public R<?> createAuthenticationToken(@RequestBody UserDto authenticationRequest, HttpServletResponse response) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        setCookie(token, response);
        return R.ok(token);
    }

    private void authenticate(String username, String password) throws Exception {
        //并不在该类进行验证用户名密码，而是委托给AuthenticationManager
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    private void setCookie(String token, HttpServletResponse response) {
        //将JWT写入cookie，用于减少客户端对Header的操作(该步骤为可选项)
        Cookie cookie = new Cookie(Constants.JWT, token);
        response.addCookie(cookie);
    }

}

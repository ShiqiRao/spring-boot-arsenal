package com.example.shield.controller;

import com.example.shield.domain.R;
import com.example.shield.domain.UserDto;
import com.example.shield.entity.TUser;
import com.example.shield.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class SimpleController {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @PostMapping("/account")
    public R<Void> register(@RequestBody @Valid UserDto userDto) {
        TUser tUser = new TUser()
                .setUsername(userDto.getUsername())
                //数据库中存储明文密码会降低系统安全性，故存储前使用encoder为密码加密。
                .setPassword(encoder.encode(userDto.getPassword()));
        userRepository.save(tUser);
        return R.ok(null);
    }

    @GetMapping("/hello")
    public R<String> greeting() {
        //仅用来代表受保护的资源
        return R.ok("Hi!");
    }

}

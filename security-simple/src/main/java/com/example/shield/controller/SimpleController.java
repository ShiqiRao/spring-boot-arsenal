package com.example.shield.controller;

import com.example.shield.domain.R;
import com.example.shield.domain.UserDto;
import com.example.shield.entity.TUser;
import com.example.shield.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@RequiredArgsConstructor
@RestController
public class SimpleController {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @PostMapping("/account")
    public R<Void> register(@RequestBody @Valid UserDto userDto) {
        TUser duplicated = userRepository.findByUsername(userDto.getUsername());
        if (duplicated != null) {
            return R.failed("username重复");
        }
        TUser TUser = new TUser()
                .setUsername(userDto.getUsername())
                //数据库中存储明文密码会降低系统安全性，故存储前使用encoder为密码加密。
                .setPassword(encoder.encode(userDto.getPassword()));
        userRepository.save(TUser);
        return R.ok(null);
    }

    @GetMapping("/hello")
    public R<String> greeting() {
        //用来代表受保护的资源
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //获取当前用户名
        return R.ok(String.format("Hi! This is %s.I have some authorities like %s.",
                authentication.getName(), Arrays.toString(((User) authentication.getPrincipal()).getAuthorities().toArray())));
    }

    @GetMapping("/administrator")
    public R<String> administrator() {
        //只有administrator才可以访问的接口
        return R.ok("administrator");
    }

    @GetMapping("/staff")
    public R<String> staff() {
        //只有staff才可以访问的接口
        return R.ok("staff");
    }

    @GetMapping("/read")
    public R<String> read() {
        //用以表示读操作接口
        return R.ok("blah blah blah……");
    }

    @PostMapping("/write")
    public R<String> write(@RequestParam String input) {
        //用以表示写操作接口
        return R.ok(input);
    }
}

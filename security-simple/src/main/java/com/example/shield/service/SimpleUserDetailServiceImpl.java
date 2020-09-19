package com.example.shield.service;

import com.example.shield.entity.TUser;
import com.example.shield.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SimpleUserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TUser tUser = userRepository.findByUsername(username);
        if (tUser == null) {
            throw new UsernameNotFoundException("username not found");
        }
        return new User(tUser.getUsername(), tUser.getPassword(), getAuthorities());
    }

    private Collection<GrantedAuthority> getAuthorities() {
        //获取用户的角色权限，本示例不包含权限控制，故简化实现过程
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}

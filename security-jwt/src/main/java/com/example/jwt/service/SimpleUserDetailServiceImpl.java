package com.example.jwt.service;


import com.example.jwt.entity.TUser;
import com.example.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

@Transactional(rollbackFor = Exception.class)
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
        return new User(tUser.getUsername(), tUser.getPassword(), getAuthorities(tUser));
    }

    private Collection<GrantedAuthority> getAuthorities(TUser user) {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }
}

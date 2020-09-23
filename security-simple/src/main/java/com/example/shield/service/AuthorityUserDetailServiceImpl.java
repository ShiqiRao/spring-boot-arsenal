package com.example.shield.service;

import com.example.shield.entity.TAuthority;
import com.example.shield.entity.TRole;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthorityUserDetailServiceImpl implements UserDetailsService {

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
        List<GrantedAuthority> authorities = new ArrayList<>();
        //将用户的角色以及角色的权限一起加入至GrantedAuthority集合
        for (TRole role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            for (TAuthority authority : role.getAuthorities()) {
                authorities.add(new SimpleGrantedAuthority(authority.getName()));
            }
        }
        return authorities;
    }
}

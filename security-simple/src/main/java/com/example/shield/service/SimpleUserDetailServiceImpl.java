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
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Transactional
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
        return user.getRoles()
                .stream()
                .flatMap(
                        r -> r.getAuthorities()
                                .stream()
                                .map(a -> new SimpleGrantedAuthority(a.getName()))
                )
                .collect(Collectors.toList());
    }
}

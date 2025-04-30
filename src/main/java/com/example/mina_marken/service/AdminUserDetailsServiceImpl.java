package com.example.mina_marken.service;

import com.example.mina_marken.model.entity.Admin;
import com.example.mina_marken.repo.AdminRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserDetailsServiceImpl implements UserDetailsService {

    private final AdminRepo adminRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(admin.getUsername(), admin.getPassword(),
                List.of(new SimpleGrantedAuthority(admin.getRole())));
    }
}

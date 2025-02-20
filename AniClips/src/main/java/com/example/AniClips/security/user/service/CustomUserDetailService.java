package com.example.AniClips.security.user.service;

import com.example.AniClips.security.user.repo.UserRepository;
import lombok.Generated;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails)this.userRepository.findFirstByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    @Generated
    public CustomUserDetailService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
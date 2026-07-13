package com.self.SafePay.Service;

import com.self.SafePay.Entity.User;
import com.self.SafePay.Repository.UserRepository;
import com.self.SafePay.Security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email).
                orElseThrow(()->new RuntimeException("User not exist"));
        return new CustomUserDetails(user);
    }
}

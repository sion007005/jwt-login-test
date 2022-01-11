package com.sion.jwtlogintest.auth;

import com.sion.jwtlogintest.model.User;
import com.sion.jwtlogintest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// /login 요청이 올 때 동작하는 클래스
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService의 loadUserByUsername 호출");
        User user = userRepository.findByUsername(username);
        return new PrincipalDetails(user);
    }
}

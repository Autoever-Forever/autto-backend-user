package com.autto.userservice.service;

import com.autto.userservice.entity.User;
import com.autto.userservice.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

// 로컬에서 암호화 안한 비밀번호로 테스트시 사용
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.beans.factory.annotation.Autowired;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.getByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다."));
    }

    // 로컬에서 암호화 안한 비밀번호로 테스트시 사용
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 return
    private UserDetails createUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                // 로컬에서 암호화 안한 비밀번호로 테스트시 사용
//                .password(passwordEncoder.encode(user.getPassword()))
                .roles(user.getRole().name())
                .build();
    }
}

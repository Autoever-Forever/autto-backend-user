package com.autto.userservice.service;

import com.autto.userservice.dto.JwtToken;
import com.autto.userservice.persistence.UserRepository;
import com.autto.userservice.provider.JwtTokenProvider;
import com.autto.userservice.response.CustomUsernameNotFoundException;
import com.autto.userservice.response.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SignInServiceImpl implements SignInService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtToken signIn(String email, String password) {
        try {
            // 1. email + password 를 기반으로 Authentication 객체 생성
            // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

            // 2. 실제 검증. authenticate() 메서드를 통해 요청된 User 에 대한 검증 진행
            // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // 3. 인증 정보를 기반으로 JWT 토큰 생성
            JwtToken jwtToken;
            jwtToken = jwtTokenProvider.generateToken(authentication);

            return jwtToken;
        } catch (UsernameNotFoundException ex) {
            // 이메일이 틀릴 경우 예외 발생
            throw new UsernameNotFoundException("");  // 이메일 오류
        } catch (BadCredentialsException ex) {
            // 비밀번호가 틀릴 경우 예외 발생
//            throw new BadCredentialsException("The password is incorrect.");  // 비밀번호 오류
            throw new CustomUsernameNotFoundException(ErrorCode.INVALID_USER);  // 이메일 오류
        } catch (Exception ex) {
            // 다른 예외 처리
            throw new RuntimeException("Unexpected error occurred during authentication.", ex);
        }
    }
}

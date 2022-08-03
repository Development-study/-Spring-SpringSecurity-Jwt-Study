package com.study.security.domain.auth.service;

import com.study.security.domain.auth.presentation.dto.UserSignInRequest;
import com.study.security.domain.auth.presentation.dto.UserSignUpRequest;
import com.study.security.domain.user.entity.User;
import com.study.security.domain.user.exception.PasswordWrongException;
import com.study.security.domain.user.exception.UserAlreadyExistsException;
import com.study.security.domain.user.exception.UserNotFoundException;
import com.study.security.domain.user.repository.UserRepository;
import com.study.security.global.token.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public String signUp(UserSignUpRequest request) {
        userRepository.findByUserId(request.getId())
                .ifPresent(m -> {
                    throw UserAlreadyExistsException.EXCEPTION;
                });

        User user = User.builder()
                .userId(request.getId())
                .password(passwordEncoder.encode(request.getPw()))
                .build();

        return userRepository.save(user).getUserId();
    }

    public String signIn(UserSignInRequest request) {
        User found = userRepository.findByUserId(request.getId())
                .orElseThrow(() -> {
                    throw UserNotFoundException.EXCEPTION;
                });

        if(passwordEncoder.matches(request.getPw(), found.getPassword())) {
            return jwtProvider.generateAccessToken(found.getId());
        } else {
            throw PasswordWrongException.EXCEPTION;
        }
    }

}

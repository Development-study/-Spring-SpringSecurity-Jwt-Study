package com.study.security.domain.auth.service;

import com.study.security.domain.auth.presentation.dto.UserSignInRequest;
import com.study.security.domain.auth.presentation.dto.UserSignUpRequest;
import com.study.security.domain.user.entity.User;
import com.study.security.domain.user.enums.Role;
import com.study.security.domain.user.exception.PasswordWrongException;
import com.study.security.domain.user.exception.UserAlreadyExistsException;
import com.study.security.domain.user.exception.UserNotFoundException;
import com.study.security.domain.user.repository.UserRepository;
import com.study.security.global.jwt.JwtProvider;
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
    public String userSignUp(UserSignUpRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(m -> {
                    throw UserAlreadyExistsException.EXCEPTION;
                });

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        return userRepository.save(user).getEmail();
    }

    public String userSignIn(UserSignInRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    throw UserNotFoundException.EXCEPTION;
                });

        if(passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return jwtProvider.generateToken(user.getEmail(), user.getRole());
        } else {
            throw PasswordWrongException.EXCEPTION;
        }

    }

}

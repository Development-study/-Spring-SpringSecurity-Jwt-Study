package com.study.security.domain.user.service;

import com.study.security.domain.user.entity.User;
import com.study.security.domain.user.exception.UserNotFoundException;
import com.study.security.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDetails getUserByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw UserNotFoundException.EXCEPTION;
                });

        return new UserDetailsImpl(user);
    }

}

package com.study.security.domain.auth.presentation;

import com.study.security.domain.auth.presentation.dto.UserSignInRequest;
import com.study.security.domain.auth.presentation.dto.UserSignUpRequest;
import com.study.security.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public String signUp(
            @RequestBody UserSignUpRequest request
    ) {
        return authService.signUp(request);
    }

    @PostMapping("/sign-in")
    public String signIn(
            @RequestBody UserSignInRequest request
    ) {
        return authService.signIn(request);
    }

}

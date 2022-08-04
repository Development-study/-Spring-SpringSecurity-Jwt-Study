package com.study.security.domain.auth.presentation;

import com.study.security.domain.auth.presentation.dto.UserSignInRequest;
import com.study.security.domain.auth.presentation.dto.UserSignUpRequest;
import com.study.security.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public String userSignUp(
            @RequestBody @Valid UserSignUpRequest request
    ) {
        return authService.userSignUp(request);
    }

    @PostMapping("/sign-in")
    public String userSignIn(
            @RequestBody @Valid UserSignInRequest request
    ) {
        return authService.userSignIn(request);
    }

}

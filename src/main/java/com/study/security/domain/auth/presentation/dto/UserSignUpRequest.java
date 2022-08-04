package com.study.security.domain.auth.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;

@Getter @AllArgsConstructor
@Builder
public class UserSignUpRequest {

    @Email
    private String email;

    private String password;

}

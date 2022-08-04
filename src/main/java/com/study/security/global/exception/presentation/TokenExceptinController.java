package com.study.security.global.exception.presentation;

import com.study.security.global.error.ErrorCode;
import com.study.security.global.exception.CustomException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/exception")
public class TokenExceptinController {

    @GetMapping("/entrypoint")
    public void entryPoint() {
        throw new CustomException(ErrorCode.NO_LOGIN);
    }

    @GetMapping("/access")
    public void denied() {
        throw new CustomException(ErrorCode.NO_ADMIN);
    }


}

package onboarding.wanted.backend.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import onboarding.wanted.backend.domain.auth.dto.Token;
import onboarding.wanted.backend.domain.auth.dto.UserLoginRequest;
import onboarding.wanted.backend.domain.auth.dto.UserSignupRequest;
import onboarding.wanted.backend.domain.auth.dto.UserSignupResponse;
import onboarding.wanted.backend.domain.auth.service.AuthService;
import onboarding.wanted.backend.domain.user.repository.UserRepository;
import onboarding.wanted.backend.global.response.ApiResponse;
import onboarding.wanted.backend.global.response.CustomResponse;
import onboarding.wanted.backend.global.response.ResultCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<CustomResponse> signup (
            @Valid @RequestBody UserSignupRequest signupReqDto) {

        UserSignupResponse signupResDto = authService.signup(signupReqDto);

        return ApiResponse.of(ResultCode.USER_SIGNUP_SUCCESS, signupResDto);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<CustomResponse> login (
            @Valid @RequestBody UserLoginRequest loginReqDto
    ) {
        Token token = authService.login(loginReqDto);

        return ApiResponse.of(ResultCode.USER_LOGIN_SUCCESS, token);
    }

//    // 토큰 재발급
//    @PostMapping("/reissue")
//    public ResponseEntity<ResultResponse> reissue (String refreshToken) {
//    }

}
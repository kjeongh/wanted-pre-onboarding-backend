package onboarding.wanted.backend.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import onboarding.wanted.backend.domain.auth.service.AuthService;
import onboarding.wanted.backend.domain.user.dto.UserLoginRequest;
import onboarding.wanted.backend.domain.user.dto.UserSignupRequest;
import onboarding.wanted.backend.domain.user.dto.UserSignupResponse;
import onboarding.wanted.backend.domain.user.repository.UserRepository;
import onboarding.wanted.backend.global.response.ResultCode;
import onboarding.wanted.backend.global.response.ResultResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<ResultResponse> signup (
            @RequestBody UserSignupRequest signupReqDto) {

        UserSignupResponse signupResDto = authService.signup(signupReqDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_SIGNUP_SUCCESS, signupResDto));
    }

    @PostMapping("/login")
    public ResponseEntity<ResultResponse> login (
            @RequestBody UserLoginRequest loginReqDto
    ) {
        String accessToken = authService.login(loginReqDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_LOGIN_SUCCESS, accessToken));
    }


//    @PostMapping("/test")
//    public ResponseEntity<ResultResponse> login (@RequestBody String email
//    ) {
//        User user = userRepository.findByEmail(email).orElseThrow();
//        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_LOGIN_SUCCESS, user));
//    }

//    //토큰 재발급
//    @PostMapping("/reissue")
//    public ResponseEntity<ResultResponse> reissue (String refreshToken) {
//        //만료된 refreshToken이면 null반환
//        String newAccessToken = authService.reissue(String refreshToken);
//
//        if(newAccessToken == null) {
//
//        }
//
//    }


}
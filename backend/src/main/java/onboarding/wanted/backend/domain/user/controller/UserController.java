package onboarding.wanted.backend.domain.user.controller;

import lombok.RequiredArgsConstructor;
import onboarding.wanted.backend.domain.user.dto.UserSignupRequest;
import onboarding.wanted.backend.domain.user.dto.UserSignupResponse;
import onboarding.wanted.backend.domain.user.service.UserService;
import onboarding.wanted.backend.global.response.ResultCode;
import onboarding.wanted.backend.global.response.ResultResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }

    @PostMapping("/signup")
    public ResponseEntity<ResultResponse> signup (
            @RequestBody UserSignupRequest signupReqDto) {

        UserSignupResponse signupResDto = userService.signup(signupReqDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_SIGNUP_SUCCESS, signupResDto));
    }


}
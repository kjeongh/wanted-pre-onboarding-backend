package onboarding.wanted.backend.domain.auth.service;

import lombok.RequiredArgsConstructor;
import onboarding.wanted.backend.domain.auth.util.TokenProvider;
import onboarding.wanted.backend.domain.auth.repository.RefreshTokenRepository;
import onboarding.wanted.backend.domain.user.entity.User;
import onboarding.wanted.backend.domain.auth.dto.UserLoginRequest;
import onboarding.wanted.backend.domain.auth.dto.UserLoginResponse;
import onboarding.wanted.backend.domain.auth.dto.UserSignupRequest;
import onboarding.wanted.backend.domain.auth.dto.UserSignupResponse;
import onboarding.wanted.backend.domain.user.mapper.UserMapper;
import onboarding.wanted.backend.domain.user.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    // 회원가입
    public UserSignupResponse signup(UserSignupRequest signupReqDto) {
        if(userRepository.existsByEmail(signupReqDto.getEmail())) {
            throw new RuntimeException();
        }

        User user = userMapper.toEntity(signupReqDto);
        userRepository.save(user);

        return UserSignupResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    // 로그인
    public UserLoginResponse login(UserLoginRequest loginReqDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginReqDto.getEmail(), loginReqDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        return UserLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}


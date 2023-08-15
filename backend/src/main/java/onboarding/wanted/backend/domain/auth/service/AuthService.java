package onboarding.wanted.backend.domain.auth.service;

import lombok.RequiredArgsConstructor;
import onboarding.wanted.backend.domain.auth.util.SecurityUtil;
import onboarding.wanted.backend.domain.auth.util.TokenProvider;
import onboarding.wanted.backend.domain.auth.repository.RefreshTokenRepository;
import onboarding.wanted.backend.domain.user.entity.User;
import onboarding.wanted.backend.domain.auth.dto.UserLoginRequest;
import onboarding.wanted.backend.domain.auth.dto.Token;
import onboarding.wanted.backend.domain.auth.dto.UserSignupRequest;
import onboarding.wanted.backend.domain.auth.dto.UserSignupResponse;
import onboarding.wanted.backend.domain.user.repository.UserRepository;
import onboarding.wanted.backend.global.error.ErrorCode;
import onboarding.wanted.backend.global.error.exception.BusinessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public UserSignupResponse signup(UserSignupRequest signupReqDto) {

        if(userRepository.existsByEmail(signupReqDto.getEmail())) {
            throw new BusinessException(ErrorCode.USER_ALREADY_EXISTS);
        }

        User user = signupReqDto.toEntity(passwordEncoder.encode(signupReqDto.getPassword()));

        return UserSignupResponse.of(userRepository.save(user));
    }

    @Transactional
    public Token login(UserLoginRequest loginReqDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginReqDto.getEmail(), loginReqDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        return new Token(accessToken, refreshToken);
    }

    @Transactional
    public User getLoginUser() {
        User loginUser = SecurityUtil.getLoginUserEmail()
                .flatMap(userRepository::findByEmail)
                .orElseThrow(() -> new BusinessException(ErrorCode.LOGIN_USER_NOT_FOUND));

        return loginUser;
    }

}

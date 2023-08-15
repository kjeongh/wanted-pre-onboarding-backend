package onboarding.wanted.backend.domain.auth.service;

import lombok.RequiredArgsConstructor;
import onboarding.wanted.backend.domain.auth.TokenProvider;
import onboarding.wanted.backend.domain.auth.repository.RefreshTokenRepository;
import onboarding.wanted.backend.domain.user.User;
import onboarding.wanted.backend.domain.user.dto.UserLoginRequest;
import onboarding.wanted.backend.domain.user.dto.UserSignupRequest;
import onboarding.wanted.backend.domain.user.dto.UserSignupResponse;
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

    public String login(UserLoginRequest loginReqDto) {

        //TODO: 여기서 익셉션 터짐,,,
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginReqDto.getEmail(), loginReqDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        //TODO: 토큰을 헤더에 저장해두기

        return accessToken;
    }


//    public String reissue(String refreshToken) {
//        Optional<RefreshToken> foundRefreshTokenOptional = refreshTokenRepository.findById(refreshToken);
//
//        //refresh토큰 만료되지 않은 경우
//        if (foundRefreshTokenOptional.isPresent()) {
//            RefreshToken foundRefreshToken = foundRefreshTokenOptional.get();
//            Long userId = foundRefreshToken.getUserId();
//
//            String newAccessToken = tokenProvider.createAccessToken();
//
//        }
//        //refresh토큰 만료된 경우
//        else {
//            //UNAUTHORIZED 에러
//        }
//    }

}

